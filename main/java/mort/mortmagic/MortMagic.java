package mort.mortmagic;

import mort.mortmagic.common.potions.PotionRecipeRegistry;
import mort.mortmagic.common.spells.ElementAndItemToSpellMapping;
import mort.mortmagic.obsolete.RobesRegistry;
import mort.mortmagic.common.runes.RuneDictionary;
import mort.mortmagic.obsolete.SacrificeRegistry;
import mort.mortmagic.common.CommonProxy;
import mort.mortmagic.common.potions.PotionIngredientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

@Mod(modid=MortMagic.MODID,name="Elements")
public class MortMagic {

	public static final String MODID = "mortmagic";

	@Mod.Instance
	public static MortMagic instance;
	
	@SidedProxy(clientSide="mort.mortmagic.client.ClientProxy",serverSide="mort.mortmagic.common.CommonProxy")
	public static CommonProxy proxy;

	public static final SimpleNetworkWrapper networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel( "mortmagic" );

	//<editor-fold desc="Custom registries, initialized along with forge registries in event">
	public static RobesRegistry robes;
	public static SacrificeRegistry sacrReg;
	public static RuneDictionary dictionary;
	public static PotionIngredientRegistry potReg;
	public static PotionRecipeRegistry potionRecipeRegistry;
	public static ElementAndItemToSpellMapping spellMapping;
	//</editor-fold>
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event){
        Content.preInit();
		proxy.preInit();
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event){
		Content.init();
	    proxy.init();
	}

	@Mod.EventHandler
	public void  postInit(FMLPostInitializationEvent event){
	    proxy.postInit();
    }

}
