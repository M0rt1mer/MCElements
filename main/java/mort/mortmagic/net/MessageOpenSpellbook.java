package mort.mortmagic.net;

import io.netty.buffer.ByteBuf;
import mort.mortmagic.MortMagic;
import net.minecraft.entity.player.EntityPlayerMP;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageOpenSpellbook implements IMessage, IMessageHandler<MessageOpenSpellbook,IMessage>{

	@Override
	public IMessage onMessage(MessageOpenSpellbook message, MessageContext ctx) {
		EntityPlayerMP plr = ctx.getServerHandler().playerEntity;
		plr.openGui(MortMagic.instance, 0, plr.worldObj, (int)plr.posX, (int)plr.posY, (int)plr.posZ);
		return null;
	}

	@Override
	public void fromBytes(ByteBuf buf) {}

	@Override
	public void toBytes(ByteBuf buf) {}

}
