package mort.mortmagic.common.grimoire;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.ArrayList;
import java.util.List;

public class GrimoireChapter extends IForgeRegistryEntry.Impl<GrimoireChapter> {

    public GrimoireChapter getParentChapter() {
        return parentChapter;
    }

    public void setParentChapter(GrimoireChapter parentChapter) {
        if(this.parentChapter != null)
            parentChapter.childChapters.remove(this);
        this.parentChapter = parentChapter;
        parentChapter.childChapters.add(this);
    }

    private GrimoireChapter parentChapter;
    private List<GrimoireChapter> childChapters = new ArrayList<>();

    public GrimoireChapter(ResourceLocation resLoc) {
        setRegistryName(resLoc);
    }

    public GrimoireChapter(ResourceLocation resLoc, GrimoireChapter parentChapter) {
        this(resLoc);
        setParentChapter(parentChapter);
    }

    public String getUntranslatedString(){
        return getRegistryName().getResourceDomain()+".grimoire.chapter." + getRegistryName().getResourcePath() + ".name";
    }

}
