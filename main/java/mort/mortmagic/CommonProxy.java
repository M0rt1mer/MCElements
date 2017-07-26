package mort.mortmagic;

import mort.mortmagic.inventory.SpellbookContainer;
import mort.mortmagic.spells.ISpell;
import mort.mortmagic.world.EntitySpellMissile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.registry.EntityRegistry;

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
	public EventHandler handlr;
	
	public CommonProxy(){}
	
	public void preInit(){
		
		//plrMan = new MMMPlayerCustomDataManager();
		handlr = new EventHandler();
		//MinecraftForge.EVENT_BUS.register( plrMan );
		MinecraftForge.EVENT_BUS.register( handlr );
		//MinecraftForge.EVENT_BUS.register( MortMagic.mngr );
		EntityRegistry.registerModEntity(EntitySpellMissile.class, "spellMissile", 0, MortMagic.instance, 64, 1, false);
	}
	
	public void init(){
		
		//int entityId = EntityRegistry.findGlobalUniqueEntityId();
		
		//EntityRegistry.registerGlobalEntityID(EntitySpellMissile.class, "spellMissile", entityId);
		
		
	}

	public void spawnParticle( World wld, double p1, double p2, double p3, double p4, double p5, double p6, ISpell spell ){}
	
	//updates mana and saturation of local player (client only, called from MessageSync, that cannot reference net.minecraft.Minecraft)
	public void updatePlayerStats(float mana, float saturation){}
	
	public int addArmor(String str){
		return 0;
	}
	
}