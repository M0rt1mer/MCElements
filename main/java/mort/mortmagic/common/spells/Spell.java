package mort.mortmagic.common.spells;

import mort.mortmagic.MortMagic;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
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

    public String getTextureName(){
		return textureName;
	}
	public Spell setTextureName(String name){
		textureName = name;
		return this;
	}
	
	//cast helper - casts on
	public void cast(EntityLivingBase caster, Vec3d impact, EntityLivingBase impactEntity, World wld, float charge) {
		SpellCastData dat = new SpellCastData(caster, impact.x, impact.y, impact.z, impactEntity, wld);
		float entityFract = 0; //fraction of power that goes to entity
		float range = (float)Math.max(1, Math.cbrt(charge) );
		List list;
		if(impactEntity!=null){
			charge -= entityCast( impactEntity, charge*0.75f, dat );
			list = wld.getEntitiesInAABBexcluding(impactEntity, new AxisAlignedBB(impact.x-range,impact.y-range ,impact.z-range ,impact.x+range ,impact.y+range ,impact.z+range ),
					p_82704_1_ -> p_82704_1_ instanceof EntityLivingBase);
		}
		
		list = wld.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(impact.x-range,impact.y-range ,impact.z-range ,impact.x+range ,impact.y+range ,impact.z+range ) );
		Collections.sort(list, new EntityDistanceComparator(impact.x, impact.y, impact.z));
		for( Object obj : list ){
			EntityLivingBase ent = (EntityLivingBase) obj;
			float fract = (float)Math.min(0.6, 1/ent.getDistanceSq(impact.x, impact.y, impact.z) );
			System.out.println("entity cast "+fract);
			charge -= entityCast(ent, charge *fract, dat );
			
		}
		
		while(charge>0){
			int x = (int) (impact.x + Math.sin(Math.random()*Math.PI)*range);
			int y = (int) (impact.y + Math.sin(Math.random()*Math.PI)*range);
			int z = (int) (impact.z + Math.sin(Math.random()*Math.PI)*range);

			charge -= Math.max(0.1f, blockCast(wld,new BlockPos(x,y,z),EnumFacing.getFacingFromVector( (float)(x-impact.x), (float)(y-impact.y), (float)(z-impact.z) ),charge, dat) );
		}
		
	}

	/**
	 * Applies spells effect on given entity
	 * @return
	 */
	protected float entityCast(EntityLivingBase base, float charge, SpellCastData dat){
		return 0;
	}

	/**
	 * applies the spells effect on given block.
	 * @return
	 */
	protected float blockCast(World world, BlockPos pos, EnumFacing castFromDir, float charge, SpellCastData dat){
        IBlockState state = world.getBlockState(pos);
	    for(RegistrySpellBlockTransformation.IBlockTransformation trn : MortMagic.spellBlockTransformation.tranforms.get(getBlockTransformSpell())){
	        IBlockState newBlockState = trn.transform(state);
	        if( newBlockState != null ){ // if null, doesn't apply.
	            world.setBlockState( pos, newBlockState );
	            return 1; // TODO: impleemnt some cost
            }
        }
        for(RegistrySpellBlockTransformation.IBlockGrowth grw : MortMagic.spellBlockTransformation.growths.get(getBlockTransformSpell())){
            IBlockState newBlockState = grw.growth(state, castFromDir);
            if( newBlockState != null ){ // if null, doesn't apply.
                world.setBlockState( pos, newBlockState );
                return 1; // TODO: impleemnt some cost
            }
        }
		return 0;
	}

    /**
     * Gets the spell which should be used for block transformations. Used by default blockCast.
     * This method allows spells to "inherit" block transformation lists of other spells.
     */
	protected Spell getBlockTransformSpell(){
	    return this;
    }

	protected class SpellCastData{
		public EntityLivingBase caster;
		public double x;
		public double y;
		public double z;
		public EntityLivingBase target;
		public World wld;
		public SpellCastData(EntityLivingBase caster, double x, double y,double z, EntityLivingBase target, World wld) {
			this.caster = caster;
			this.x = x;
			this.y = y;
			this.z = z;
			this.target = target;
			this.wld = wld;
		}
		
	}
	
	private class EntityDistanceComparator implements Comparator<Entity>{
        double x, y,z;

		public EntityDistanceComparator(double x, double y, double z) {
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
