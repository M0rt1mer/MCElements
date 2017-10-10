package mort.mortmagic.common.net;

import io.netty.buffer.ByteBuf;
import mort.mortmagic.MortMagic;
import mort.mortmagic.common.grimoire.GrimoirePage;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import java.nio.charset.Charset;
import java.util.HashMap;

public class MessageSyncGrimoire implements IMessage {

	public HashMap<GrimoirePage,Byte> pageUpdates;

	private static final Charset charset = Charset.forName("UTF-8");

    public MessageSyncGrimoire() {
        pageUpdates = new HashMap<>();
    }

    public MessageSyncGrimoire(HashMap<GrimoirePage, Integer> pageUpdates) {
        this.pageUpdates = (HashMap<GrimoirePage,Byte>)pageUpdates.clone();
    }


	@Override
	public void fromBytes(ByteBuf buf) {
        IForgeRegistry<GrimoirePage> registry = GameRegistry.findRegistry(GrimoirePage.class);
        char[] name = new char[Byte.MAX_VALUE];
        int numUpdates = buf.readShort();
        for( int i = 0; i<numUpdates; i++ ){
            byte level = buf.readByte();
            int length = buf.readInt();
            ResourceLocation key = new ResourceLocation( (String) buf.readCharSequence( length, charset ) );
            if(registry.containsKey(key))
                pageUpdates.put( registry.getValue(key) , level );
        }
	}

	@Override
	public void toBytes(ByteBuf buf) {
        if( pageUpdates.size() > Short.MAX_VALUE )
            throw new RuntimeException( "More than "+Short.MAX_VALUE+" pages updated at once." );
        buf.writeShort( pageUpdates.size() );
        for( GrimoirePage page : pageUpdates.keySet() ) {
            buf.writeByte(pageUpdates.get(page)); //variant
            int index = buf.writerIndex();
            buf.writeInt(0); //make space for the length
            buf.setInt( index, buf.writeCharSequence( page.getRegistryName().toString(), charset ) ); //write length of sequence
        }
	}

	public static class MessageSyncStatsHandler implements IMessageHandler<MessageSyncGrimoire, IMessage>{

        //does nothing on server
	    @Override
        public IMessage onMessage(MessageSyncGrimoire message, MessageContext ctx) {
            MortMagic.proxy.handle_syncGrimoireMessage(message);
            return null;
        }
    }
	

}
