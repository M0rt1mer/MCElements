package mort.mortmagic.net;

import net.minecraft.client.settings.KeyBinding;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;


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
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void playerTick(TickEvent.PlayerTickEvent event) {
		if (event.phase == TickEvent.Phase.START ) {
			if (specInventory.isPressed() && FMLClientHandler.instance().getClient().inGameHasFocus) {
				NetworkManager.instance.sendToServer(new MessageOpenSpellbook());
			}
			if( weakCast.isPressed() != weakPressed || strongCast.isPressed() != strongPressed ){
				weakPressed = weakCast.isPressed();
				strongPressed = strongCast.isPressed();
				//System.out.println("Tick "+weakCast.getIsKeyPressed()+" "+strongCast.getIsKeyPressed());
				NetworkManager.instance.sendToServer( new MessageCast( (weakPressed?1:0) + (strongPressed?2:0) ) );
				
			}
		}
	}

}
