package mort.mortmagic;

import mort.mortmagic.api.RobesRegistry;
import mort.mortmagic.api.SacrificeRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@Mod(modid=MortMagic.MODID,name="Elements")
public class MortMagic {

	public static final String MODID = "mortmagic";

	@Mod.Instance
	public static MortMagic instance;
	
	@SidedProxy(clientSide="mort.mortmagic.ClientProxy",serverSide="mort.mortmagic.CommonProxy")
	public static CommonProxy proxy;

	public static RobesRegistry robes;
	public static SacrificeRegistry sacrReg;
	
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event){
		robes = new RobesRegistry();
		sacrReg = new SacrificeRegistry();
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
