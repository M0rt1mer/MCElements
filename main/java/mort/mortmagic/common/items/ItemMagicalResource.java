package mort.mortmagic.common.items;

import mort.mortmagic.MortMagic;
import mort.mortmagic.client.IInitializeMyOwnModels;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemMagicalResource extends Item implements IInitializeMyOwnModels{

	public String[] classes;

	public ItemMagicalResource( String[] names ){
		this();
		//this.setMaxDamage(names.length);
		this.classes = names;
	}

	public <E extends Enum<E>> ItemMagicalResource( Class<E> enm ){
		this();
        E[] values = enm.getEnumConstants();
        classes = new String[values.length];
        for( int i = 0; i < values.length; i++ )
            classes[i] = values[i].toString().toLowerCase();
		//this.setMaxDamage(classes.length);
    }

    private ItemMagicalResource(){
		this.setHasSubtypes(true);
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

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if( I18n.hasKey(getUnlocalizedName(stack)+".description" ) )
            tooltip.add( I18n.format(getUnlocalizedName(stack)+".description" ) );
    }

    @Override
	@SideOnly(Side.CLIENT)
	public void initModels() {
		for (int i =0;i<classes.length;i++) {
		    ResourceLocation rl = new ResourceLocation( getRegistryName() + "_" + classes[i] );
			ModelBakery.registerItemVariants(this, rl);
			ModelLoader.setCustomModelResourceLocation(this, i, new ModelResourceLocation(rl, "inventory") );
		}
	}
	
}
