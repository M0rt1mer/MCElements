package mort.mortmagic;

import mort.mortmagic.client.GuiManaOverlay;
import mort.mortmagic.client.GuiSpellbook;
import mort.mortmagic.client.MissileRenderer;
import mort.mortmagic.client.SpellParticle;
import mort.mortmagic.client.WildFireRenderer;
import mort.mortmagic.inventory.InventorySpellbook;
import mort.mortmagic.inventory.SpellbookContainer;
import mort.mortmagic.net.KeyBindingManager;
import mort.mortmagic.spells.ISpell;
import mort.mortmagic.world.EntitySpellMissile;
import mort.mortmagic.world.block.TileEntityWildfire;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,	int x, int y, int z) {
		return new GuiSpellbook(new SpellbookContainer(player));
	}

	public KeyBindingManager keyBind;
	
	public ClientProxy(){}
	
	public void preInit(){
		super.preInit();
		
		keyBind = new KeyBindingManager();
		keyBind.initAndRegister();
		
		RenderingRegistry.registerEntityRenderingHandler(EntitySpellMissile.class, new MissileRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer( TileEntityWildfire.class , new WildFireRenderer() );
	}

	@Override
	public void init() {
		super.init();
		
		
		MinecraftForge.EVENT_BUS.register( new GuiManaOverlay() );
	}
	
	public void spawnParticle( World wld, double p1, double p2, double p3, double p4, double p5, double p6, ISpell spell){
		//if( wld == Minecraft.getMinecraft().thePlayer.worldObj ){
			SpellParticle spl =  new SpellParticle(wld, p1, p2, p3, p4, p5, p6);
			spl.setSpell(spell);
			Minecraft.getMinecraft().effectRenderer.addEffect( spl );
		//}
	}

	@Override
	public void updatePlayerStats(float mana, float saturation) {
		super.updatePlayerStats( mana,  saturation);
		Minecraft.getMinecraft().thePlayer.getFoodStats().setFoodSaturationLevel(saturation);
		ExtendedPlayer.getExtendedPlayer( Minecraft.getMinecraft().thePlayer ).mana = mana;
	}

	@Override
	public int addArmor(String str) {
		return RenderingRegistry.addNewArmourRendererPrefix("mmRobe");
	}
	
	
}
