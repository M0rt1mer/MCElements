package mort.mortmagic.common.spells;

import com.google.common.base.Predicate;
import mort.mortmagic.Content;
import mort.mortmagic.common.spells.Element;
import mort.mortmagic.common.spells.Spell;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class ElementAndItemToSpellMapping{

    Map<Element,ElementMappings> mappings = new HashMap<>();;

    private class ElementMappings{
        Spell defaultSpell; //if item is not mapped
        Map<Predicate<ItemStack>,Spell> spellMap = new HashMap<>();
    }

    public void registerDefaultSpell( Element ele, Spell spell ){
        if(!mappings.containsKey(ele))
            mappings.put( ele, new ElementMappings() );
        mappings.get(ele).defaultSpell = spell;
    }

    public void registerItemSpellCombination( Element ele, Predicate<ItemStack> pre, Spell spell ){
        if(!mappings.containsKey(ele))
            mappings.put( ele, new ElementMappings() );
        mappings.get(ele).spellMap.put( pre, spell );
    }

    public Spell getSpellByElementAndItem( Element ele, ItemStack item ){
        if(!mappings.containsKey(ele))
            return null;
        ElementMappings map = mappings.get(ele);
        for( Predicate<ItemStack> pred : map.spellMap.keySet() )
            if( pred.apply(item) )
                return map.spellMap.get(pred);
        return map.defaultSpell;
    }
}
