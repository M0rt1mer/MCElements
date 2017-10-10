package mort.mortmagic.common.grimoire;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class GrimoirePage extends IForgeRegistryEntry.Impl<GrimoirePage> {

    public GrimoirePage(ResourceLocation name) {
        this.setRegistryName(name);
    }

    public String getUntranslatedText( int version ){
        return getRegistryName().getResourceDomain() + ".grimoire.page." + getRegistryName().getResourcePath() + "." + version;
    }

}