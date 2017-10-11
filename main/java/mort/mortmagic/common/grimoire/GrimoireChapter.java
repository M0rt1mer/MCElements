package mort.mortmagic.common.grimoire;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class GrimoireChapter extends IForgeRegistryEntry.Impl<GrimoireChapter> implements ICanBeDisplayedInGrimoire {

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
    private List<GrimoirePage> childPages = new ArrayList<>();

    public GrimoireChapter(ResourceLocation resLoc) {
        setRegistryName(resLoc);
    }

    public GrimoireChapter(ResourceLocation resLoc, GrimoireChapter parentChapter) {
        this(resLoc);
        setParentChapter(parentChapter);
    }

    public String getUntranslatedName(){
        return getRegistryName().getResourceDomain()+".grimoire.chapter." + getRegistryName().getResourcePath() + ".name";
    }

    public String getPageText( Set<GrimoirePage> knownPages ){
        return getPageTextRecursive( 0, knownPages );
    }

    private String getPageTextRecursive( int indentLevel, Set<GrimoirePage> knownPages  ){
        String children = "";
        for( GrimoireChapter child : childChapters ){
            String childStr = child.getPageTextRecursive(indentLevel + 1, knownPages);
            if (childStr != null)
                children += childStr + "\n";
        }
        for( GrimoirePage child : childPages ){
            if( knownPages.contains( child ) ) {
                children += I18n.format( child.getUntranslatedName() ) + "\n";
            }
        }

        if( !children.isEmpty() )
            return org.apache.commons.lang3.StringUtils.repeat(' ',indentLevel) + I18n.format( getUntranslatedName() ) + "\n" + children;
        else
            return null;
    }

    @Override
    public String getTranslatedText(HashMap<GrimoirePage, Integer> knownPages) {
        return getPageText(knownPages.keySet());
    }

}
