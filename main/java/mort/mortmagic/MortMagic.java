package mort.mortmagic;

import mort.mortmagic.api.PotionRecipeRegistry;
import mort.mortmagic.api.RobesRegistry;
import mort.mortmagic.api.RuneDictionary;
import mort.mortmagic.api.SacrificeRegistry;
import mort.mortmagic.common.CommonProxy;
import mort.mortmagic.api.PotionIngredientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid=MortMagic.MODID,name="Elements")
public class MortMagic {

	public static final String MODID = "mortmagic";

	@Mod.Instance
	public static MortMagic instance;
	
	@SidedProxy(clientSide="mort.mortmagic.ClientProxy",serverSide="mort.mortmagic.common.CommonProxy")
	public static CommonProxy proxy;

	public static RobesRegistry robes;
	public static SacrificeRegistry sacrReg;
	public static RuneDictionary dictionary;
	public static PotionIngredientRegistry potReg;
	public static PotionRecipeRegistry potionRecipeRegistry;
	
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
