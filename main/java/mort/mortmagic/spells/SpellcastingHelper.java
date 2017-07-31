package mort.mortmagic.spells;

import mort.mortmagic.ExtendedPlayer;
import mort.mortmagic.MortMagic;
import mort.mortmagic.Resource;
import mort.mortmagic.api.ElementAndItemToSpellMapping;
import mort.mortmagic.world.items.ItemScroll;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

public abstract class SpellcastingHelper {


    public static void resolvePlayerSpellcasting(EntityPlayer plr, ExtendedPlayer dat){


        if( dat.spellbook.getStackInSlot(dat.spellbookActive)==null ){
            System.out.println("No held spell");
            return;
        }
        Element elem = ItemScroll.getElement( dat.spellbook.getStackInSlot(dat.spellbookActive) );
        Spell spell;

        if( plr.getHeldItem(EnumHand.MAIN_HAND) != null && plr.getHeldItem(EnumHand.MAIN_HAND).getItem() == Resource.charge ){
            spell = Resource.charge.getSpell( plr.getHeldItem(EnumHand.MAIN_HAND) );
            if( dat.hasMana( spell.getCost() ) ){
                Resource.charge.chargeUp( plr.getHeldItem(EnumHand.MAIN_HAND), dat.castingMode );
                dat.drainMana( spell.getCost() );
            }
        }
        else{
            spell = ElementAndItemToSpellMapping.getSpellByElementAndItem( elem, plr.getHeldItem(EnumHand.MAIN_HAND) );
            if( dat.hasMana( spell.getCost() ) ){
                ItemStack stk = Resource.charge.create(spell, plr.getHeldItem(EnumHand.MAIN_HAND) );
                plr.inventory.setInventorySlotContents( plr.inventory.currentItem , stk);
                dat.drainMana( spell.getCost() );
            }
        }
        dat.syncStats();
    }

}
