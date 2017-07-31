package mort.mortmagic;

import mort.mortmagic.api.RobesRegistry;
import mort.mortmagic.api.SacrificeRegistry;
import mort.mortmagic.net.NetworkManager;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import javax.annotation.Nullable;
import java.util.concurrent.Callable;

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

	    // Create extended player capability. As it is an internal capavbility, neither factory nor storage are needed to work
		CapabilityManager.INSTANCE.register(ExtendedPlayer.class, new Capability.IStorage<ExtendedPlayer>() {
            @Nullable
            @Override
            public NBTBase writeNBT(Capability<ExtendedPlayer> capability, ExtendedPlayer instance, EnumFacing side) {
                return null;
            }

            @Override
            public void readNBT(Capability<ExtendedPlayer> capability, ExtendedPlayer instance, EnumFacing side, NBTBase nbt) {
            }
        }, new Callable<ExtendedPlayer>() {
            @Override
            public ExtendedPlayer call() throws Exception {
                return null;
            }
        });

		robes = new RobesRegistry();
		sacrReg = new SacrificeRegistry();
		
		proxy.preInit();
		//handles creating all registries
		NetworkManager.init();
		
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event){
		NetworkRegistry.INSTANCE.registerGuiHandler(this, proxy);

		proxy.init();
	}
	
}
