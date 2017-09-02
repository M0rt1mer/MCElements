package mort.mortmagic.common.items;


import mort.mortmagic.client.IInitializeMyOwnModels;
import mort.mortmagic.common.spells.Spell;
import mort.mortmagic.common.entity.EntitySpellMissile;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;
import java.util.List;

public class ItemCharge extends Item implements IInitializeMyOwnModels{
	
	public ItemCharge(){
		this.setMaxDamage(1000);
		this.setMaxStackSize(1);
	}

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand handIn) {
	    ItemStack stack = player.getHeldItem(handIn);
		if( !world.isRemote ){
			EntitySpellMissile fb = new EntitySpellMissile(world,player);
			fb.setSpellContents( getSpell(stack), player, stack.getMaxDamage() - stack.getItemDamage() );
			world.spawnEntity(fb);
		}
		player.inventory.setInventorySlotContents( player.inventory.currentItem , createContainedStack(stack));
		return new ActionResult<>(EnumActionResult.SUCCESS, stack);
	}

	@Override
	public void onUpdate(ItemStack stack, World p_77663_2_,Entity bearer, int p_77663_4_, boolean p_77663_5_) {
		if( bearer instanceof EntityPlayer ){ //not player, dunno what to do
			EntityPlayer plr = (EntityPlayer)bearer;
			if( plr.getHeldItem(EnumHand.MAIN_HAND) != stack ){
				for(int i = 0; i<plr.inventory.mainInventory.size();i++)
					if( plr.inventory.getStackInSlot(i)==stack ){
						plr.inventory.setInventorySlotContents(i, createContainedStack(stack) );
						return;
					}
			}
			
		}
	}

	@Override
	public boolean onDroppedByPlayer(ItemStack item, EntityPlayer player) {
		
		player.inventory.setInventorySlotContents( player.inventory.currentItem , createContainedStack(item));
		getSpell( item ).cast(player, player.getPositionVector(), player, player.getEntityWorld(), getMaxDamage()-item.getItemDamage() );
		return false;
	}

	@Override
	public boolean onLeftClickEntity(ItemStack item, EntityPlayer player,Entity entity) {
		System.out.println("touch cast");
		getSpell( item ).cast(player, player.getPositionVector(), player, player.getEntityWorld(), getMaxDamage()-item.getItemDamage() );
		return true;
	}

	@Override
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
		// TODO Auto-generated method stub
		return super.onEntitySwing(entityLiving, stack);
	}

	public ItemStack create( Spell spell, ItemStack implement ){
		ItemStack charge = new ItemStack(this);
		NBTTagCompound cmpd = new NBTTagCompound();
		cmpd.setString("RegisteredSpell", spell.getRegistryName().toString() );
		if(implement!=null){
			NBTTagCompound it = new NBTTagCompound();
			implement.writeToNBT(it);
			cmpd.setTag("implement", it);
		}
		charge.setTagCompound(cmpd);
		charge.setItemDamage(999);
		return charge;
	}
	
	public ItemStack createContainedStack( ItemStack stk ){
		if( stk.getTagCompound().hasKey("implement") )
			return new ItemStack( (NBTTagCompound)stk.getTagCompound().getTag("implement") );
		else
			return null;
	}
	
	public Spell getSpell(ItemStack stk ){
		return GameRegistry.findRegistry( Spell.class ).getValue( new ResourceLocation( stk.getTagCompound().getString("RegisteredSpell") ) );
	}
	
	public void chargeUp(ItemStack stk, int amount){
		stk.setItemDamage( stk.getItemDamage()-1 );
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void initModels() {
	    List<Spell> allSpells = GameRegistry.findRegistry( Spell.class ).getValues();
        HashMap<ResourceLocation,ModelResourceLocation> models = new HashMap<>();

        for (Spell spl : allSpells ) {
            models.put( spl.getRegistryName(), new ModelResourceLocation(
                    spl.getRegistryName().getResourceDomain() + ":spell_" + spl.getRegistryName().getResourcePath() + "_charge", "inventory" ) );
        }

		ModelBakery.registerItemVariants(this, models.values().toArray(new ModelResourceLocation[allSpells.size()]) );

		ModelLoader.setCustomMeshDefinition(this, stack -> {
            if( stack.hasTagCompound() && stack.getTagCompound().hasKey("RegisteredSpell") )
                return models.get( stack.getTagCompound().getString("RegisteredSpell") );
            else
                return null;
        });
	}
	
	
}
