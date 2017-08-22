package mort.mortmagic.common.potions;

//not implemented as registry, for it relies on other registries

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class PotionIngredientRegistry {


    List<Entry> registry = new ArrayList<>();

    public Entry findItemEntry( ItemStack stk ){
        if(stk == null)
            return null;
        for ( Entry e : registry ){
            if( e.filter.test(stk) )
                return e;
        }
        return null;
    }

    public void register( Entry e ){
        registry.add(e);
    }


    public static class Entry{

        public final Predicate<ItemStack> filter;

        public final float rubedo;
        public final float auredo;
        public final float caerudo;

        public Entry(Predicate<ItemStack> filter, float rubedo, float auredo, float caerudo) {
            this.filter = filter;
            this.rubedo = rubedo;
            this.auredo = auredo;
            this.caerudo = caerudo;
        }

        public Entry( Item item, float rubedo, float auredo, float caerudo) {
            this.rubedo = rubedo;
            this.auredo = auredo;
            this.caerudo = caerudo;
            this.filter = ( stk -> stk.getItem() == item );
        }

        public Entry( Item item, int damage, float rubedo, float auredo, float caerudo) {
            this.rubedo = rubedo;
            this.auredo = auredo;
            this.caerudo = caerudo;
            this.filter = ( stk -> stk.getItem() == item && stk.getItemDamage() == damage );
        }
    }

}
