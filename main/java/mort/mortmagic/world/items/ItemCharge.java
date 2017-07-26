package mort.mortmagic.world.items;

import java.util.HashMap;
import java.util.Map;

import mort.mortmagic.MortMagic;
import mort.mortmagic.spells.ISpell;
import mort.mortmagic.world.EntitySpellMissile;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemCharge extends Item{

	//@SideOnly(Side.CLIENT)
	public Map<ISpell,IIcon> iconMap = new HashMap<ISpell, IIcon>();
	
	public ItemCharge(){
		this.setMaxDamage(1000);
		this.setMaxStackSize(1);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if( !world.isRemote ){
			EntitySpellMissile fb = new EntitySpellMissile(world,player);
			fb.setSpellContents( getSpell(stack), player, stack.getMaxDamage() - stack.getItemDamage() );
			world.spawnEntityInWorld(fb);
		}
		player.inventory.setInventorySlotContents( player.inventory.currentItem , createContainedStack(stack));
		return stack;
	}

	@Override
	public void onUpdate(ItemStack stack, World p_77663_2_,Entity bearer, int p_77663_4_, boolean p_77663_5_) {
		if( bearer instanceof EntityPlayer ){ //not player, dunno what to do
			EntityPlayer plr = (EntityPlayer)bearer;
			if( plr.getHeldItem()!= stack ){
				for(int i = 0; i<plr.inventory.mainInventory.length;i++)
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
		getSpell( item ).cast(player, (float)player.posX, (float)player.posY, (float)player.posZ, player, player.worldObj, getMaxDamage()-item.getItemDamage() );
		return false;
	}

	@Override
	public boolean onLeftClickEntity(ItemStack item, EntityPlayer player,Entity entity) {
		System.out.println("touch cast");
		getSpell( item ).cast(player, (float)player.posX, (float)player.posY, (float)player.posZ, player, player.worldObj, getMaxDamage()-item.getItemDamage() );
		return true;
	}

	@Override
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
		// TODO Auto-generated method stub
		return super.onEntitySwing(entityLiving, stack);
	}

	public ItemStack create( ISpell spell, ItemStack implement ){
		ItemStack charge = new ItemStack(this);
		NBTTagCompound cmpd = new NBTTagCompound();
		cmpd.setString("RegisteredSpell", spell.getUnlocalizedName() );
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
			return ItemStack.loadItemStackFromNBT( (NBTTagCompound)stk.getTagCompound().getTag("implement") );
		else
			return null;
	}
	
	public ISpell getSpell( ItemStack stk ){
		if( stk.getTagCompound()==null )
			return null;
		return MortMagic.spellReg.getSpellByName( stk.getTagCompound().getString("RegisteredSpell") );
	}
	
	public void chargeUp(ItemStack stk, int amount){
		stk.setItemDamage( stk.getItemDamage()-1 );
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister p_94581_1_) {
		for( ISpell spell : MortMagic.spellReg.getAllSpells() ){
			iconMap.put(spell, p_94581_1_.registerIcon(spell.getTextureName()+"_charge" ));
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(ItemStack stack, int pass) {
		return iconMap.get( getSpell(stack) );
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIconIndex(ItemStack stack) {
		return iconMap.get( getSpell(stack) );
	}
	
	
}
