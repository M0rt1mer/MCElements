package mort.mortmagic.client;

import mort.mortmagic.common.entity.EntitySpellMissile;
import mort.mortmagic.common.spells.Spell;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class MissileRenderer extends Render<EntitySpellMissile> {
	
	Map<Spell,ResourceLocation> locMap = new HashMap<Spell, ResourceLocation>();

	public static IRenderFactory<EntitySpellMissile> FACTORY = (manager -> {return new MissileRenderer( manager );});

    protected MissileRenderer(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public void doRender(EntitySpellMissile entity, double x, double y, double z, float entityYaw, float partialTicks) {
        super.doRender(entity, x, y, z, entityYaw, partialTicks);

        if(entity.getSpell() == null)
            return;


        bindEntityTexture( entity );
        renderBillboard( x,y,z );



    }
    
    private void renderBillboard( double x, double y, double z ){

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableRescaleNormal();
        GlStateManager.disableLighting();
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)x, (float)y, (float)z);
        GlStateManager.rotate(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F );
        GlStateManager.rotate(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F );

        GlStateManager.glNormal3f(0.05625F, 0.0F, 0.0F);
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos(-0.5, -0.5, 0.0 ).tex(0.0D, 0).endVertex();
        bufferbuilder.pos(0.5, -0.5, 0.0 ).tex(0.1, 0).endVertex();
        bufferbuilder.pos(0.5, 0.5, 0.0 ).tex(1, 1).endVertex();
        bufferbuilder.pos(-0.5, 0.5, 0.0 ).tex(0, 1).endVertex();
        tessellator.draw();

        GlStateManager.disableRescaleNormal();
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
        GlStateManager.disableRescaleNormal();
    }


    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntitySpellMissile entity) {
        if(entity.spell == null)
            return null;
        else{
            return new ResourceLocation(entity.spell.getRegistryName().getResourceDomain(), "entities/spell_"+entity.spell.getRegistryName().getResourcePath() );

        }
    }

}