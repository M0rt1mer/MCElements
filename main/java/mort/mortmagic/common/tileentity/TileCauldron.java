package mort.mortmagic.common.tileentity;

import mort.mortmagic.MortMagic;
import mort.mortmagic.api.PotionIngredientRegistry;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.Vec3d;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TileCauldron extends TileEntity {

    public List<ItemStack> items = new ArrayList<>();
    /**
     * a mirror of @link(items), saves on ingredient lookups.
     * Is not saved, but refreshed after load
     */
    private Set<PotionIngredientRegistry.Entry> ingredientCache = new HashSet<>();

    public boolean throwItemIn( ItemStack stk ){
        PotionIngredientRegistry.Entry entry = MortMagic.potReg.findItemEntry( stk );
        if(entry==null || ingredientCache.contains(entry) ) //item is not an ingredient, OR ingredient is already in cauldron
            return false;
        if( world.isRemote )
            return true; //if world is remote, don't actually change anything
        items.add(stk.splitStack(1));
        ingredientCache.add(entry);
        /*getWorld().markBlockRangeForRenderUpdate( getPos(), getPos() );
        this.markDirty();*/
        IBlockState state = world.getBlockState( pos );
        markDirty();
        world.notifyBlockUpdate( pos, state, state, 3 );
        return true;
    }

    public static final Vec3d rubToRgb[] = {
            new Vec3d( 1,1,1 ), new Vec3d(0.163,0.373,0.6), new Vec3d(1,1,0), new Vec3d(0,0.6,0.2),
            new Vec3d( 1,0,0 ), new Vec3d(0.5,0,0.5), new Vec3d(1,0.5,0), new Vec3d(0.2,0.094,0)
    };

    public int getColor(){

        float rubedo = 0; int rubedoCnt = 0;
        float auredo = 0; int auredoCnt = 0;
        float caerudo = 0; int caerudoCnt = 0;

        for(PotionIngredientRegistry.Entry entry : ingredientCache){
            if( entry.rubedo != PotionIngredientRegistry.AspectPower.NONE ){
                rubedo += entry.rubedo.value;
                rubedoCnt++;
            }
            if( entry.auredo != PotionIngredientRegistry.AspectPower.NONE ){
                auredo += entry.auredo.value;
                auredoCnt++;
            }
            if( entry.caerudo != PotionIngredientRegistry.AspectPower.NONE ){
                caerudo += entry.caerudo.value;
                caerudoCnt++;
            }
        }
        rubedo = rubedoCnt>0 ? (rubedo/rubedoCnt) : 0;
        auredo = auredoCnt>0 ? (auredo/auredoCnt) : 0;
        caerudo = caerudoCnt>0 ? (caerudo/caerudoCnt) : 0;

        Vec3d result = trilinearInterpolation( rubToRgb, caerudo, auredo, rubedo );
        return ((int)(result.x*255) << 16 ) + ((int)(result.y*255) << 8 ) + (int)(result.z*255);
    }


    private static Vec3d linearInterpolation( Vec3d a, Vec3d b, float f ){
        return new Vec3d( a.x * (1-f) + b.x * f, a.y * (1-f) + b.y * f, a.z * (1-f) + b.z * f );
    }

    /**
     * i interpolates in numbers, f in characters
     * @param a1
     * @param b1
     * @param a2
     * @param b2
     * @param f
     * @param i
     * @return
     */
    private static Vec3d bilinearInterpolation( Vec3d a1, Vec3d b1, Vec3d a2, Vec3d b2, float f, float i ){
        return linearInterpolation( linearInterpolation(a1,a2,i),linearInterpolation(b1,b2,i),f );
    }
    private static Vec3d trilinearInterpolation( Vec3d[] cube, float f, float i, float z ){
        return linearInterpolation( bilinearInterpolation(cube[0],cube[1],cube[2],cube[3],f,i), bilinearInterpolation(cube[4],cube[5],cube[6],cube[7],f,i), z );
    }
    public static Vec3d getRGB( float rubedo, float auredo, float caerudo ){
        return trilinearInterpolation( rubToRgb, caerudo, auredo, rubedo );
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        for( NBTBase bas : compound.getTagList( "items", 10 ) ){
            ItemStack stk = new ItemStack( (NBTTagCompound) bas );
            PotionIngredientRegistry.Entry entry = MortMagic.potReg.findItemEntry( stk );
            if(entry != null) { //only add to list if it is ingredient (an item can stop beeing an ingredient between saves, if updated)
                items.add(stk);
                ingredientCache.add(entry);
            }
        }
    }
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound = super.writeToNBT(compound);
        NBTTagList list = new NBTTagList();
        for( ItemStack stk : items ){
            list.appendTag( stk.serializeNBT() );
        }
        compound.setTag("items",list);
        return compound;
    }
    @Override
    public NBTTagCompound getUpdateTag() {
        return writeToNBT(new NBTTagCompound() );
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity( pos, 0, getUpdateTag() );
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        this.readFromNBT( pkt.getNbtCompound() );
    }
}
