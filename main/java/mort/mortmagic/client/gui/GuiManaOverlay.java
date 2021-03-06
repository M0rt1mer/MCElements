package mort.mortmagic.client.gui;

import mort.mortmagic.common.SpellCaster;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.FoodStats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.GuiIngameForge;
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
	public void event_onPostFoodRender( RenderGameOverlayEvent.Post evnt ){

		if(evnt.getType() != ElementType.FOOD ) //after food was rendered
			return;

		Minecraft mc = Minecraft.getMinecraft();
		EntityPlayer plr = (EntityPlayer) mc.getRenderViewEntity();
		
		int height = evnt.getResolution().getScaledHeight();
		int width = evnt.getResolution().getScaledWidth();

		mc.getTextureManager().bindTexture( res );

		int left = width / 2 + 91;
        int top = height - GuiIngameForge.right_height + 10; //right_height was shifted by 10 during food draw

		GlStateManager.enableBlend();

        int mana = (int) plr.getCapability(SpellCaster.SPELLCASTER_CAPABILITY, EnumFacing.DOWN).getMana()/4;
        int saturation = (int)(plr.getFoodStats().getSaturationLevel()/2);

        //draw saturation
        for( int i = 0; i<10 ; i++ ){
			int x = left - i * 8 - 9;
			int y = top;
        	if( i < saturation+mana-10 )
        		this.drawTexturedModalRect( x, top, 9, 0, 9, 9 );
        	else if( i<saturation )
        		this.drawTexturedModalRect( x, top, 18, 0, 9, 9 );
        	else if( i < saturation+mana)
        		this.drawTexturedModalRect( x, top, 0, 0, 9, 9 );
        }

		GlStateManager.disableBlend();
	}

	
	
}
