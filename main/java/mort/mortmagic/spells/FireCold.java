package mort.mortmagic.spells;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class FireCold extends Spell {

	public FireCold(ResourceLocation registryName) {
		super(registryName);
	}

	@Override
	public int getCooldown() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getCost() {
		// TODO Auto-generated method stub
		return 0.1f;
	}

	@Override
	public void cast(EntityLivingBase caster, Vec3d position, EntityLivingBase impactEntity, World wld, float charge) {

	}

	@Override
	public void cast(EntityLivingBase caster, float impactX, float impactY,
			float impactZ, EntityLivingBase impactEntity, World wld,
			float charge) {
		
	}
	
}
