package mort.mortmagic.client;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
/*
public class SpellParticle extends EntityFX{

	private static Map<ISpell,ResourceLocation> locMap = new HashMap<ISpell, ResourceLocation>();
	private ISpell spell;
	
	public SpellParticle(World p_i1219_1_, double p_i1219_2_,
			double p_i1219_4_, double p_i1219_6_, double p_i1219_8_,
			double p_i1219_10_, double p_i1219_12_) {
		super(p_i1219_1_, p_i1219_2_, p_i1219_4_, p_i1219_6_, p_i1219_8_, p_i1219_10_,
				p_i1219_12_);
		this.noClip = true;
	}

	public SpellParticle(World p_i1218_1_, double p_i1218_2_,
			double p_i1218_4_, double p_i1218_6_) {
		super(p_i1218_1_, p_i1218_2_, p_i1218_4_, p_i1218_6_);
		this.noClip = true;
	}

	public void setSpell(ISpell spell){
		this.spell = spell;
	}
	
	@Override
	public void renderParticle(Tessellator p_70539_1_, float p_70539_2_, float p_70539_3_, float p_70539_4_, float p_70539_5_, float p_70539_6_, float p_70539_7_) {
		
		if(spell==null)
			return;
		if( !locMap.containsKey(spell) )
			locMap.put(spell, new ResourceLocation(spell.getTextureName()+"_particle.png") );
		Minecraft.getMinecraft().renderEngine.bindTexture(locMap.get(spell));
        //float f6 = this.particleIcon.getMinU(); 
        float f6 = 0;
        //float f7 = this.particleIcon.getMaxU();
        float f7 = 1;
        //float f8 = this.particleIcon.getMinV();
        float f8 = 0;
        //float f9 = this.particleIcon.getMaxV();
        float f9 = 1;
        float f10 = 0.1F * this.particleScale;
        
        float f11 = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)p_70539_2_ - interpPosX);
        float f12 = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)p_70539_2_ - interpPosY);
        float f13 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)p_70539_2_ - interpPosZ);
        
        p_70539_1_.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha);
        p_70539_1_.addVertexWithUV((double)(f11 - p_70539_3_ * f10 - p_70539_6_ * f10), (double)(f12 - p_70539_4_ * f10), (double)(f13 - p_70539_5_ * f10 - p_70539_7_ * f10), (double)f7, (double)f9);
        p_70539_1_.addVertexWithUV((double)(f11 - p_70539_3_ * f10 + p_70539_6_ * f10), (double)(f12 + p_70539_4_ * f10), (double)(f13 - p_70539_5_ * f10 + p_70539_7_ * f10), (double)f7, (double)f8);
        p_70539_1_.addVertexWithUV((double)(f11 + p_70539_3_ * f10 + p_70539_6_ * f10), (double)(f12 + p_70539_4_ * f10), (double)(f13 + p_70539_5_ * f10 + p_70539_7_ * f10), (double)f6, (double)f8);
        p_70539_1_.addVertexWithUV((double)(f11 + p_70539_3_ * f10 - p_70539_6_ * f10), (double)(f12 - p_70539_4_ * f10), (double)(f13 + p_70539_5_ * f10 - p_70539_7_ * f10), (double)f6, (double)f9);
		
		
	}

	
	

	
	
}*/
