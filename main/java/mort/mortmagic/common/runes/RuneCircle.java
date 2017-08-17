package mort.mortmagic.common.runes;


import mort.mortmagic.MortMagic;
import mort.mortmagic.Resource;
import mort.mortmagic.api.RuneDictionary;
import mort.mortmagic.common.CommonProxy;
import mort.mortmagic.common.block.BlockRune;
import mort.mortmagic.common.tileentity.TileRune;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A collections of runes in world, that form a circle
 */
public class RuneCircle {

    List<BlockPos> sequenceOfRunes;
    RuneWord word;

    public RuneCircle(List<BlockPos> sequenceOfRunes, RuneWord word) {
        this.sequenceOfRunes = sequenceOfRunes;
        this.word = word;
    }

    public static RuneCircle tryFindCircle(World world, BlockPos startingPosition ){

        //try all three axes. The axis currently used is considered the normal
        for( EnumFacing face : new EnumFacing[]{ EnumFacing.NORTH, EnumFacing.WEST, EnumFacing.UP } ){
            RuneCircle c = tryFind2dCircle( world, startingPosition,face );
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

        RuneMaterial mat = ((TileRune)world.getTileEntity(startPos)).material;

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
                if( state.getBlock() == Resource.runeBlock && ((TileRune)world.getTileEntity(newPos)).material == mat ){
                    continuations.add(newPos);
                }
            }
            // on the first rune, we have 2 possible directions. Each other step should yield only one possible direction
            if( continuations.size() == 1 || (continuations.size()==2 && circle.size()==1 ) ){
                if( continuations.get(0).equals( startPos ) ) {
                    for( BlockPos pos : circle ){
                        MortMagic.proxy.spawnDebugParticle( world, pos, CommonProxy.EnumDebugParticle.CIRCLE_COMPLETED);
                    }
                    return tryCreateRuneCircle(world, circle);
                }
                else {
                    circle.add(continuations.get(0));
                }
            }
            else{
                for( BlockPos pos : circle ){
                    MortMagic.proxy.spawnDebugParticle( world, pos, CommonProxy.EnumDebugParticle.CIRCLE_CANDIDATE);
                }
                return null;
            }
        }
    }

    private static RuneCircle tryCreateRuneCircle( World world, List<BlockPos> positions ){
        List<RuneCharacter> characters = new ArrayList<>();
        for( BlockPos pos : positions ){
            characters.add( ((TileRune)world.getTileEntity(pos)).character );
        }
        RuneWord word = MortMagic.dictionary.findMatchingWord( characters );
        if(word!=null)
            return new RuneCircle( positions, word );
        else
            return null;
    }


}
