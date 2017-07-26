package mort.mortmagic.client;

import mort.mortmagic.Resource;
import mort.mortmagic.world.block.TileEntityWildfire;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

public class WildFireRenderer extends TileEntitySpecialRenderer{
	
	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double d, double d1, double d2, float f)  {
		GL11.glPushMatrix();
	    GL11.glTranslatef((float)d, (float)d1, (float)d2);
	    TileEntityWildfire tileEntityYour = (TileEntityWildfire)tileEntity;
	    renderFire(tileEntityYour, tileEntity.getWorldObj(), tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord, Resource.wildfire);
	    GL11.glPopMatrix();
   }
   //And this method actually renders your tile entity
   public void renderFire(TileEntityWildfire tl, World world, int i, int j, int k, Block block) {
	   
	   Tessellator tessellator = Tessellator.instance;
	   int l = world.getLightBrightnessForSkyBlocks(i, j, k, 0);
	   int l1 = l % 65536;
	   int l2 = l / 65536;
	   if( world.getBlockMetadata(i, j, k)==1 )
		   tessellator.setColorOpaque_F(0.6f, 1, 0.6f);
	   else
		   tessellator.setColorOpaque_F(1,1,1);
	   OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)l1, (float)l2); 
	   
	   bindTexture( Minecraft.getMinecraft().getTextureMapBlocks().locationBlocksTexture );
	   
	   IIcon iicon = Blocks.fire.getFireIcon(0);
       IIcon iicon1 = Blocks.fire.getFireIcon(1);
       
       ///// copy from renderFireBlock
       
       float xLocal = 0, yLocal = 0, zLocal = 0;
       
       double u1 = (double)iicon1.getMinU();
       double v2 = (double)iicon1.getMinV();
       double u2 = (double)iicon1.getMaxU();
       double v1 = (double)iicon1.getMaxV();
       
       tessellator.startDrawingQuads();
       tessellator.addVertexWithUV(0, 0, 0, u1, v1);
       tessellator.addVertexWithUV(0, 1.5, 0.1, u1, v2);
       tessellator.addVertexWithUV(1, 1.5, 0.1, u2, v2);
       tessellator.addVertexWithUV(1, 0, 0, u2, v1);
       
       /*tessellator.addVertexWithUV(0, 0, 0.3, u1, v1);
       tessellator.addVertexWithUV(0, 1, 0.6, u1, v2);
       tessellator.addVertexWithUV(1, 1, 0.6, u2, v2);
       tessellator.addVertexWithUV(1, 0, 0.3, u2, v1);*/
       
       
       tessellator.addVertexWithUV(1  , 0, 0, u1, v1);
       tessellator.addVertexWithUV(0.9  , 1.5, 0, u1, v2);
       tessellator.addVertexWithUV(0.9  , 1.5, 1, u2, v2);
       tessellator.addVertexWithUV(1  , 0, 1, u2, v1);
       
       
       tessellator.addVertexWithUV(0, 1.5, 0.9, u1, v2);
       tessellator.addVertexWithUV(0, 0, 1, u1, v1);
       tessellator.addVertexWithUV(1, 0, 1, u2, v1);
       tessellator.addVertexWithUV(1, 1.5, 0.9, u2, v2);
       
       
       tessellator.addVertexWithUV(0.1  , 1.5, 0, u1, v2);
       tessellator.addVertexWithUV(0  , 0, 0, u1, v1);
       tessellator.addVertexWithUV(0  , 0, 1, u2, v1);
       tessellator.addVertexWithUV(0.1  , 1.5, 1, u2, v2);
       
       
       
       
	   tessellator.draw();
	   
   }

	@Override
	protected void bindTexture(ResourceLocation p_147499_1_) {
		super.bindTexture(p_147499_1_);
	}
	
	
}
