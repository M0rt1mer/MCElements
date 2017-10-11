package mort.mortmagic.common.grimoire;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

import java.util.Set;

public class GrimoireChapterRoot extends GrimoireChapter {

    public GrimoireChapterRoot(ResourceLocation resLoc) {
        super(resLoc);
    }

    @Override
    public String getPageText(Set<GrimoirePage> knownPages) {
        String text = super.getPageText(knownPages);
        if( text == null )
            return I18n.format( "mortmagic.grimoire.empty" );
        else return text;
    }
}
