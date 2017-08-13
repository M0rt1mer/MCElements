package mort.mortmagic.common.items;

import mort.mortmagic.common.spells.Element;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;

/**
 * Item scroll that can hold any existing element. Element+level are stored as NBT tags, and used to render different spell model
 */
public class ItemScroll extends Item {

	public ItemScroll( ){
		setMaxStackSize( 1 );
		//setHasSubtypes(true);
	}

	public ItemStack createScroll( Element elem, int level){
		ItemStack stk = new ItemStack( this, 1 );
		NBTTagCompound tag = new NBTTagCompound();
		tag.setString( "element", elem.getRegistryName().toString() );
		tag.setInteger( "level", level );
		stk.setTagCompound( tag );
		return stk;
	}

	public static Element getElement( ItemStack stk ){
        return GameRegistry.findRegistry(Element.class).getValue( new ResourceLocation( stk.getTagCompound().getString("element") ) );
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        for (Element ele : GameRegistry.findRegistry( Element.class ).getValues() ) {
            for( int i = 0; i<3; i++ ){
                items.add( createScroll( ele, i ) );
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        HashMap<ResourceLocation,ModelResourceLocation[]> models = new HashMap<>();

        for (Element ele : GameRegistry.findRegistry( Element.class ).getValues() ) {
            ModelResourceLocation[] locations = new ModelResourceLocation[3];
            for(int i = 0;i<locations.length;i++){
                locations[i] = new ModelResourceLocation( ele.getRegistryName() + "_scroll_" + i,"inventory" );
            }
            models.put( ele.getRegistryName(), locations );
            ModelBakery.registerItemVariants( this, locations );
        }

        ModelLoader.setCustomMeshDefinition(this, new ItemMeshDefinition() {
            @Override
            public ModelResourceLocation getModelLocation(ItemStack stack) {
                return models.get( new ResourceLocation( stack.getTagCompound().getString("element") ) )[stack.getTagCompound().getInteger("level")];
            }
        });
    }

}
