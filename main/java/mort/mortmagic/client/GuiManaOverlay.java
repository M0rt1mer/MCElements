package mort.mortmagic.client;

import mort.mortmagic.ExtendedPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class GuiManaOverlay extends Gui{
	
	ResourceLocation res = new ResourceLocation("mortmagic","textures/manaIcons.png");
	
	public GuiManaOverlay(){
		this.zLevel = -89;
	}
	
	
	@SubscribeEvent
	public void onExperienceBarRender( RenderGameOverlayEvent.Post evnt ){
		
		if(evnt.type != ElementType.FOOD ) //after all was rendered
			return;
		
		Minecraft mc = Minecraft.getMinecraft();
		
		int height = evnt.resolution.getScaledHeight();
		int width = evnt.resolution.getScaledWidth();
		
		//mc.entityRenderer.setupOverlayRendering();
		
		mc.getTextureManager().bindTexture( res );
		
		
		int left = width / 2 + 82;
        int top = height - 39;
        
        GL11.glEnable(GL11.GL_BLEND);

        int mana = (int)(ExtendedPlayer.getExtendedPlayer(mc.thePlayer).mana/2);
        int saturation = (int)(mc.thePlayer.getFoodStats().getSaturationLevel()/2);
        
        //draw saturation
        int i = 0;
        for( i = 0; i<10 ; i++ ){
        	if( i < saturation+mana-10 )
        		this.drawTexturedModalRect( left - 8*i, top, 9, 0, 9, 9 );
        	else if( i<saturation )
        		this.drawTexturedModalRect( left - 8*i, top, 18, 0, 9, 9 );
        	else if( i < saturation+mana)
        		this.drawTexturedModalRect( left - 8*i, top, 0, 0, 9, 9 );	
        }

        GL11.glDisable(GL11.GL_BLEND);
		
		
	}

	
	
}
