package mort.mortmagic.world;

import mort.mortmagic.MortMagic;
import mort.mortmagic.spells.ISpell;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;

public class EntitySpellMissile extends EntityThrowable {
	
	public ISpell spell;
	public EntityLivingBase caster;
	public float charge;
	
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

	public void setSpellContents( ISpell spell, EntityLivingBase caster, int charge ){
		this.spell = spell;
		this.caster = caster;
		this.charge = charge;
		dataWatcher.updateObject(25, spell.getUnlocalizedName() );
	}
	
	public ISpell getSpell(){
		if(spell==null)
			spell = MortMagic.spellReg.getSpellByName( dataWatcher.getWatchableObjectString(25) );
		return spell;
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		
		MortMagic.proxy.spawnParticle(worldObj, posX, posY, posZ, 0, 0, 0, spell );
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataWatcher.addObject(25, "spl");
	}

	@Override
	protected void onImpact(MovingObjectPosition mov) {
		if(!worldObj.isRemote){
			this.setDead();
			if( mov == null )
				spell.cast(caster, (float)posX, (float)posY, (float)posZ, null, this.worldObj,charge);
			else if( mov.typeOfHit == MovingObjectType.ENTITY && mov.entityHit instanceof EntityLiving )
				spell.cast(caster, mov.blockX, mov.blockY, mov.blockZ, (EntityLiving)mov.entityHit, this.worldObj,charge);
			else
				spell.cast(caster, mov.blockX, mov.blockY, mov.blockZ, null, this.worldObj,charge);
		}
	}



	@Override
	public void writeEntityToNBT(NBTTagCompound p_70014_1_) {
		super.writeEntityToNBT(p_70014_1_);
		p_70014_1_.setString("RegisteredSpell", spell.getUnlocalizedName() );
	}



	@Override
	public void readEntityFromNBT(NBTTagCompound p_70037_1_) {
		super.readEntityFromNBT(p_70037_1_);
		spell = MortMagic.spellReg.getSpellByName( p_70037_1_.getString("RegisteredSpell") );
	}
	
	
	
	//registered to EntitySpellMissile in EventHandler
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
