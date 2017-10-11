package mort.mortmagic.common;

import mort.mortmagic.MortMagic;
import mort.mortmagic.common.grimoire.GrimoirePage;
import mort.mortmagic.common.inventory.InventorySpellbook;
import mort.mortmagic.common.net.MessageSyncGrimoire;
import mort.mortmagic.common.net.MessageSyncStats;
import mort.mortmagic.obsolete.RobesRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

import javax.annotation.Nonnull;
import java.util.HashMap;

public class SpellCaster implements ICapabilitySerializable<NBTTagCompound> {

    @CapabilityInject(SpellCaster.class)
    public static Capability<SpellCaster> SPELLCASTER_CAPABILITY = null;

    public SpellCaster(EntityPlayer plr) {
        this.plr = plr;
        spellbook = new InventorySpellbook();
    }

    public static ResourceLocation extPlayerResLoc = new ResourceLocation( "mortmagic", "extPlayer" );

    public static SpellCaster getPlayerSpellcasting( Entity plr ){
    	return plr.hasCapability(SPELLCASTER_CAPABILITY,EnumFacing.DOWN) ? plr.getCapability(SPELLCASTER_CAPABILITY, EnumFacing.DOWN) : null;
	}

	//---------------  PERSISTENT

	public InventorySpellbook spellbook;
    public int spellbookActive = 0;
    private float mana; // 4 mana equals one icon (which equals one point of hunger) - mana is equivalent to exhaustion
    public HashMap<GrimoirePage,Byte> knownPages = new HashMap<>();

    //--------------- NONPERSISTENT
    private EntityPlayer plr;
    public int castingMode = 0;
	public int castCooldown = 0;
    private float lastMana;
    private float lastSaturation;
    private HashMap<GrimoirePage,Byte> unsyncedPages = new HashMap<>();

	public float gainMana(float gain){
		
		float maxMana = 0;
		for( int i = 0; i<4; i++ ){
			if( plr.inventory.armorInventory.get(i) != null ){
				RobesRegistry.RobeInfo rb = MortMagic.robes.getRobe(plr.inventory.armorInventory.get(i).getItem());
				if(rb!=null)
					maxMana += rb.maxMana;
			}
		}
		//temporarily disabled for testing
		/*if(mana+gain > maxMana){
			gain = maxMana - mana;
		}*/
		mana += gain;
		return gain;
	}
	
	public void drainMana(float drain){
		if(this.mana > drain)
			mana -= drain;
		else{
			drain -= mana;
			mana = 0;
			plr.addExhaustion( drain );
		}
	}

    public float getMana() {
        return mana;
    }

    /**
     * Only to be used in synchronization. RPG mana gain should be administered through @link(gainMana)
     * @param mana
     * @return
     */
    @SideOnly(Side.CLIENT)
    public void setMana(float mana){
        this.mana = mana;
    }

	public boolean hasMana(float drain){
		return (mana+plr.getFoodStats().getSaturationLevel()+plr.getFoodStats().getFoodLevel())>drain;
	}

	//only called on server
	public void syncStatsIfNeeded(){
		if( Math.abs(lastMana - mana)>=0.99f || Math.abs(plr.getFoodStats().getSaturationLevel()-lastSaturation)>0.99f ){
			lastMana = mana;
			lastSaturation = plr.getFoodStats().getSaturationLevel();
			if(this.plr instanceof EntityPlayerMP){
                MortMagic.networkWrapper.sendTo( new MessageSyncStats(lastMana,lastSaturation), (EntityPlayerMP)plr);
			}
		}
        if( !unsyncedPages.isEmpty() ){
		    MortMagic.networkWrapper.sendTo( new MessageSyncGrimoire(unsyncedPages), (EntityPlayerMP)plr );
		    unsyncedPages.clear();
        }
	}

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return (capability == SPELLCASTER_CAPABILITY);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == SPELLCASTER_CAPABILITY) {
            return (T)this;
        }
        return null;
    }

    public void unlockGrimoirePage(GrimoirePage page, byte level){
	    if( !knownPages.containsKey( page ) || knownPages.get(page) < level ){
	        knownPages.put( page, level );
            unsyncedPages.put( page, level );
        }
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound tag = new NBTTagCompound();
	    NBTTagCompound inv = new NBTTagCompound();
        spellbook.saveToNBT(inv);
        tag.setTag("spellbook", inv);
        NBTTagList pages = new NBTTagList();
        for( GrimoirePage res : knownPages.keySet() ){
            NBTTagCompound page = new NBTTagCompound();
            page.setString( "page", res.getRegistryName().toString() );
            page.setByte( "version", knownPages.get(res) );
            pages.appendTag( page );
        }
        tag.setTag( "grimoire", pages );
        return tag;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        spellbook = InventorySpellbook.fromTag( nbt.getCompoundTag("spellbook") );
        IForgeRegistry<GrimoirePage> registry = GameRegistry.findRegistry( GrimoirePage.class );
        for(NBTBase base : nbt.getTagList( "grimoire", 10 )){
            NBTTagCompound page = (NBTTagCompound)base;
            ResourceLocation resLoc = new ResourceLocation( page.getString( "page" ) );
            if(registry.containsKey( resLoc ))
                knownPages.put( registry.getValue( resLoc ), page.getByte("version"));
        }
        unsyncedPages.putAll( knownPages );
    }
}
