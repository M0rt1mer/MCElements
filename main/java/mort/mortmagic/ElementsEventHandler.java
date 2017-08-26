package mort.mortmagic;

import mort.mortmagic.common.SpellCaster;
import mort.mortmagic.common.spells.SpellcastingHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ElementsEventHandler {

    @SubscribeEvent
	public void attachCapability( AttachCapabilitiesEvent<Entity> constr ) {

		if (constr.getObject() instanceof EntityPlayer) {
			constr.addCapability( SpellCaster.extPlayerResLoc, new SpellCaster( (EntityPlayer) constr.getObject()) );
		}

	}

	@SubscribeEvent
	public void onPlayerTick(PlayerEvent.LivingUpdateEvent evnt){
		if( !(evnt.getEntity() instanceof EntityPlayer) || evnt.getEntityLiving().getEntityWorld().isRemote )
			return;
		EntityPlayer plr = (EntityPlayer)evnt.getEntity();
		SpellCaster dat = plr.getCapability( SpellCaster.SPELLCASTER_CAPABILITY, EnumFacing.DOWN );
		
		if( dat.castingMode > 0 ) {
            SpellcastingHelper.resolvePlayerSpellcasting(plr, dat);
        }
	}

}
