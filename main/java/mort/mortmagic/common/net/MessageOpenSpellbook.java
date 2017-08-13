package mort.mortmagic.common.net;

import io.netty.buffer.ByteBuf;
import mort.mortmagic.MortMagic;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageOpenSpellbook implements IMessage, IMessageHandler<MessageOpenSpellbook,IMessage> {

	@Override
	public IMessage onMessage(MessageOpenSpellbook message, MessageContext ctx) {
		EntityPlayerMP plr = ctx.getServerHandler().player;
		plr.openGui(MortMagic.instance, 0, plr.getEntityWorld(), (int)plr.posX, (int)plr.posY, (int)plr.posZ);
		return null;
	}

	@Override
	public void fromBytes(ByteBuf buf) {}

	@Override
	public void toBytes(ByteBuf buf) {}

}
