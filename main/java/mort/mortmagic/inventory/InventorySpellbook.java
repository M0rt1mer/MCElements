package mort.mortmagic.inventory;

import mort.mortmagic.world.items.ItemScroll;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.ITextComponent;

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
			ItemStack stack = new ItemStack(cmpd);
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
	public boolean isEmpty() {
		return false;
	}

	@Override
	public ItemStack getStackInSlot(int id) {
		return (id>=stacks.length)?null:stacks[id];
	}

	@Override
	public ItemStack decrStackSize(int id, int change) {
		if(stacks[id]==null)
			return null;
		
		if( change >= stacks[id].getCount() ){
			ItemStack stk = stacks[id];
			stacks[id] = null;
			return stk;
		}else{
			return stacks[id].splitStack(change);
			
		}
		
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		return null;
	}

	@Override
	public void setInventorySlotContents(int id, ItemStack stack) {
		stacks[id] = stack;
	}

	@Override
	public int getInventoryStackLimit() {
		return 0;
	}

	@Override
	public void markDirty() {

	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		return false;
	}

	@Override
	public void openInventory(EntityPlayer player) {

	}

	@Override
	public void closeInventory(EntityPlayer player) {

	}

	@Override
	public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
		return p_94041_2_.getItem() instanceof ItemScroll;
	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {

	}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void clear() {

	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public ITextComponent getDisplayName() {
		return null;
	}
}
