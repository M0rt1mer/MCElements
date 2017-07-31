package mort.mortmagic.world.items;

import mort.mortmagic.spells.Spell;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
	public String getUnlocalizedName(ItemStack p_77667_1_) {
		return super.getUnlocalizedName(p_77667_1_)+"."+classes[p_77667_1_.getItemDamage()];
	}

	@SideOnly(Side.CLIENT)
	public void initModels() {
		ModelResourceLocation[] models = new ModelResourceLocation[classes.length];

		for (int i =0;i<classes.length;i++)
			models[i] = new ModelResourceLocation( getRegistryName() + "_" + classes[i], "inventory" );

		ModelBakery.registerItemVariants(this, models );

		ModelLoader.setCustomMeshDefinition(this, stack -> models[ stack.getItemDamage() ]);
	}
	
}
