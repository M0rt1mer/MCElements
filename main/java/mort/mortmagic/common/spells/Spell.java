package mort.mortmagic.common.spells;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class Spell extends IForgeRegistryEntry.Impl<Spell> {

    public Spell(ResourceLocation registryName) {
        this.setRegistryName(registryName);
    }

    private String textureName;

    public abstract int getCooldown();

    public abstract float getCost();

    public abstract void cast(EntityLivingBase caster, Vec3d position, EntityLivingBase impactEntity, World wld, float charge);

    public String getTextureName(){
		return textureName;
	}
	public Spell setTextureName(String name){
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
			list = wld.getEntitiesInAABBexcluding(impactEntity, new AxisAlignedBB(impactX-range,impactY-range ,impactZ-range ,impactX+range ,impactY+range ,impactZ+range ),
					p_82704_1_ -> p_82704_1_ instanceof EntityLivingBase);
		}
		
		list = wld.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(impactX-range,impactY-range ,impactZ-range ,impactX+range ,impactY+range ,impactZ+range ) );
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
