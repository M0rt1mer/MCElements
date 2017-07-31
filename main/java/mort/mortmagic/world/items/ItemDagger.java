package mort.mortmagic.world.items;

import com.sun.jna.platform.win32.Guid;
import mort.mortmagic.Resource;
import mort.mortmagic.api.SacrificeRegistry.ExpSacrifice;
import mort.mortmagic.sacrifice.IAltar;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
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

	/*@Override
	public Multimap getItemAttributeModifiers(EntityEquipmentSlot slot) {
        Multimap multimap = super.getItemAttributeModifiers( slot );
        multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", (double)this.damage, 0));
        return multimap;
	}*/


	@Override
	public boolean hitEntity(ItemStack p_77644_1_, EntityLivingBase target,	EntityLivingBase attacker) {
		System.out.println(sacred);
		System.out.println( target.getHealth() );
		if( sacred && target.getHealth() <= 0){
			//MURDER
			System.out.println( target.getClass() );
			if( target instanceof EntityCow ){
				dropItem( target.getEntityWorld(), target.posX, target.posY, target.posZ, new ItemStack(Resource.metaItem,1,6) );
			} 
			
		}
		
		return super.hitEntity(p_77644_1_, target, attacker);
	}
	
	private void dropItem( World wld, double x, double y, double z, ItemStack stk){
		EntityItem entItm = new EntityItem(wld, x, y, z, stk);
		wld.spawnEntity(entItm);
	}

	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving) {
		super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);

		if( state.getBlock() == Blocks.TALLGRASS && Math.abs(worldIn.getCelestialAngle(0) - 0.5f) < 0.05f ){
			ItemStack stk = new ItemStack( Resource.metaItem, 1, 3 );
			EntityItem itm = new EntityItem( worldIn, pos.getX(), pos.getY(), pos.getZ(), stk );
			worldIn.spawnEntity(itm);
		}
		return true;
	}

	//-----------------------  SACRIFICE EXP
	/*@Override
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
	}*/
	

	/*@Override
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
		
	}*/
	
	
	
}
