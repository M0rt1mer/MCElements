package mort.mortmagic.common.spells;
/*
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class SimplePotionSpell extends Spell {

	Potion effect;
	int amplif;
	float ratio;
	
	
	public SimplePotionSpell(String unLocName, Potion effect, int apl, float powerToDuration) {
		super(unLocName);
		this.effect = effect;
		this.amplif = apl;
		this.ratio = powerToDuration;
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
	protected float entityCast(EntityLivingBase base, float charge,
			SpellCastData dat) {
		base.addPotionEffect( new PotionEffect( effect.id, amplif, (int) (charge*ratio) ) );
		return charge;
	}

	@Override
	protected float blockCast(World world, int x, int y, int z, float charge,
			SpellCastData dat) {
		//burn the mana
		return charge;
	}

	
	

	
	
	
}
*/