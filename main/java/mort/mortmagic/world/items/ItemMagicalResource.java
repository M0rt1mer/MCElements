package mort.mortmagic.world.items;

import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemMagicalResource extends Item{

	public String[] classes;
	
	public ItemMagicalResource( String[] names ){
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
		this.classes = names;
	}

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
	    if(this.isInCreativeTab(tab))
            for(int i = 0; i<classes.length;i++)
                items.add(new ItemStack(this,1,i));
    }

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return super.getUnlocalizedName(stack)+"."+classes[stack.getItemDamage()];
	}

	@SideOnly(Side.CLIENT)
	public void initModels() {
		for (int i =0;i<classes.length;i++) {
		    ResourceLocation rl = new ResourceLocation( getRegistryName() + "_" + classes[i] );
			ModelBakery.registerItemVariants(this, rl);
			ModelLoader.setCustomModelResourceLocation(this, i, new ModelResourceLocation(rl, "inventory") );
		}
	}
	
}
