package mort.mortmagic.common.potions;

import mort.mortmagic.common.SpellCaster;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;

public class PotionManaRegen extends Potion{

    public PotionManaRegen(boolean isBadEffectIn, int liquidColorIn) {
        super(isBadEffectIn, liquidColorIn);
    }

    @Override
    public void performEffect(EntityLivingBase entityLivingBaseIn, int amplifier) {
        SpellCaster caster = SpellCaster.getPlayerSpellcasting(entityLivingBaseIn);
        if(caster != null)
            caster.mana += 2;
    }

}
