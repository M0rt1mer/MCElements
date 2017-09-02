package mort.mortmagic.common.net;

import io.netty.buffer.ByteBuf;
import mort.mortmagic.MortMagic;
import mort.mortmagic.common.SpellCaster;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageSyncStats implements IMessage {

	public float newMana;
    public float newSatur;

    public MessageSyncStats() {
    }

    public MessageSyncStats(float newMana, float newSatur) {
		this.newMana = newMana;
		this.newSatur = newSatur;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		newMana = buf.readFloat();
		newSatur = buf.readFloat();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeFloat( newMana );
		buf.writeFloat( newSatur );
	}

	public static class MessageSyncStatsHandler implements IMessageHandler<MessageSyncStats, IMessage>{

	    @Override
        public IMessage onMessage(MessageSyncStats message, MessageContext ctx) {
            MortMagic.proxy.handle_syncStatsMessage( message );
            System.out.println("update stats "+message.newMana+" "+ message.newSatur);
            return null;
        }
    }
	

}
