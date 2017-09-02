package mort.mortmagic.common;

import mort.mortmagic.Content;
import mort.mortmagic.ElementsEventHandler;
import mort.mortmagic.MortMagic;
import mort.mortmagic.common.inventory.SpellbookContainer;
import mort.mortmagic.common.net.MessageCast;
import mort.mortmagic.common.net.MessageOpenSpellbook;
import mort.mortmagic.common.net.MessageSyncStats;
import mort.mortmagic.common.spells.Spell;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;

import javax.annotation.Nullable;

public class CommonProxy implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		return new SpellbookContainer(player);
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		return null;
	}

	//public MMMPlayerCustomDataManager plrMan;
	public ElementsEventHandler handlr;
	
	public CommonProxy(){}
	
	public void preInit(){
        // Create extended player capability. As it is an internal capability, neither factory nor storage are needed to work
        CapabilityManager.INSTANCE.register(SpellCaster.class, new Capability.IStorage<SpellCaster>() {
            @Nullable
            @Override
            public NBTBase writeNBT(Capability<SpellCaster> capability, SpellCaster instance, EnumFacing side) {
                return null;
            }

            @Override
            public void readNBT(Capability<SpellCaster> capability, SpellCaster instance, EnumFacing side, NBTBase nbt) {
            }
        }, () -> null);
		handlr = new ElementsEventHandler();
		MinecraftForge.EVENT_BUS.register( handlr );
		//EntityRegistry.registerModEntity(EntitySpellMissile.class, "spellMissile", 0, MortMagic.instance, 64, 1, false);
        //handles creating all registries

        MortMagic.networkWrapper.registerMessage(MessageCast.MessageCastHandler.class, MessageCast.class, 0, Side.SERVER);
        MortMagic.networkWrapper.registerMessage(MessageOpenSpellbook.class, MessageOpenSpellbook.class, 1, Side.SERVER);
        MortMagic.networkWrapper.registerMessage(MessageSyncStats.MessageSyncStatsHandler.class, MessageSyncStats.class, 2, Side.CLIENT);

        Content.registerTileEntities();

    }
	
	public void init(){
		
		//int entityId = EntityRegistry.findGlobalUniqueEntityId();
		
		//EntityRegistry.registerGlobalEntityID(EntitySpellMissile.class, "spellMissile", entityId);
        NetworkRegistry.INSTANCE.registerGuiHandler(MortMagic.instance, this );
		Content.registerRecipes();
	}

	public void postInit(){

	}

	public void spawnParticle( World wld, double p1, double p2, double p3, double p4, double p5, double p6, Spell spell ){}

	public enum EnumDebugParticle{
		CIRCLE_CANDIDATE, CIRCLE_COMPLETED;
	}

	public void spawnDebugParticle(World wld, BlockPos pos, EnumDebugParticle type ){}

	public int addArmor(String str){
		return 0;
	}

    public void handle_syncStatsMessage( MessageSyncStats message ){}

}