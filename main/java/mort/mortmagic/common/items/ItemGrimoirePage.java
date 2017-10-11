package mort.mortmagic.common.items;

import mort.mortmagic.common.SpellCaster;
import mort.mortmagic.common.grimoire.GrimoirePage;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

public class ItemGrimoirePage extends Item {

    public ItemGrimoirePage( ){
        setMaxStackSize( 1 );
        setHasSubtypes(true);
    }

    public ItemStack createItemStack(GrimoirePage page, byte level){
        ItemStack stk = new ItemStack(this,1);
        NBTTagCompound tag = new NBTTagCompound();
        stk.setTagCompound(tag);
        tag.setString("grimoire_page",page.getRegistryName().toString());
        tag.setByte( "grimoire_page_level", level );
        return stk;
    }

    public GrimoirePage getPageFromStack(ItemStack stk ){
        final NBTTagCompound tagCompound = stk.getTagCompound();
        if( tagCompound.hasKey("grimoire_page") ){
            final IForgeRegistry<GrimoirePage> registry = GameRegistry.findRegistry(GrimoirePage.class);
            ResourceLocation resLoc = new ResourceLocation( tagCompound.getString("grimoire_page") );
            return registry.getValue(resLoc);
        }
        return null;
    }

    public byte getLevelFromStack( ItemStack stk ){
        final NBTTagCompound tagCompound = stk.getTagCompound();
        if( tagCompound.hasKey("grimoire_page_level") )
            return tagCompound.getByte( "grimoire_page_level" );
        return -1;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if(!worldIn.isRemote){
            final GrimoirePage page = getPageFromStack(playerIn.getHeldItem(handIn));
            final byte level = getLevelFromStack(playerIn.getHeldItem(handIn));
            SpellCaster.getPlayerSpellcasting( playerIn ).unlockGrimoirePage( page, level);
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        for (GrimoirePage page : GameRegistry.findRegistry( GrimoirePage.class ).getValues() ) {
            for( byte i = 0; i<3; i++ ){
                items.add( createItemStack( page, i ) );
            }
        }
    }

}
