package mort.mortmagic;

import mort.mortmagic.api.RobesRegistry;
import mort.mortmagic.inventory.InventorySpellbook;
import mort.mortmagic.net.MessageSyncStats;
import mort.mortmagic.net.NetworkManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class ExtendedPlayer implements ICapabilitySerializable<NBTTagCompound> {

    @CapabilityInject(ExtendedPlayer.class)
    public static Capability<ExtendedPlayer> EXTENDED_PLAYER_CAPABILITY = null;

    public ExtendedPlayer(EntityPlayer plr) {
        this.plr = plr;
        spellbook = new InventorySpellbook();
    }

    public static ResourceLocation extPlayerResLoc = new ResourceLocation( "mortmagic", "extPlayer" );

	private EntityPlayer plr;
	public InventorySpellbook spellbook;
	public int castingMode = 0;
	public int spellbookActive = 0;
	public int castCooldown = 0;
	public float mana;
	
	private float lastMana;
	private float lastSaturation;

	public float gainMana(float gain){
		
		float maxMana = 0;
		for( int i = 0; i<4; i++ ){
			if( plr.inventory.armorInventory.get(i) != null ){
				RobesRegistry.RobeInfo rb = MortMagic.robes.getRobe(plr.inventory.armorInventory.get(i).getItem());
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

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return (capability == EXTENDED_PLAYER_CAPABILITY);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == EXTENDED_PLAYER_CAPABILITY) {
            return (T)this;
        }
        return null;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound tag = new NBTTagCompound();
	    NBTTagCompound inv = new NBTTagCompound();
        spellbook.saveToNBT(inv);
        tag.setTag("spellbook", inv);
        return tag;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        spellbook = new InventorySpellbook( nbt.getCompoundTag("spellbook") );
    }
}
