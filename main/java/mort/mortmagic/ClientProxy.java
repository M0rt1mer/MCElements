package mort.mortmagic;

import mort.mortmagic.client.GuiManaOverlay;
import mort.mortmagic.client.GuiSpellbook;
import mort.mortmagic.client.rendering.McElementsModelLoader;
import mort.mortmagic.common.CommonProxy;
import mort.mortmagic.common.inventory.SpellbookContainer;
import mort.mortmagic.client.KeyBindingManager;
import mort.mortmagic.common.spells.Spell;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,	int x, int y, int z) {
		return new GuiSpellbook(new SpellbookContainer(player));
	}

	public KeyBindingManager keyBind;
	
	public void preInit(){
		super.preInit();
		keyBind = new KeyBindingManager();
		keyBind.initAndRegister();
		ModelLoaderRegistry.registerLoader( new McElementsModelLoader());
	}

	@Override
	public void init() {
		super.init();
		MinecraftForge.EVENT_BUS.register( new GuiManaOverlay() );
	}

	@SubscribeEvent
	public static void event_registerModels(ModelRegistryEvent event) {
		Resource.metaItem.initModels();
		Resource.spellScroll.initModel();
		Resource.charge.initModel();
		Resource.runeBlock.initModels();
		Resource.runeBlock.initItemModels();
	}

	public void postInit(){
		super.postInit();
	}

	public void spawnParticle( World wld, double p1, double p2, double p3, double p4, double p5, double p6, Spell spell){
		//if( wld == Minecraft.getMinecraft().thePlayer.worldObj ){
			/*SpellParticle spl =  new SpellParticle(wld, p1, p2, p3, p4, p5, p6);
			spl.setSpell(spell);
			Minecraft.getMinecraft().effectRenderer.addEffect( spl );*/
		//}
	}

	@Override
	public void updatePlayerStats(float mana, float saturation) {
		super.updatePlayerStats( mana,  saturation);
		Minecraft.getMinecraft().player.getFoodStats().setFoodSaturationLevel(saturation);
		Minecraft.getMinecraft().player.getCapability(ExtendedPlayer.EXTENDED_PLAYER_CAPABILITY, EnumFacing.DOWN).mana = mana;
	}

}
