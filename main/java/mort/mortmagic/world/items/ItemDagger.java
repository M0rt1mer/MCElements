package mort.mortmagic.world.items;

import mort.mortmagic.Resource;
import mort.mortmagic.api.SacrificeRegistry.ExpSacrifice;
import mort.mortmagic.sacrifice.IAltar;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.google.common.collect.Multimap;

public class ItemDagger extends ItemSword{

	boolean sacred;
	protected float damage;
	
	public ItemDagger(ToolMaterial p_i45356_1_, boolean sacred) {
		super(p_i45356_1_);
		this.sacred = sacred;
		damage = p_i45356_1_.getDamageVsEntity() + 1; //sword would have +4
	}

	@Override
	public Multimap getItemAttributeModifiers() {
        Multimap multimap = super.getItemAttributeModifiers();
        multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", (double)this.damage, 0));
        return multimap;
	}

	@Override
	public boolean hitEntity(ItemStack p_77644_1_, EntityLivingBase target,	EntityLivingBase attacker) {
		System.out.println(sacred);
		System.out.println( target.getHealth() );
		if( sacred && target.getHealth() <= 0){
			//MURDER
			System.out.println( target.getClass() );
			if( target instanceof EntityCow ){
				dropItem( target.worldObj, target.posX, target.posY, target.posZ, new ItemStack(Resource.mobDrop,1,0) );
			} 
			
		}
		
		return super.hitEntity(p_77644_1_, target, attacker);
	}
	
	private void dropItem( World wld, double x, double y, double z, ItemStack stk){
		EntityItem entItm = new EntityItem(wld, x, y, z, stk);
		wld.spawnEntityInWorld(entItm);
	}

	@Override
	public boolean onBlockDestroyed(ItemStack p_150894_1_, World world,
			Block block, int p_150894_4_, int p_150894_5_,
			int p_150894_6_, EntityLivingBase p_150894_7_) {
		
		super.onBlockDestroyed(p_150894_1_, world, block, p_150894_4_, p_150894_5_, p_150894_6_, p_150894_7_);
		if( block == Blocks.tallgrass && world.getBlockMetadata(p_150894_4_, p_150894_5_, p_150894_6_) == 2 && Math.abs( world.getCelestialAngle(0) - 0.5f ) < 0.05f ){
			ItemStack stk = new ItemStack( Resource.fern, 1 );
			EntityItem itm = new EntityItem( world, p_150894_4_, p_150894_5_, p_150894_6_, stk );
			world.spawnEntityInWorld(itm);
		}
		
		return true;
		
	}

	
	//-----------------------  SACRIFICE EXP
	@Override
	public boolean onItemUse(ItemStack p_77648_1_, EntityPlayer p_77648_2_,
			World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_,
			int p_77648_7_, float p_77648_8_, float p_77648_9_,
			float p_77648_10_) {
		if( p_77648_2_.experienceTotal < 10 )
			return false;
		TileEntity ent = p_77648_3_.getTileEntity(p_77648_4_, p_77648_5_, p_77648_6_) ;
		if( ent!=null && ent instanceof IAltar ){
			((IAltar)ent).addSacrifice( new ExpSacrifice(10) );
			p_77648_2_.addExperience(-10);
			return true;
		}
		return false;
	}
	

	@Override
	public boolean itemInteractionForEntity(ItemStack p_111207_1_,
			EntityPlayer p_111207_2_, EntityLivingBase ent) {
		if( p_111207_2_.experienceTotal < 10 )
			return false;
		if( ent!=null && ent instanceof IAltar ){
			((IAltar)ent).addSacrifice( new ExpSacrifice(10) );
			p_111207_2_.addExperience(-10);
			return true;
		}
		return false;
		
	}
	
	
	
}
