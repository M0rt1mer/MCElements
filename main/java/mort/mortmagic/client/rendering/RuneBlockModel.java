package mort.mortmagic.client.rendering;

import mort.mortmagic.Content;
import mort.mortmagic.common.runes.RuneCharacter;
import mort.mortmagic.common.runes.RuneMaterial;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.common.model.IModelState;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class RuneBlockModel implements IModel {
    @Override
    public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        return new RuneBlockBakedModel(state,format,bakedTextureGetter);
    }

    @Override
    public Collection<ResourceLocation> getTextures() {
        List<ResourceLocation> textures = new ArrayList<>();
        for(RuneMaterial mat : Content.RUNE_MATERIAL_REGISTRY )
            for(RuneCharacter chr : Content.RUNE_CHARACTER_REGISTRY)
                textures.add( RuneBlockBakedModel.getResLocFromRune(mat,chr) );
        return textures;
    }
}
