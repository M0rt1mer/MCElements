package mort.mortmagic.common.spells;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class BaseLife extends Spell {

	public static final float HEALTH_PER_CHARGE = 0.5f;

	public BaseLife(ResourceLocation registryName) {
		super(registryName);
	}

	@Override
	public int getCooldown() {
		return 0;
	}

	@Override
	public float getCost() {
		return 0.1f;
	}

    @Override
    protected float entityCast(EntityLivingBase entity, float charge, SpellCastData dat) {
        //System.out.println("Cast on "+entity+" with charge "+charge);
        if( entity.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD ){
            float damageAmount = Math.min( charge/2*HEALTH_PER_CHARGE, entity.getHealth() );
            entity.attackEntityFrom(DamageSource.causePlayerDamage( (EntityPlayer)dat.caster), damageAmount );
            return damageAmount/HEALTH_PER_CHARGE;
        }
        else {
            float healAmount = Math.min(charge / 2 * HEALTH_PER_CHARGE, entity.getMaxHealth() - entity.getHealth());
            entity.setHealth(entity.getHealth() + healAmount);
            return healAmount / HEALTH_PER_CHARGE;
        }
    }
}
