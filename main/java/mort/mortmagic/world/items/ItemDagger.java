package mort.mortmagic.world.items;

import mort.mortmagic.Resource;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

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

        ItemStack stk = getBlockDrops(worldIn,state,pos,entityLiving);
        if(stk != null){
            EntityItem itm = new EntityItem( worldIn, pos.getX()+0.5f, pos.getY()+0.5f, pos.getZ()+0.5f, stk);
            worldIn.spawnEntity(itm);
        }
		return true;
	}

	private ItemStack getBlockDrops( World world, IBlockState state, BlockPos pos, EntityLivingBase entLiving ){
		if( state.getBlock() == Blocks.TALLGRASS ){
			if( Math.abs( world.getCelestialAngle(0) - 0.5f ) < 0.05f && world.getMoonPhase() == 2 ){
                return new ItemStack( Resource.metaItem, 1,2 );
			}
		} else if( state.getBlock() == Blocks.LEAVES ){
		    if( world.rand.nextInt(4) == 0 ){
		        return new ItemStack( Resource.metaItem,1, 6);
            }
        }
        return null;
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
