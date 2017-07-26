package mort.mortmagic;

import mort.mortmagic.api.RobesRegistry;
import mort.mortmagic.inventory.InventorySpellbook;
import mort.mortmagic.net.MessageSyncStats;
import mort.mortmagic.net.NetworkManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class ExtendedPlayer implements IExtendedEntityProperties{

	public static final String IDENTIFIER = "spellData";
	
	private EntityPlayer plr;
	public InventorySpellbook spellbook;
	public int castingMode = 0;
	public int spellbookActive = 0;
	public int castCooldown = 0;
	public float mana;
	
	private float lastMana;
	private float lastSaturation;
	
	@Override
	public void saveNBTData(NBTTagCompound compound) {
		NBTTagCompound inv = new NBTTagCompound();
		spellbook.saveToNBT(inv);
		compound.setTag("spellbook", inv);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		spellbook = new InventorySpellbook( compound.getCompoundTag("spellbook") );
	}

	@Override
	public void init(Entity entity, World world) {
		if( spellbook==null )
			spellbook = new InventorySpellbook();
	}
	
	public void register(EntityPlayer plr){
		plr.registerExtendedProperties(IDENTIFIER, this);
		this.plr = plr;
	}
	
	public float gainMana(float gain){
		
		float maxMana = 0;
		for( int i = 0; i<4; i++ ){
			if( plr.inventory.armorInventory[i] != null ){
				RobesRegistry.RobeInfo rb = MortMagic.robes.getRobe(plr.inventory.armorInventory[i].getItem());
				if(rb!=null)
					maxMana += rb.maxMana;
			}
		}
		if(mana+gain > maxMana){
			gain = maxMana - mana;
		}
		mana += gain;
		return gain;
	}
	
	public void drainMana(float drain){
		if(this.mana > drain)
			mana -= drain;
		else{
			drain -= mana;
			mana = 0;
			plr.addExhaustion( drain*4 );
		}
	}
	
	public boolean hasMana(float drain){
		return (mana+plr.getFoodStats().getSaturationLevel()+plr.getFoodStats().getFoodLevel())>drain;
	}
	
	public void syncStats(){
		if( Math.abs(lastMana - mana)>=0.99f || Math.abs(plr.getFoodStats().getSaturationLevel()-lastSaturation)>0.99f ){
			lastMana = mana;
			lastSaturation = plr.getFoodStats().getSaturationLevel();
			if(this.plr instanceof EntityPlayerMP){
				NetworkManager.instance.sendTo( new MessageSyncStats(lastMana,lastSaturation), (EntityPlayerMP)plr);
			}
		}
	}
	
	public static ExtendedPlayer getExtendedPlayer(EntityPlayer plr){
		return (ExtendedPlayer)plr.getExtendedProperties(IDENTIFIER);
	}
	
}
