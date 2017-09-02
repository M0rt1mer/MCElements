package mort.mortmagic.common.net;

import io.netty.buffer.ByteBuf;
import mort.mortmagic.MortMagic;
import mort.mortmagic.common.SpellCaster;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;


public class MessageCast implements IMessage {

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
	public void toBytes(ByteBuf buf) {
		buf.writeByte( strength );
	}

	public static class MessageCastHandler implements IMessageHandler<MessageCast,IMessage>{

		@Override
		public IMessage onMessage(MessageCast message, MessageContext ctx) {
            ctx.getServerHandler().player.getServerWorld().addScheduledTask(() -> SpellCaster.getPlayerSpellcasting(ctx.getServerHandler().player).castingMode = message.strength);
			return null;
		}

	}


}
