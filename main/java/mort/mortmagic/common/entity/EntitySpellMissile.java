package mort.mortmagic.common.entity;

import mort.mortmagic.MortMagic;
import mort.mortmagic.common.spells.Spell;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
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
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class EntitySpellMissile extends EntityThrowable {
	
	public Spell spell;
	public float charge;

	public static DataParameter<String> param_spell = EntityDataManager.createKey( EntitySpellMissile.class, DataSerializers.STRING );

    public EntitySpellMissile(World worldIn) {
        super(worldIn);
		getDataManager().register( param_spell, "spl" );
    }

    public EntitySpellMissile(World worldIn, EntityLivingBase throwerIn, Spell spell, float charge) {
		super(worldIn, throwerIn);
		this.spell = spell;
		this.charge = charge;
        getDataManager().register( param_spell, spell.getRegistryName().toString() );
        setHeadingFromThrower( throwerIn, throwerIn.rotationPitch, throwerIn.rotationYaw, 0, 1, 1 );
	}

	public Spell getSpell(){
		if(spell==null)
			spell = GameRegistry.findRegistry( Spell.class ).getValue(new ResourceLocation(getDataManager().get(param_spell) ) );
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
		//getDataManager().register( param_spell, "spl" );
	}

	@Override
	protected void onImpact(RayTraceResult mov) {
		if(!getEntityWorld().isRemote){
			this.setDead();
			if( mov == null )
				spell.cast(getThrower(), getPositionVector(), null, this.getEntityWorld(),charge);
			else if( mov.typeOfHit ==  RayTraceResult.Type.ENTITY && mov.entityHit instanceof EntityLiving )
				spell.cast(getThrower(), mov.hitVec, (EntityLiving)mov.entityHit, this.getEntityWorld(),charge);
			else
				spell.cast(getThrower(), mov.hitVec, null, this.getEntityWorld(),charge);
		}
	}



	@Override
	public void writeEntityToNBT(NBTTagCompound tag) {
		super.writeEntityToNBT( tag );
        tag.setString("RegisteredSpell", spell.getRegistryName().toString() );
	}



	@Override
	public void readEntityFromNBT(NBTTagCompound tag) {
		super.readEntityFromNBT(tag);
		spell = GameRegistry.findRegistry( Spell.class).getValue( new ResourceLocation( tag.getString("RegisteredSpell") ) );
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
