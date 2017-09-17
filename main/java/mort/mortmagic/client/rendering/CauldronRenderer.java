package mort.mortmagic.client.rendering;

import mort.mortmagic.common.block.BlockCauldron;
import mort.mortmagic.common.tileentity.TileCauldron;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.model.animation.FastTESR;

public class CauldronRenderer extends FastTESR<TileCauldron> {

    protected static BlockRendererDispatcher blockRenderer;


    @Override
    public void renderTileEntityFast(TileCauldron te, double x, double y, double z, float partialTicks, int destroyStage, float partial, BufferBuilder buffer) {

        if( te.hasWater() ) {
            if (blockRenderer == null) blockRenderer = Minecraft.getMinecraft().getBlockRendererDispatcher();
            BlockPos pos = te.getPos();
            IBlockAccess world = MinecraftForgeClient.getRegionRenderCache(te.getWorld(), te.getPos());
            IBlockState state = getBlockStateForRender( te, world );
            IBakedModel model = blockRenderer.getBlockModelShapes().getModelForState(state);
            buffer.setTranslation(x - pos.getX(), y - pos.getY(), z - pos.getZ());
            blockRenderer.getBlockModelRenderer().renderModel(world, model, state, pos, buffer, false);
        }

    }

    private IBlockState getBlockStateForRender( TileCauldron te, IBlockAccess world ){
        IBlockState prevState = world.getBlockState(te.getPos());
        return  prevState.withProperty( BlockCauldron.IS_WATER, te.hasIngredients() ? 2 : 1 );
    }

}
