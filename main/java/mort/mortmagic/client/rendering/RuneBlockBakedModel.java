package mort.mortmagic.client.rendering;

import com.google.common.collect.ImmutableList;
import mort.mortmagic.MortMagic;
import mort.mortmagic.Resource;
import mort.mortmagic.common.block.BlockRune;
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
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.model.pipeline.UnpackedBakedQuad;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.property.IExtendedBlockState;
import scala.actors.threadpool.Arrays;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Function;

import static mort.mortmagic.common.block.BlockRune.runeCharacterProperty;
import static mort.mortmagic.common.block.BlockRune.runeMaterialProperty;

public class RuneBlockBakedModel implements IBakedModel {

    public static final ResourceLocation MISSING_RUNE_TEXTURE = new ResourceLocation(MortMagic.MODID, "missing_rune_combo");

    IModelState state;
    VertexFormat format;
    Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter;

    public RuneBlockBakedModel(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        this.state = state;
        this.format = format;
        this.bakedTextureGetter = bakedTextureGetter;
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {

        if( state.getValue(BlockRune.FACING) != side )
            return ImmutableList.of();

        IExtendedBlockState ext = (IExtendedBlockState)state;

        TextureAtlasSprite sprite = bakedTextureGetter.apply( getResLocFromRuneLoc( ext.getValue( runeMaterialProperty ),
                ext.getValue(runeCharacterProperty) ) );

        switch( ext.getValue(BlockRune.FACING) ){
            case SOUTH: return createQuad( new Vec3d(0,0,0), new Vec3d(0,1,0), new Vec3d(1,1,0), new Vec3d(1,0,0), sprite);
            case NORTH: return createQuad( new Vec3d(0,0,1), new Vec3d(0,1,1), new Vec3d(1,1,1), new Vec3d(1,0,1), sprite);
            case EAST: return createQuad( new Vec3d(0,0,0), new Vec3d(0,1,0), new Vec3d(0,1,1), new Vec3d(0,0,1), sprite);
            case WEST: return createQuad( new Vec3d(1,0,0), new Vec3d(1,1,0), new Vec3d(1,1,1), new Vec3d(1,0,1), sprite);
            case DOWN: return createQuad( new Vec3d(0,1,0), new Vec3d(0,1,1), new Vec3d(1,1,1), new Vec3d(1,1,0), sprite);
            case UP: return createQuad( new Vec3d(0,0,0), new Vec3d(0,0,1), new Vec3d(1,0,1), new Vec3d(1,0,0), sprite);
        }

        return ImmutableList.of();
    }

    private List<BakedQuad> createQuad(Vec3d v1, Vec3d v2, Vec3d v3, Vec3d v4, TextureAtlasSprite sprite) {
        BakedQuad[] ret = new BakedQuad[2];
        Vec3d normal = v3.subtract(v2).crossProduct(v1.subtract(v2)).normalize();
        Vec3d reverseNormal = Vec3d.ZERO.subtract(normal);

        UnpackedBakedQuad.Builder builder = new UnpackedBakedQuad.Builder(format);
        builder.setTexture(sprite);
        putVertex(builder, reverseNormal, v4.x, v4.y, v4.z, 16, 0, sprite);
        putVertex(builder, reverseNormal, v3.x, v3.y, v3.z, 16, 16, sprite);
        putVertex(builder, reverseNormal, v2.x, v2.y, v2.z, 0, 16, sprite);
        putVertex(builder, reverseNormal, v1.x, v1.y, v1.z, 0, 0, sprite);
        ret[0] = builder.build();
        builder = new UnpackedBakedQuad.Builder(format);
        builder.setTexture(sprite);
        putVertex(builder, normal, v1.x, v1.y, v1.z, 0, 0, sprite);
        putVertex(builder, normal, v2.x, v2.y, v2.z, 0, 16, sprite);
        putVertex(builder, normal, v3.x, v3.y, v3.z, 16, 16, sprite);
        putVertex(builder, normal, v4.x, v4.y, v4.z, 16, 0, sprite);
        ret[1] = builder.build();
        return Arrays.asList(ret);
    }

    private void putVertex(UnpackedBakedQuad.Builder builder, Vec3d normal, double x, double y, double z, float u, float v, TextureAtlasSprite sprite) {
        for (int e = 0; e < format.getElementCount(); e++) {
            switch (format.getElement(e).getUsage()) {
                case POSITION:
                    builder.put(e, (float)x, (float)y, (float)z, 1.0f);
                    break;
                case COLOR:
                    builder.put(e, 1.0f, 1.0f, 1.0f, 1.0f);
                    break;
                case UV:
                    if (format.getElement(e).getIndex() == 0) {
                        u = sprite.getInterpolatedU(u);
                        v = sprite.getInterpolatedV(v);
                        builder.put(e, u, v, 0f, 1f);
                        break;
                    }
                case NORMAL:
                    builder.put(e, (float) normal.x, (float) normal.y, (float) normal.z, 0f);
                    break;
                default:
                    builder.put(e);
                    break;
            }
        }
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
        if( mat == null || chr == null )
            return MISSING_RUNE_TEXTURE;
        return  getResLocFromRuneLoc( mat.getRegistryName(), chr.getRegistryName() );
    }

    public static ResourceLocation getResLocFromRuneLoc(ResourceLocation matLoc, ResourceLocation chrLoc){
        if( matLoc == null || chrLoc == null )
            return MISSING_RUNE_TEXTURE;
        return new ResourceLocation( MortMagic.MODID, "runes/" + matLoc.getResourcePath() + "_" + chrLoc.getResourcePath() );
    }
}
