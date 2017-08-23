package mort.mortmagic.common.tileentity;

import mort.mortmagic.MortMagic;
import mort.mortmagic.common.potions.PotionIngredientRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;

public class TileCauldron extends TileEntity {

    public List<ItemStack> items = new ArrayList<>();
    /**
     * a mirror of @link(items), saves on ingredient lookups.
     * Is not saved, but refreshed after load
     */
    private List<PotionIngredientRegistry.Entry> ingredientCache = new ArrayList<>();

    public boolean throwItemIn( ItemStack stk ){
        PotionIngredientRegistry.Entry entry = MortMagic.potReg.findItemEntry( stk );
        if(entry==null)
            return false;
        items.add(stk.splitStack(1));
        ingredientCache.add(entry);
        getWorld().markBlockRangeForRenderUpdate( getPos(), getPos() );
        return true;
    }

    public static final Vec3d rubToRgb[] = {
            new Vec3d( 1,1,1 ), new Vec3d(0.163,0.373,0.6), new Vec3d(1,1,0), new Vec3d(0,0.6,0.2),
            new Vec3d( 1,0,0 ), new Vec3d(0.5,0,0.5), new Vec3d(1,0.5,0), new Vec3d(0.2,0.094,0)
    };

    public int getColor(){

        if( items.size() == 0 )
            return 0;

        float rubedo = 0; int rubedoCnt = 0;
        float auredo = 0; int auredoCnt = 0;
        float caerudo = 0; int caerudoCnt = 0;

        for(PotionIngredientRegistry.Entry entry : ingredientCache){
            if( entry.rubedo > 0 ){
                rubedo += entry.rubedo;
                rubedoCnt++;
            }
            if( entry.auredo > 0 ){
                auredo += entry.auredo;
                auredoCnt++;
            }
            if( entry.caerudo > 0 ){
                caerudo += entry.caerudo;
                caerudoCnt++;
            }
        }
        rubedo = rubedoCnt>0 ? (rubedo/rubedoCnt) : 0;
        auredo = auredoCnt>0 ? (auredo/auredoCnt) : 0;
        caerudo = caerudoCnt>0 ? (caerudo/caerudoCnt) : 0;

        Vec3d result = trilinearInterpolation( rubToRgb, auredo, caerudo, rubedo );
        return (((int)result.x*255) >> 16 ) + (((int)result.y*255) >> 8 ) + ((int)result.z*255);
    }


    private static Vec3d linearInterpolation( Vec3d a, Vec3d b, float f ){
        return new Vec3d( a.x * f + b.x * (1-f), a.y * f + b.y * (1-f), a.z * f + b.z * (1-f) );
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
        return compound;
    }
    @Override
    public NBTTagCompound getUpdateTag() {
        return writeToNBT(new NBTTagCompound() );
    }
}
