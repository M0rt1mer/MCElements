package mort.mortmagic.common.runes;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

/**
 * Represents a single rune, like "Ra".
 */
public class RuneMaterial extends IForgeRegistryEntry.Impl<RuneMaterial> {

    String unlocalizedName;

    public RuneMaterial(ResourceLocation resLoc, String unlocalizedName) {
        this.unlocalizedName = unlocalizedName;
        this.setRegistryName(resLoc);
    }

}
