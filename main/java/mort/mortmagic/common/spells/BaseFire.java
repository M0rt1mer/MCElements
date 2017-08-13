package mort.mortmagic.common.spells;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class BaseFire extends Spell {

	private static final float fireDamageCost = 3;

	public BaseFire(ResourceLocation registryName) {
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
    public void cast(EntityLivingBase caster, Vec3d position, EntityLivingBase impactEntity, World wld, float charge) {

    }

    @Override
	protected float entityCast(EntityLivingBase base, float charge, SpellCastData dat) {
		if(base.isImmuneToFire() || base.isPotionActive( Potion.getPotionFromResourceLocation("minecraft:fireResist") ))
			return 0;
		base.attackEntityFrom( DamageSource.IN_FIRE, charge/2/fireDamageCost - ((int)(charge/2/fireDamageCost)) );
		base.setFire( (int)(charge/2/fireDamageCost) );
		return charge;
	}

	/*@Override
	protected float blockCast(World world, int x, int y, int z, float charge, SpellCastData dat) {
		if( !world.isAirBlock(x, y, z) )
			return 0;
		for( int i = 0; i<6; i++){
			if( !world.isAirBlock(x+ForgeDirection.VALID_DIRECTIONS[i].offsetX, y+ForgeDirection.VALID_DIRECTIONS[i].offsetY,z+ForgeDirection.VALID_DIRECTIONS[i].offsetZ) ){
				if( charge>=(Math.random()*5) )
					world.setBlock(x, y, z, Blocks.fire );
				return 5;
			}
		}
		return 0;
	}*/

	/*
	@Override
	public void cast(EntityLivingBase caster, float impactX, float impactY,	float impactZ, EntityLivingBase impactEntity, World wld, float charge) {
		System.out.println("fire spell cast");
	}*/
	
	
	
	
}
