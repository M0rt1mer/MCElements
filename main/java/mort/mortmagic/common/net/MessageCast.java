package mort.mortmagic.common.net;

import io.netty.buffer.ByteBuf;
import mort.mortmagic.ExtendedPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;


public class MessageCast implements IMessage, IMessageHandler<MessageCast,IMessage> {

	public int strength;
	
	
	public MessageCast() {}

	public MessageCast(int strength) {
		this.strength = strength;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		strength = buf.readByte();
	}

	@Override
	public IMessage onMessage(MessageCast message, MessageContext ctx) {
		//System.out.println("Message "+message.strength);
		ctx.getServerHandler().player.getCapability(ExtendedPlayer.EXTENDED_PLAYER_CAPABILITY, EnumFacing.DOWN).castingMode = message.strength;
		return null;
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeByte( strength );
	}
	
}
