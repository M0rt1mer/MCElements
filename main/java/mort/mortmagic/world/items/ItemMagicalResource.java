package mort.mortmagic.world.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemMagicalResource extends Item{

	public String[] classes;
	public IIcon[] icons;
	
	public ItemMagicalResource( String[] names ){
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
		this.classes = names;
	}

	@Override
	public void registerIcons(IIconRegister map) {
		icons = new IIcon[classes.length];
		for( int i = 0; i<classes.length;i++)
			icons[i] = map.registerIcon( this.getIconString()+"_"+classes[i] );
	}

	@Override
	public IIcon getIconFromDamage(int dam) {
		return icons[dam];
	}

	@Override
	public void getSubItems(Item a, CreativeTabs t, List list) {
		for(int i = 0; i<classes.length;i++)
			list.add(new ItemStack(a,1,i));
	}

	@Override
	public String getUnlocalizedName(ItemStack p_77667_1_) {
		return super.getUnlocalizedName(p_77667_1_)+"."+classes[p_77667_1_.getItemDamage()];
	}

	
	
}
