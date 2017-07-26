package mort.mortmagic.world.items;

import mort.mortmagic.Resource;
import mort.mortmagic.spells.Element;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemScroll extends Item {

	Element elem;
	
	
	public ItemScroll(String unlocalizedName){
		setUnlocalizedName(unlocalizedName);
		setMaxDamage(100);
		setMaxStackSize( 1 );
	}
	
	public ItemScroll setElement(Element e){
		this.elem = e;
		return this;
	}
	
	
	public static Element getElement( ItemStack stk ){
		
		return ((ItemScroll)stk.getItem()).elem;
	}
	
	public static int getMaxLevel( ItemStack stk ){
		return 1;
	}
	
}
