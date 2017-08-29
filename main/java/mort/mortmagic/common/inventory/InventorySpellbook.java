package mort.mortmagic.common.inventory;

import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class InventorySpellbook extends InventoryBasic {

	public InventorySpellbook(){
		super( "Spellbook", false, 6 );
	}

	public static InventorySpellbook fromTag(NBTTagCompound data){
	    InventorySpellbook inv = new InventorySpellbook();
		NBTTagList list = data.getTagList("spellbook", 10 ); //10-compound
		for( int i = 0; i<list.tagCount(); i++ ){
			NBTTagCompound cmpd = list.getCompoundTagAt(i);
			int slot = cmpd.getInteger("Slot");
            inv.setInventorySlotContents( slot, new ItemStack(cmpd) );
		}
        return inv;
	}

	public void saveToNBT(NBTTagCompound cmpd){
		NBTTagList list = new NBTTagList();
		for( int i = 0; i < getSizeInventory(); i++ ){
		    ItemStack stk = getStackInSlot(i);
			if( stk!=null ){
				NBTTagCompound comp = new NBTTagCompound();
				comp.setByte("Slot", (byte)i);
				stk.writeToNBT(comp);
				list.appendTag(comp);
			}
		}
		cmpd.setTag("spellbook", list);
	}

}
