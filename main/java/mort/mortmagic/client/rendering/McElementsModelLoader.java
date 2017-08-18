package mort.mortmagic.client.rendering;

import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;

public class McElementsModelLoader implements ICustomModelLoader{

    public static final RuneBlockModel MODEL = new RuneBlockModel();

    @Override
    public boolean accepts(ResourceLocation modelLocation) {
        return modelLocation.toString().equals("mortimer:runeblock");
    }

    @Override
    public IModel loadModel(ResourceLocation modelLocation) throws Exception {
        return MODEL;
    }

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {

    }
}
