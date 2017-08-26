package mort.mortmagic.common.runes;


import mort.mortmagic.Content;
import mort.mortmagic.MortMagic;
import mort.mortmagic.common.CommonProxy;
import mort.mortmagic.common.tileentity.TileRune;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

import java.util.*;

/**
 * A collections of runes in world, that form a circle
 */
public class RuneCircle {

    List<BlockPos> sequenceOfRunes;
    RuneWord word;
    AxisAlignedBBInteger bounds;
    Set<BlockPos> setOfRunes;
    EnumFacing.Axis normal;

    public RuneCircle(List<BlockPos> sequenceOfRunes, RuneWord word, EnumFacing normal) {
        this.sequenceOfRunes = sequenceOfRunes;
        this.word = word;
        this.normal = normal.getAxis();
        initialize();
    }

    public NBTTagCompound writeToNBT(){
        NBTTagCompound tag = new NBTTagCompound();
        NBTTagList list = new NBTTagList();

        for( BlockPos pos : sequenceOfRunes ) {
            NBTTagCompound posTag = new NBTTagCompound();
            posTag.setInteger( "x", pos.getX() );
            posTag.setInteger( "y", pos.getY() );
            posTag.setInteger( "z", pos.getZ() );
            list.appendTag( posTag );
        }
        tag.setTag( "runes", list );
        tag.setString( "axis", normal.getName() );
        tag.setString( "word", word.getRegistryName().toString() );
        return tag;
    }

    public RuneCircle(NBTTagCompound source){
        this.normal = EnumFacing.Axis.byName( source.getString("axis") );
        sequenceOfRunes = new ArrayList<>( source.getTagList("runes",10).tagCount() );
        for( NBTBase base : source.getTagList("runes",10) ){
            NBTTagCompound comp = (NBTTagCompound) base;
            sequenceOfRunes.add( new BlockPos( comp.getInteger("x"), comp.getInteger("y"), comp.getInteger("z") ) );
        }
        this.word = Content.RUNE_WORD_REGISTRY.getValue( new ResourceLocation( source.getString("word") ) );
        initialize();
    }

    /**
     * Initializes all fiels that are calculated
     */
    private void initialize(){
        setOfRunes = new HashSet<>( sequenceOfRunes );
        bounds = new AxisAlignedBBInteger( sequenceOfRunes.get(0) );
        for( BlockPos pos : sequenceOfRunes ){
            bounds.enlarge( pos );
        }
        switch (normal){
            case X: bounds.grow( (int)Math.ceil(Math.sqrt(sequenceOfRunes.size()))-1,0,0 );
            case Y: bounds.grow( 0,(int)Math.ceil(Math.sqrt(sequenceOfRunes.size()))-1,0 );
            case Z: bounds.grow( 0,0,(int)Math.ceil(Math.sqrt(sequenceOfRunes.size()))-1 );
        }

    }

    public void spawnParticles( World world ){
        for( BlockPos pos : sequenceOfRunes ){
            MortMagic.proxy.spawnDebugParticle( world, pos, CommonProxy.EnumDebugParticle.CIRCLE_COMPLETED);
        }
    }

    public boolean contains( BlockPos pos ){
        if( !bounds.contains( pos ) ) //outside bounding box - cannot be in circle
            return false;
        ////------------------------------------ height
        return outlineContains(pos);
    }

    private static Vec3i rayStepX = new Vec3i( 1, 0, 0 );
    private static Vec3i rayStepY = new Vec3i( 0, 1, 0 );
    //private static Vec3i rayStepZ = new Vec3i( 0, 0, 1 );

    private boolean outlineContains( BlockPos pos ){
        if( (normal == EnumFacing.Axis.X && setOfRunes.contains(new BlockPos( sequenceOfRunes.get(0).getX(),pos.getY(), pos.getZ() ) ) ) ||
                (normal == EnumFacing.Axis.Y && setOfRunes.contains(new BlockPos( pos.getX(),sequenceOfRunes.get(0).getY(), pos.getZ() ) ) ) ||
                        (normal == EnumFacing.Axis.X && setOfRunes.contains(new BlockPos( pos.getX(),pos.getY(), sequenceOfRunes.get(0).getZ() ) ) ) )
            return true; //if projected blocks is on boundary - return true ( "integer raycast" test doesn't work correctly on boundary blocks
        Vec3i step = Vec3i.NULL_VECTOR;
        BlockPos current = BlockPos.ORIGIN;
        int numSteps = 0;
        switch(normal){
            case X: step = rayStepY;
                            current = new BlockPos( sequenceOfRunes.get(0).getX(), bounds.minY, pos.getZ() );
                            numSteps = pos.getY() - current.getY();
                            break;
            case Z: step = rayStepY;
                            current = new BlockPos( pos.getX(), bounds.minY, sequenceOfRunes.get(0).getZ() );
                            numSteps = pos.getY() - current.getY();
                            break;
            case Y: step = rayStepX;
                            current = new BlockPos(bounds.minX, sequenceOfRunes.get(0).getY(), pos.getZ() );
                            numSteps = pos.getX() - current.getX();
        }
        boolean contains = false;
        for( int i = 0; i<numSteps;i++ ) {
            if (setOfRunes.contains(current))
                contains = !contains;
            current = current.add(step);
        }
        return contains;
    }

