package mort.mortmagic.common.potions;

import mort.mortmagic.Content;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;

public class PotionBleed extends Potion {
    public PotionBleed(boolean isBadEffectIn, int liquidColorIn) {
        super(isBadEffectIn, liquidColorIn);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return duration % (20>>amplifier) == 0;
    }

    @Override
    public void performEffect(EntityLivingBase entityLivingBaseIn, int amplifier) {
        entityLivingBaseIn.attackEntityFrom(Content.DAMAGE_BLEED, 1.0F);
    }
}
