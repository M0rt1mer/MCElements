package mort.mortmagic.spells;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public interface ISpell {

	int getCooldown();
	
	float getCost();
	
	void cast( EntityLivingBase caster, float impactX, float impactY, float impactZ, EntityLivingBase impactEntity, World wld, float charge );
	
	String getUnlocalizedName();
	
	String getTextureName();
	
}
