package mort.mortmagic.net;

import net.minecraft.client.settings.KeyBinding;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class KeyBindingManager {

	public KeyBinding weakCast;
	public KeyBinding strongCast;
	public KeyBinding specInventory;
	
	private boolean weakPressed = false;
	private boolean strongPressed = false;
	
	public void initAndRegister(){

		weakCast = new KeyBinding("key.mort.weakCast",Keyboard.KEY_C,"key.categories.mortMagic");
		strongCast = new KeyBinding("key.mort.strongCast",Keyboard.KEY_V,"key.categories.mortMagic");
		specInventory = new KeyBinding("key.mort.specInventory",Keyboard.KEY_R,"key.categories.mortMagic");

		ClientRegistry.registerKeyBinding(weakCast);
		ClientRegistry.registerKeyBinding(strongCast);
		ClientRegistry.registerKeyBinding(specInventory);
		FMLCommonHandler.instance().bus().register(this);
	}

	@SideOnly(value=Side.CLIENT)
	@SubscribeEvent
	public void playerTick(PlayerTickEvent event) {
		if (event.phase == Phase.START ) {
			if (specInventory.isPressed() && FMLClientHandler.instance().getClient().inGameHasFocus) {
				NetworkManager.instance.sendToServer(new MessageOpenSpellbook());
			}
			if( weakCast.getIsKeyPressed() != weakPressed || strongCast.getIsKeyPressed() != strongPressed ){
				weakPressed = weakCast.getIsKeyPressed();
				strongPressed = strongCast.getIsKeyPressed();
				//System.out.println("Tick "+weakCast.getIsKeyPressed()+" "+strongCast.getIsKeyPressed());
				NetworkManager.instance.sendToServer( new MessageCast( (weakPressed?1:0) + (strongPressed?2:0) ) );
				
			}
		}
	}

}
