package mort.mortmagic.world;

import mort.mortmagic.MortMagic;
import mort.mortmagic.spells.Spell;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class EntitySpellMissile extends EntityThrowable {
	
	public Spell spell;
	public EntityLivingBase caster;
	public float charge;

	public static DataParameter<String> param_spell = EntityDataManager.createKey( EntitySpellMissile.class, DataSerializers.STRING );
	
	public EntitySpellMissile(World p_i1778_1_, double p_i1778_2_,
			double p_i1778_4_, double p_i1778_6_) {
		super(p_i1778_1_, p_i1778_2_, p_i1778_4_, p_i1778_6_);
		// TODO Auto-generated constructor stub
	}



	public EntitySpellMissile(World p_i1777_1_, EntityLivingBase p_i1777_2_) {
		super(p_i1777_1_, p_i1777_2_);
		// TODO Auto-generated constructor stub
	}

	public EntitySpellMissile(World p_i1776_1_) {
		super(p_i1776_1_);
		// TODO Auto-generated constructor stub
	}

	public void setSpellContents(Spell spell, EntityLivingBase caster, int charge ){
		this.spell = spell;
		this.caster = caster;
		this.charge = charge;
		getDataManager().set(param_spell, spell.getRegistryName().toString() );
	}
	
	public Spell getSpell(){
		if(spell==null)
			spell = GameRegistry.findRegistry( Spell.class).getValue(new ResourceLocation(getDataManager().get(param_spell) ) );
		return spell;
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		
		MortMagic.proxy.spawnParticle(getEntityWorld(), posX, posY, posZ, 0, 0, 0, spell );
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		getDataManager().register( param_spell, "spl" );
	}

	@Override
	protected void onImpact(RayTraceResult mov) {
		if(!getEntityWorld().isRemote){
			this.setDead();
			if( mov == null )
				spell.cast(caster, getPositionVector(), null, this.getEntityWorld(),charge);
			else if( mov.typeOfHit ==  RayTraceResult.Type.ENTITY && mov.entityHit instanceof EntityLiving )
				spell.cast(caster, mov.hitVec, (EntityLiving)mov.entityHit, this.getEntityWorld(),charge);
			else
				spell.cast(caster, mov.hitVec, null, this.getEntityWorld(),charge);
		}
	}



	@Override
	public void writeEntityToNBT(NBTTagCompound p_70014_1_) {
		super.writeEntityToNBT(p_70014_1_);
		p_70014_1_.setString("RegisteredSpell", spell.getRegistryName().toString() );
	}



	@Override
	public void readEntityFromNBT(NBTTagCompound p_70037_1_) {
		super.readEntityFromNBT(p_70037_1_);
		spell = GameRegistry.findRegistry( Spell.class).getValue( new ResourceLocation( p_70037_1_.getString("RegisteredSpell") ) );
	}
	
	
	
	//registered to EntitySpellMissile in ElementsEventHandler
	/*public static class EntitySpellMissileData implements IExtendedEntityProperties {

		
		
		@Override
		public void saveNBTData(NBTTagCompound compound) {
			System.out.println("Saving NBT for missile");
			compound.setString( "spell", spell.getUnlocalizedName() );
			compound.setFloat("charge", charge);
		}

		@Override
		public void loadNBTData(NBTTagCompound compound) {
			spell = MortMagic.spellReg.getSpellByName( compound.getString( "spell") );
			charge = compound.getFloat( "charge" );
		}

		@Override
		public void init(Entity entity, World world) {}
		
		
	}*/
	
}
