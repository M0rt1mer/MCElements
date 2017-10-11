package mort.mortmagic.common.grimoire;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.HashMap;
import java.util.Set;

public class GrimoirePage extends IForgeRegistryEntry.Impl<GrimoirePage> implements ICanBeDisplayedInGrimoire {

    public GrimoirePage(ResourceLocation name) {
        this.setRegistryName(name);
    }

    public String getUntranslatedText( int version ){
        return getRegistryName().getResourceDomain() + ".grimoire.page." + getRegistryName().getResourcePath() + "." + version;
    }

    public String getUntranslatedName(){
        return getRegistryName().getResourceDomain() + ".grimoire.page." + getRegistryName().getResourcePath() + ".name";
    }

    @Override
    public String getTranslatedText(HashMap<GrimoirePage, Byte> knownPages) {
        return I18n.format( getUntranslatedText( knownPages.get(this) ) );
    }
}