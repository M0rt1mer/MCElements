package mort.mortmagic.api;

import com.google.common.base.Predicate;
import mort.mortmagic.spells.Element;
import mort.mortmagic.spells.Spell;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import java.util.HashMap;
import java.util.Map;

public class ElementAndItemToSpellMapping{

    Map<Element,ElementMappings> mappings = new HashMap<>();

    private class ElementMappings{
        Spell defaultSpell; //if item is not mapped


    }

    //<editor-fold desc="Instance">
    Element element;
    Item item;
    Predicate<ItemStack> itemPredicate;

    public ElementAndItemToSpellMapping(Element element) {
        this.element = element;
    }
    //</editor-fold>

    public static Spell getSpellByElementAndItem( Element ele, ItemStack item ){
        return null;
    }
}
