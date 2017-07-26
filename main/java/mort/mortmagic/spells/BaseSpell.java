package mort.mortmagic.spells;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import mort.mortmagic.MortMagic;
import mort.mortmagic.spells.life.IMagicallyGrowable;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public abstract class BaseSpell implements ISpell{

	private String unLocName;
	private String textureName;
	
	public BaseSpell(String unLocName) {
		this.unLocName = unLocName;
	}

	@Override
	public String getUnlocalizedName() {
		return unLocName;
	}

	public String getTextureName(){
		return textureName;
	}
	
	public BaseSpell setTextureName(String name){
		textureName = name;
		return this;
	}
	
	//cast helper - casts on
	public void cast(EntityLivingBase caster, float impactX, float impactY,	float impactZ, EntityLivingBase impactEntity, World wld, float charge) {
		SpellCastData dat = new SpellCastData(caster, impactX, impactY, impactZ, impactEntity, wld);
		float entityFract = 0; //fraction of power that goes to entity
		float range = (float)Math.max(1, Math.cbrt(charge) );
		List list;
		if(impactEntity!=null){
			charge -= entityCast( impactEntity, charge*0.75f, dat );
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
			System.out.println("entity cast "+fract);
			charge -= entityCast(ent, charge *fract, dat );
			
		}
		
		while(charge>0){
			int x = (int) (impactX + Math.sin(Math.random()*Math.PI)*range);
			int y = (int) (impactY + Math.sin(Math.random()*Math.PI)*range);
			int z = (int) (impactZ + Math.sin(Math.random()*Math.PI)*range);
			charge -= Math.max(0.1f, blockCast(wld,x,y,z,charge, dat) );
		}
		
	}
	
	protected float entityCast(EntityLivingBase base, float charge, SpellCastData dat){
		return 0;
	}
	
	protected float blockCast( World world, int x, int y, int z, float charge, SpellCastData dat){
		return 0;
	}
	
	protected class SpellCastData{
		public EntityLivingBase caster;
		public float x;
		public float y;
		public float z;
		public EntityLivingBase target;
		public World wld;
		public SpellCastData(EntityLivingBase caster, float x, float y,float z, EntityLivingBase target, World wld) {
			this.caster = caster;
			this.x = x;
			this.y = y;
			this.z = z;
			this.target = target;
			this.wld = wld;
		}
		
	}
	
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
		
		
	}
	
	
}
