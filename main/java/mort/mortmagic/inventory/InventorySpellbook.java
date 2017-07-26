package mort.mortmagic.inventory;

import mort.mortmagic.world.items.ItemScroll;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class InventorySpellbook implements IInventory {

	public ItemStack stacks[];

	public InventorySpellbook(){
		stacks = new ItemStack[6];
	}

	public InventorySpellbook(NBTTagCompound data){
		this();
		NBTTagList list = data.getTagList("spellbook", 10 ); //10-compound
		for( int i = 0; i<list.tagCount(); i++ ){
			NBTTagCompound cmpd = list.getCompoundTagAt(i);
			int slot = cmpd.getInteger("Slot");
			ItemStack stack = ItemStack.loadItemStackFromNBT(cmpd);
			stacks[slot] = stack;
		}

	}

	public void saveToNBT(NBTTagCompound cmpd){
		NBTTagList list = new NBTTagList();
		for( int i = 0; i<stacks.length; i++ ){
			if(stacks[i]!=null){
				NBTTagCompound comp = new NBTTagCompound();
				comp.setByte("Slot", (byte)i);
				stacks[i].writeToNBT(comp);
				list.appendTag(comp);
			}
		}
		cmpd.setTag("spellbook", list);
	}

	@Override
	public int getSizeInventory() {
		return stacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int id) {
		return (id>=stacks.length)?null:stacks[id];
	}

	@Override
	public ItemStack decrStackSize(int id, int change) {
		if(stacks[id]==null)
			return null;
		
		if( change >= stacks[id].stackSize ){
			ItemStack stk = stacks[id];
			stacks[id] = null;
			return stk;
		}else{
			return stacks[id].splitStack(change);
			
		}
		
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int id) {
		if (this.stacks[id] != null) {
			ItemStack itemstack = this.stacks[id];
			this.stacks[id] = null;
			return itemstack;
		} else {
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int id, ItemStack stack) {
		stacks[id] = stack;
	}

	@Override
	public String getInventoryName() {
		return "Spellbook";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public void markDirty() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void openInventory() {
		// TODO Auto-generated method stub

	}

	@Override
	public void closeInventory() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
		return p_94041_2_.getItem() instanceof ItemScroll;
	}



}
