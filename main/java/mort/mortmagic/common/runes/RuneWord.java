package mort.mortmagic.common.runes;

import com.google.common.collect.ImmutableList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

/**
 * Represent a rune word - a specific magical effect. Actual composition is dictated by a recipe
 */
public class RuneWord extends IForgeRegistryEntry.Impl<RuneWord> {

    public RuneWord( ResourceLocation loc ) {
        this.setRegistryName(loc);
        //this.characters = ImmutableList.copyOf(characters);
    }



}
