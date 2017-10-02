package mort.mortmagic.common.items;

import mort.mortmagic.client.gui.GuiScreenGrimoire;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

public class ItemGrimoire extends Item {

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {

        if( FMLCommonHandler.instance().getSide().equals(Side.CLIENT) )
            FMLCommonHandler.instance().showGuiScreen( new GuiScreenGrimoire( playerIn ));

        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn)); //consume event

    }

}