    public static RuneCircle tryFindCircle(World world, BlockPos startingPosition ){

        //try all three axes. The axis currently used is considered the normal
        for( EnumFacing face : new EnumFacing[]{ EnumFacing.NORTH, EnumFacing.WEST, EnumFacing.UP } ){
            RuneCircle c = tryFind2dCircle( world, startingPosition, face );
            if(c!=null)
                return c;
        }

        return null;
    }

    private static RuneCircle tryFind2dCircle( World world, BlockPos startPos, EnumFacing normal ){
        EnumFacing[] directions;
        switch( normal ){
            case DOWN:
            case UP:    directions = new EnumFacing[]{ EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.SOUTH, EnumFacing.WEST };
                    break;
            case EAST:
            case WEST:  directions = new EnumFacing[]{ EnumFacing.NORTH, EnumFacing.UP, EnumFacing.SOUTH, EnumFacing.DOWN };
                        break;
            case NORTH:
            case SOUTH:
            default:    directions = new EnumFacing[]{ EnumFacing.UP, EnumFacing.EAST, EnumFacing.DOWN, EnumFacing.WEST };
            //default not necessary, just for compilers sake
        }

        RuneMaterial mat = ((TileRune)world.getTileEntity(startPos)).getMaterial();

        List<BlockPos> circle = new ArrayList<>();
        circle.add( startPos );

        List<BlockPos> continuations = new ArrayList<>(); //resets every step
        while( true ){
            continuations.clear();
            //find all directions, in which the circle can continue
            for (EnumFacing dir : directions){
                BlockPos newPos = circle.get( circle.size()-1 ).offset( dir );
                if( circle.size() > 1 && newPos.equals(circle.get(circle.size()-2)) ) //do not go backwards
                    continue;
                IBlockState state = world.getBlockState(newPos);
                if( state.getBlock() == Content.runeBlock && ((TileRune)world.getTileEntity(newPos)).getMaterial() == mat ){
                    continuations.add(newPos);
                }
            }
            // on the first rune, we have 2 possible directions. Each other step should yield only one possible direction
            if( continuations.size() == 1 || (continuations.size()==2 && circle.size()==1 ) ){
                if( continuations.get(0).equals( startPos ) ) {
                    return tryCreateRuneCircle(world, circle, normal );
                }
                else {
                    circle.add(continuations.get(0));
                }
            }
            else{
                /*for( BlockPos pos : circle ){
                    MortMagic.proxy.spawnDebugParticle( world, pos, CommonProxy.EnumDebugParticle.CIRCLE_CANDIDATE);
                }*/
                return null;
            }
        }
    }

    private static RuneCircle tryCreateRuneCircle(World world, List<BlockPos> positions, EnumFacing normal){
        List<RuneCharacter> characters = new ArrayList<>();
        for( BlockPos pos : positions ){
            characters.add( ((TileRune)world.getTileEntity(pos)).getCharacter() );
        }
        RuneWord word = MortMagic.dictionary.findMatchingWord( characters );
        if(word!=null)
            return new RuneCircle( positions, word, normal );
        else
            return null;
    }

    private static Vec3d getBlockPosCentre(BlockPos pos){
        return new Vec3d( pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f );
    }

    private class AxisAlignedBBInteger{
        int minX;
        int minY;
        int minZ;
        int maxX;
        int maxY;
        int maxZ;

        public AxisAlignedBBInteger(BlockPos initial) {
            minX = maxX = initial.getX();
            minY = maxY = initial.getY();
            minZ = maxZ = initial.getZ();
        }

        public void enlarge( BlockPos newPos ){
            minX = Math.min( minX, newPos.getX() );
            minY = Math.min( minY, newPos.getY() );
            minZ = Math.min( minZ, newPos.getZ() );
            maxX = Math.max( maxX, newPos.getX() );
            maxY = Math.max( maxY, newPos.getY() );
            maxZ = Math.max( maxZ, newPos.getX() );
        }
        public boolean contains(BlockPos pos){
            return minX  <= pos.getX() && maxX >= pos.getX() &&
                    minY <= pos.getY() && maxY >= pos.getY() &&
                    minZ <= pos.getZ() && maxZ >= pos.getZ();
        }
        public void grow( int x, int y, int z ){
            minX -= x;
            maxX += x;
            minY -= y;
            maxY += y;
            minZ -= z;
            maxZ += z;
        }

        @Override
        public String toString() {
            return "AxisAlignedBBInteger{" + minX +
                    "," + minY +
                    "," + minZ +
                    " - " + maxX +
                    "," + maxY +
                    "," + maxZ +
                    '}';
        }
    }


}
