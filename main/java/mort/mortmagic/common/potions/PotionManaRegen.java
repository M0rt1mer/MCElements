package mort.mortmagic.common.potions;

import mort.mortmagic.common.SpellCaster;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

public class PotionManaRegen extends Potion{

    public PotionManaRegen(boolean isBadEffectIn, int liquidColorIn) {
        super(isBadEffectIn, liquidColorIn);
    }

    @Override
    public void performEffect(EntityLivingBase entityLivingBaseIn, int amplifier) {
        if( entityLivingBaseIn.world.isRemote )
            return;
        SpellCaster caster = SpellCaster.getPlayerSpellcasting(entityLivingBaseIn);
        if(caster != null)
            caster.gainMana( 8 ); // two "points"
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }
}
