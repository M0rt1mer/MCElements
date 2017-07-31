package mort.mortmagic.client;
/*
import java.util.HashMap;
import java.util.Map;

import mort.mortmagic.world.EntitySpellMissile;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class MissileRenderer extends Render {
	
	Map<ISpell,ResourceLocation> locMap = new HashMap<ISpell, ResourceLocation>();
	
	public MissileRenderer(){
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		ISpell spl = ((EntitySpellMissile)p_110775_1_).getSpell();
		if(!locMap.containsKey(spl))
			locMap.put(spl, new ResourceLocation( spl.getTextureName()+"_missile.png" ) );
		return locMap.get(spl);
	}

    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
    {
    	//System.out.println("rendering "+par1Entity.getExtendedProperties("missile").hashCode() + " with spell "+((EntitySpellMissile)par1Entity).getSpell());
    	if(((EntitySpellMissile)par1Entity).getSpell() == null)
    		return;
    	GL11.glEnable(GL12.GL_RESCALE_NORMAL);
    	//GL11.glEnable(GL11.GL_BLEND);
    	
    	bindEntityTexture(par1Entity);
    	renderBillboard( (float)par2, (float)par4, (float)par6);
    	
    	//GL11.glDisable(GL11.GL_BLEND);
    	GL11.glDisable(GL12.GL_RESCALE_NORMAL);
    	
    }
    
    private void renderBillboard(float x, float y, float z){
    	
    	GL11.glPushMatrix();
    	GL11.glTranslatef(x, y, z);
    	GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        GL11.glColor3f(1, 1, 1);
        
        Tessellator var10 = Tessellator.instance;
        var10.startDrawingQuads();
        var10.setNormal(0.0F, 1.0F, 0.0F);
        var10.addVertexWithUV( -0.5, -0.5, 0.0D, 0,0);
        var10.addVertexWithUV(0.5, -0.5, 0.0D, 1,0);
        var10.addVertexWithUV(0.5, 0.5, 0.0D, 1,1);
        var10.addVertexWithUV(-0.5, 0.5, 0.0D, 0,1);
        var10.draw();
        GL11.glPopMatrix();
    }

    

	
	
}*/
