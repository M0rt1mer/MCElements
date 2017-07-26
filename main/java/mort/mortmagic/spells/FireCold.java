package mort.mortmagic.spells;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class FireCold extends BaseSpell{

	public FireCold(String unLocName) {
		super(unLocName);
		// TODO Auto-generated constructor stub
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
	public void cast(EntityLivingBase caster, float impactX, float impactY,
			float impactZ, EntityLivingBase impactEntity, World wld,
			float charge) {
		
	}
	
}
