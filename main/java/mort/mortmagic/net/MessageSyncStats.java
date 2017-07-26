package mort.mortmagic.net;

import io.netty.buffer.ByteBuf;
import mort.mortmagic.MortMagic;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageSyncStats implements IMessage, IMessageHandler<MessageSyncStats, IMessage> {

	float newMana;
	float newSatur;
	
	public MessageSyncStats() {
	}

	public MessageSyncStats(float newMana, float newSatur) {
		super();
		this.newMana = newMana;
		this.newSatur = newSatur;
	}



	@Override
	public IMessage onMessage(MessageSyncStats message, MessageContext ctx) {
		MortMagic.proxy.updatePlayerStats(newMana, message.newSatur);
		System.out.println("update stats "+newMana+" "+ message.newSatur);
		return null;
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
	
	

}
