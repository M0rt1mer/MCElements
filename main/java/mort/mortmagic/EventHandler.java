package mort.mortmagic;

import mort.mortmagic.spells.Element;
import mort.mortmagic.spells.ISpell;
import mort.mortmagic.world.items.ItemScroll;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class EventHandler {


    @SubscribeEvent
	public void entityConstruction( EntityEvent.EntityConstructing constr ){
		
    	if( constr.entity instanceof EntityPlayer ){
	    	ExtendedPlayer ext = new ExtendedPlayer();
			ext.register( (EntityPlayer)constr.entity );
    	}
  /*  	else if( constr.entity instanceof EntitySpellMissile && constr.entity.getExtendedProperties("missile")==null ){
    		constr.entity.registerExtendedProperties("missile", new EntitySpellMissileData() );
    		System.out.println("Registering missile data");
    	}*/
    	
	}
	

	@SubscribeEvent
	public void onPlayerTick(PlayerEvent.LivingUpdateEvent evnt){
		if( !(evnt.entity instanceof EntityPlayer) || evnt.entityLiving.worldObj.isRemote )
			return;
		ExtendedPlayer dat = ExtendedPlayer.getExtendedPlayer((EntityPlayer)evnt.entityLiving);
		
		if( dat.castingMode > 0 ){
			EntityPlayer plr = (EntityPlayer)evnt.entity;
			if( dat.spellbook.getStackInSlot(dat.spellbookActive)==null ){
				System.out.println("No held spell");
				return;
			}
			Element elem = ItemScroll.getElement( dat.spellbook.getStackInSlot(dat.spellbookActive) );
			ISpell spell;
			
			if( plr.getHeldItem() != null && plr.getHeldItem().getItem() == Resource.charge ){
				spell = Resource.charge.getSpell( plr.getHeldItem() );
				if( MortMagic.spellReg.getSpellsElement(spell)!=elem ){
					System.out.println("other element");
					return;
				}
				if( dat.hasMana( spell.getCost() ) ){
					Resource.charge.chargeUp( plr.getHeldItem(), dat.castingMode );
					dat.drainMana( spell.getCost() );
				}
			}
			else{
				if(plr.getHeldItem()==null)
					spell = MortMagic.spellReg.getSpell( elem, null );
				else
					spell = MortMagic.spellReg.getSpell( elem, plr.getHeldItem().getItem() );
				if( dat.hasMana( spell.getCost() ) ){
					ItemStack stk = Resource.charge.create(spell, plr.getHeldItem() );
					plr.inventory.setInventorySlotContents( plr.inventory.currentItem , stk);
					dat.drainMana( spell.getCost() );
				}
			}
		}
		dat.syncStats();

	}

}
