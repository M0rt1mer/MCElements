package mort.mortmagic.common.runes;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

/**
 * Represents a single rune, like "Ra".
 */
public class RuneCharacter extends IForgeRegistryEntry.Impl<RuneCharacter>{

    String unlocalizedName;

    public RuneCharacter(ResourceLocation resLoc, String unlocalizedName) {
        this.unlocalizedName = unlocalizedName;
        this.setRegistryName(resLoc);
    }

}
