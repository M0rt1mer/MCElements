package mort.mortmagic.common;

import mort.mortmagic.Content;
import mort.mortmagic.ElementsEventHandler;
import mort.mortmagic.ExtendedPlayer;
import mort.mortmagic.MortMagic;
import mort.mortmagic.common.inventory.SpellbookContainer;
import mort.mortmagic.common.net.NetworkManager;
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
        CapabilityManager.INSTANCE.register(ExtendedPlayer.class, new Capability.IStorage<ExtendedPlayer>() {
            @Nullable
            @Override
            public NBTBase writeNBT(Capability<ExtendedPlayer> capability, ExtendedPlayer instance, EnumFacing side) {
                return null;
            }

            @Override
            public void readNBT(Capability<ExtendedPlayer> capability, ExtendedPlayer instance, EnumFacing side, NBTBase nbt) {
            }
        }, () -> null);
		handlr = new ElementsEventHandler();
		MinecraftForge.EVENT_BUS.register( handlr );
		//EntityRegistry.registerModEntity(EntitySpellMissile.class, "spellMissile", 0, MortMagic.instance, 64, 1, false);
        //handles creating all registries
        NetworkManager.init();
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

	//updates mana and saturation of local player (client only, called from MessageSync, that cannot reference net.minecraft.Minecraft)
	public void updatePlayerStats(float mana, float saturation){}
	
	public int addArmor(String str){
		return 0;
	}
	
}