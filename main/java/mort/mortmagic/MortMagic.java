package mort.mortmagic;

import mort.mortmagic.api.RobesRegistry;
import mort.mortmagic.api.RuneDictionary;
import mort.mortmagic.api.SacrificeRegistry;
import mort.mortmagic.common.CommonProxy;
import mort.mortmagic.common.runes.RuneCircle;
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
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event){
		robes = new RobesRegistry();
		sacrReg = new SacrificeRegistry();
		dictionary = new RuneDictionary();
		proxy.preInit();
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event){
		proxy.init();
	}

	@Mod.EventHandler
	public void  postInit(FMLPostInitializationEvent event){
	    proxy.postInit();
    }

}
