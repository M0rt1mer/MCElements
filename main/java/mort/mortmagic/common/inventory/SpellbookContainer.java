package mort.mortmagic.common.inventory;

import mort.mortmagic.ExtendedPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

public class SpellbookContainer extends Container{

	InventoryPlayer plrInv;
	InventorySpellbook inv;
	
	public SpellbookContainer(EntityPlayer plr) {
		super();
		plrInv = plr.inventory;
		inv = plr.getCapability(ExtendedPlayer.EXTENDED_PLAYER_CAPABILITY, EnumFacing.DOWN).spellbook;
		
		for (int i = 0; i < 3; ++i){
			for (int j = 0; j < 9; ++j){
				this.addSlotToContainer(new Slot(plrInv, j + (i + 1) * 9, 9 + j * 18, 31 + i * 18));
			}
		}
		for (int i = 0; i < 9; ++i){
			this.addSlotToContainer(new Slot(plrInv, i, 9 + i * 18, 88));
		}
		
		for(int i = 0; i<inv.getSizeInventory();i++)
			addSlotToContainer(new Slot( inv, i, 9+18*i,9 ));
		
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer p_75145_1_) {
		return true;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(slotID);

        return itemstack;
	}
	
}
