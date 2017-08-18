package mort.mortmagic.client.rendering;

import mort.mortmagic.MortMagic;
import mort.mortmagic.common.runes.RuneCharacter;
import mort.mortmagic.common.runes.RuneMaterial;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.model.IModelState;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Function;

public class RuneBlockBakedModel implements IBakedModel {

    public RuneBlockBakedModel(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
        return null;
    }

    @Override
    public boolean isAmbientOcclusion() {
        return false;
    }

    @Override
    public boolean isGui3d() {
        return false;
    }

    @Override
    public boolean isBuiltInRenderer() {
        return false;
    }

    @Override
    public TextureAtlasSprite getParticleTexture() {
        return null;
    }

    @Override
    public ItemOverrideList getOverrides() {
        return null;
    }

    public static ResourceLocation getResLocFromRune(RuneMaterial mat, RuneCharacter chr){
        return new ResourceLocation(MortMagic.MODID,"rune_"+mat.getRegistryName().getResourcePath()+"_"+chr.getRegistryName().getResourcePath());
    }
}
