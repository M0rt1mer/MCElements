package mort.mortmagic.common.tileentity;

import mort.mortmagic.MortMagic;
import mort.mortmagic.common.potions.PotionIngredientRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

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


    public int getColor(){

        if( items.size() > 0 )
            return 564524;

        return 34355 ;
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
