package mort.mortmagic.world.items;

import mort.mortmagic.spells.Element;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemScroll extends Item {

	public ItemScroll( ){
		setMaxStackSize( 1 );
	}

	public ItemStack createScroll( Element elem, int level){
		ItemStack stk = new ItemStack( this, 1 );
		NBTTagCompound tag = new NBTTagCompound();
		tag.setString( "spell", elem.getRegistryName().toString() );
		tag.setInteger( "level", level );
		stk.setTagCompound( tag );
		return stk;
	}

	public static Element getElement( ItemStack stk ){
        return GameRegistry.findRegistry(Element.class).getValue( new ResourceLocation( stk.getTagCompound().getString("spell") ) );
    }
}
