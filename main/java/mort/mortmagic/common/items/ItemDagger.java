package mort.mortmagic.common.items;

import com.google.common.collect.Multimap;
import mort.mortmagic.Content;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemDagger extends Item{

    public final Item.ToolMaterial material;

	public ItemDagger( Item.ToolMaterial material ) {
	    this.material = material;
	}

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        stack.damageItem(1, attacker);
        return true;
    }

	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving) {
        stack.damageItem(1, entityLiving );
		super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);
		if( !worldIn.isRemote ) {
			ItemStack stk = getBlockDrops(worldIn, state, pos, entityLiving);
			if (stk != null) {
				EntityItem itm = new EntityItem(worldIn, pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f, stk);
				worldIn.spawnEntity(itm);
			}
		}
		return true;
	}

	private ItemStack getBlockDrops( World world, IBlockState state, BlockPos pos, EntityLivingBase entLiving ){
		if( state.getBlock() == Blocks.TALLGRASS ){
			if( Math.abs( world.getCelestialAngle(0) - 0.5f ) < 0.05f && world.getMoonPhase() == 2 ){
                return new ItemStack( Content.metaItem, 1,2 );
			}
		} else if( state.getBlock() == Blocks.LEAVES ){
		    if( world.rand.nextInt(4) == 0 ){
		        return new ItemStack( Content.metaItem,1, 6);
            }
        }
        return null;
	}

    /**
     * Returns True is the item is renderer in full 3D when hold.
     */
    @SideOnly(Side.CLIENT)
    public boolean isFull3D()
    {
        return true;
    }

    @Override
    public int getItemEnchantability()
    {
        return this.material.getEnchantability();
    }

    /**
     * Return whether this item is repairable in an anvil.
     */
    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
    {
        ItemStack mat = this.material.getRepairItemStack();
        if (!mat.isEmpty() && net.minecraftforge.oredict.OreDictionary.itemMatches(mat, repair, false)) return true;
        return super.getIsRepairable(toRepair, repair);
    }

    @Override
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot)
    {
        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);

        if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
        {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", (double)this.material.getDamageVsEntity() + 1, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -2.4000000953674316D, 0));
        }

        return multimap;
    }
	
}
