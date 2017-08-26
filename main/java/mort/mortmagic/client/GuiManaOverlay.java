package mort.mortmagic.client;

import mort.mortmagic.SpellCaster;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class GuiManaOverlay extends Gui{
	
	ResourceLocation res = new ResourceLocation("mortmagic","textures/manaicons.png");
	
	public GuiManaOverlay(){
		this.zLevel = -89;
	}
	
	
	@SubscribeEvent
	public void onPostFoodRender( RenderGameOverlayEvent.Post evnt ){

		if(evnt.getType() == ElementType.FOOD ) //after food was rendered
			return;

		Minecraft mc = Minecraft.getMinecraft();
		EntityPlayer plr = (EntityPlayer) mc.getRenderViewEntity();
		
		int height = evnt.getResolution().getScaledHeight();
		int width = evnt.getResolution().getScaledWidth();

		mc.getTextureManager().bindTexture( res );

		int left = width / 2 + 91;
        int top = height - 39;
        
        GL11.glEnable(GL11.GL_BLEND);

        int mana = (int)(plr.getCapability(SpellCaster.SPELLCASTER_CAPABILITY, EnumFacing.DOWN).mana/2);
        int saturation = (int)(plr.getFoodStats().getSaturationLevel()/2);
        
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
