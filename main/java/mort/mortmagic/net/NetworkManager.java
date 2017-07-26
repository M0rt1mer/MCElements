package mort.mortmagic.net;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class NetworkManager {

	public static final SimpleNetworkWrapper instance = NetworkRegistry.INSTANCE.newSimpleChannel( "mortmagic" );
	
	public static void init(){
		instance.registerMessage(MessageCast.class, MessageCast.class, 0, Side.SERVER);
		instance.registerMessage(MessageOpenSpellbook.class, MessageOpenSpellbook.class, 1, Side.SERVER);
		instance.registerMessage(MessageSyncStats.class, MessageSyncStats.class, 2, Side.CLIENT);
	}
	
	
}
