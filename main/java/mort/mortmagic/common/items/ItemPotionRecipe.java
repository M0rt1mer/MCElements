package mort.mortmagic.common.items;

import com.sun.javafx.geom.Vec3f;
import mort.mortmagic.Content;
import mort.mortmagic.MortMagic;
import mort.mortmagic.common.potions.PotionColoringHelper;
import mort.mortmagic.common.potions.PotionRecipeRegistry;
import mort.mortmagic.common.tileentity.TileCauldron;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemPotionRecipe extends Item implements IItemColor {

    public boolean advanced;

    public ItemPotionRecipe(boolean advanced) {
        this.advanced = advanced;
    }

    public void addAspectsToItem(ItemStack stk, Vec3f color){
        NBTTagCompound dipping = new NBTTagCompound();
        dipping.setFloat( "r", color.x );
        dipping.setFloat( "a", color.y );
        dipping.setFloat( "c", color.z );
        stk.getTagCompound().setTag("dipping",dipping);
    }

    public Vec3f getDippingAspectsFromStack( ItemStack stk ){
        if(stk.hasTagCompound()) {
            NBTTagCompound dipping = stk.getTagCompound().getCompoundTag("dipping");
            return new Vec3f( dipping.getFloat("r"), dipping.getFloat("a"),dipping.getFloat("c") );
        }
        else
            return new Vec3f(0,0,0);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if( advanced && worldIn.getBlockState(pos).getBlock() == Content.cauldron ){
            addAspectsToItem( player.getHeldItem(hand), ((TileCauldron)worldIn.getTileEntity(pos)).getAspects() );
            return EnumActionResult.SUCCESS;
        }
        return EnumActionResult.PASS;
    }

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
        switch (tintIndex){
            case 1:     PotionRecipeRegistry.PotionRecipe rec = getRecipeFromStack(stack);
                        return PotionColoringHelper.getColorFromAspects( new Vec3f( rec.rubedo, rec.auredo, rec.caerudo ));
            case 2:     return PotionColoringHelper.getColorFromAspects( getDippingAspectsFromStack(stack));
            default:    return PotionColoringHelper.TINT_WHITE;
        }
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if(tab == this.getCreativeTab())
            for(PotionRecipeRegistry.PotionRecipe recipe : MortMagic.potionRecipeRegistry )
                items.add( createStackFromRecipe(recipe) );
    }
}
