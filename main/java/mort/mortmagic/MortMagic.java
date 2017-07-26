package mort.mortmagic;

import sun.reflect.ReflectionFactory.GetReflectionFactoryAction;
import mort.mortmagic.api.LifeGrowthRegistry;
import mort.mortmagic.api.RobesRegistry;
import mort.mortmagic.api.SacrificeRegistry;
import mort.mortmagic.api.SpellRegistry;
import mort.mortmagic.net.NetworkManager;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(modid="mortmagic",name="Mort's Minecraft Magic")
public class MortMagic {

	@Instance
	public static MortMagic instance;
	
	@SidedProxy(clientSide="mort.mortmagic.ClientProxy",serverSide="mort.mortmagic.CommonProxy")
	public static CommonProxy proxy;
	
	public static SpellRegistry spellReg;
	public static LifeGrowthRegistry life;
	public static RobesRegistry robes;
	public static SacrificeRegistry sacrReg;
	
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event){
		
		spellReg = new SpellRegistry();
		life = new LifeGrowthRegistry();
		robes = new RobesRegistry();
		sacrReg = new SacrificeRegistry();
		
		proxy.preInit();
		NetworkManager.init();
		
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event){
		NetworkRegistry.INSTANCE.registerGuiHandler(this, proxy);
		
		Resource.registerPotions();
		Resource.registerItems();
		Resource.registerRecipes();
		Resource.registerSpells();
		proxy.init();
	}
	
}
