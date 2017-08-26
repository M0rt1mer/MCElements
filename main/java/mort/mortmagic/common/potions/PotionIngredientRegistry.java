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


    public static enum AspectPower{
        NONE(0), DARK(0.2f), DARKER(0.4f), NORMAL(0.6f), LIGHTER(0.8f), LIGHT(1);
        public final float value;
        AspectPower(float value) {
            this.value = value;
        }
    }

    public static class Entry{

        public final Predicate<ItemStack> filter;

        public final AspectPower rubedo;
        public final AspectPower auredo;
        public final AspectPower caerudo;

        public final PotionActivator activator;

        public Entry(Predicate<ItemStack> filter, AspectPower rubedo, AspectPower auredo, AspectPower caerudo, PotionActivator activator ) {
            this.filter = filter;
            this.rubedo = rubedo;
            this.auredo = auredo;
            this.caerudo = caerudo;
            this.activator = activator;
        }

        public Entry( Item item, AspectPower rubedo, AspectPower auredo, AspectPower caerudo, PotionActivator activator) {
            this.rubedo = rubedo;
            this.auredo = auredo;
            this.caerudo = caerudo;
            this.filter = ( stk -> stk.getItem() == item );
            this.activator = activator;
        }

        public Entry( Item item, int damage, AspectPower rubedo, AspectPower auredo, AspectPower caerudo, PotionActivator activator) {
            this.rubedo = rubedo;
            this.auredo = auredo;
            this.caerudo = caerudo;
            this.filter = ( stk -> stk.getItem() == item && stk.getItemDamage() == damage );
            this.activator = activator;
        }
    }

}
