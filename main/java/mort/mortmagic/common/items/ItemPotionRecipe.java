package mort.mortmagic.common.items;

import com.sun.javafx.geom.Vec3f;
import mort.mortmagic.MortMagic;
import mort.mortmagic.common.potions.PotionColoringHelper;
import mort.mortmagic.common.potions.PotionRecipeRegistry;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

public class ItemPotionRecipe extends Item implements IItemColor {

    public ItemStack createStackFromRecipe(PotionRecipeRegistry.PotionRecipe recipe){
        ItemStack stk = new ItemStack( this );
        NBTTagCompound cmp = new NBTTagCompound();
        cmp.setString( "recipe", recipe.location.toString() );
        stk.setTagCompound( cmp );
        return stk;
    }

    public PotionRecipeRegistry.PotionRecipe getRecipeFromStack( ItemStack stk ){
        return MortMagic.potionRecipeRegistry.getRecipe( new ResourceLocation( stk.getTagCompound().getString("recipe") ) );
    }

    @Override
    public int getColorFromItemstack(ItemStack stack, int tintIndex) {
        if(tintIndex == 1){
            PotionRecipeRegistry.PotionRecipe rec = getRecipeFromStack(stack);
            return PotionColoringHelper.getColorFromAspects( new Vec3f( rec.rubedo, rec.auredo, rec.caerudo ));
        }
        return PotionColoringHelper.TINT_WHITE;
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if(tab == this.getCreativeTab())
            for(PotionRecipeRegistry.PotionRecipe recipe : MortMagic.potionRecipeRegistry )
                items.add( createStackFromRecipe(recipe) );
    }
}
