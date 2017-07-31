package mort.mortmagic.spells;

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

	@Override
	public void cast(EntityLivingBase caster, Vec3d position, EntityLivingBase impactEntity, World wld, float charge) {

	}

	/*
	@Override
	public void cast(EntityLivingBase caster, float impactX, float impactY,	float impactZ, EntityLivingBase impactEntity, World wld, float charge) {
		
		float entityFract = 0; //fraction of power that goes to entity
		float range = (float)Math.max(1, Math.cbrt(charge) );
		List list;
		if(impactEntity!=null){
			entityCast( impactEntity, charge*0.75f );
			charge *= 0.25f;
			list = wld.getEntitiesWithinAABBExcludingEntity(impactEntity, AxisAlignedBB.getBoundingBox(impactX-range,impactY-range ,impactZ-range ,impactX+range ,impactY+range ,impactZ+range ),
					new IEntitySelector() {
						@Override
						public boolean isEntityApplicable(Entity p_82704_1_) {
							return p_82704_1_ instanceof EntityLivingBase;
						}
					} );
		}
		
		list = wld.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(impactX-range,impactY-range ,impactZ-range ,impactX+range ,impactY+range ,impactZ+range ) ); 
		Collections.sort(list, new EntityDistanceComparator(impactX, impactY, impactZ));
		for( Object obj : list ){
			EntityLivingBase ent = (EntityLivingBase) obj;
			float fract = (float)Math.min(0.6, 1/ent.getDistanceSq(impactX, impactY, impactZ) );
			entityCast(ent, charge *fract );
			charge = 1 - charge*fract;
		}
		
		ArrayList<IMagicallyGrowable> set = new ArrayList<IMagicallyGrowable>();
		//Vec3 base = Vec3.createVectorHelper(impactX, impactY, impactZ);
		while(charge>0){
			
			set.clear();
			int x = (int) (impactX + (Math.random()*2-1)*range);
			int y = (int) (impactY + (Math.random()*2-1)*range);
			int z = (int) (impactZ + (Math.random()*2-1)*range);
			
			//MovingObjectPosition mov = wld.rayTraceBlocks(base, Vec3.createVectorHelper(x, y, z));
			
			for( IMagicallyGrowable grw : MortMagic.life.registry ){
				if( grw.isApplicable(wld, x, y, z) )
					set.add(grw);
			}
			if(set.size()>0){
				IMagicallyGrowable grw = set.get( wld.rand.nextInt(set.size()) ); 
				if( Math.random()*grw.getCost() < charge )
					grw.apply(wld, x, y, z);
				charge -= grw.getCost(); 
			}
			else
				charge -= 0.2; //countdown
		}
		
	}
	*/
	
	protected float entityCast( EntityLivingBase entity, float charge ){
		System.out.println("Cast on "+entity+" with charge "+charge);
		return charge/2;
	}
	
	@Override
	protected float blockCast(World wld, int x, int y, int z, float charge, SpellCastData dat) {
		/*ArrayList<IMagicallyGrowable> set = new ArrayList<IMagicallyGrowable>();
		set.clear();
		
		for( IMagicallyGrowable grw : MortMagic.life.registry ){
			if( grw.isApplicable(wld, x, y, z) )
				set.add(grw);
		}
		if(set.size()>0){
			IMagicallyGrowable grw = set.get( wld.rand.nextInt(set.size()) ); 
			if( Math.random()*grw.getCost() < charge )
				grw.apply(wld, x, y, z);
			return grw.getCost(); 
		}*/
		return 0;
	}
/*
	private class EntityDistanceComparator implements Comparator<Entity>{
		float x, y,z;

		public EntityDistanceComparator(float x, float y, float z) {
			super();
			this.x = x;
			this.y = y;
			this.z = z;
		}

		@Override
		public int compare(Entity arg0, Entity arg1) {
			return Double.compare(arg0.getDistanceSq(x, y, z), arg1.getDistanceSq(x, y, z) );
		}
		
		
	}*/
	
}
