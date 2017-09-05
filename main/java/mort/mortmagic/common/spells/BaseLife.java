package mort.mortmagic.common.spells;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class BaseLife extends Spell {

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

	protected float entityCast( EntityLivingBase entity, float charge ){
		System.out.println("Cast on "+entity+" with charge "+charge);
		return charge/2;
	}
	

	
}
