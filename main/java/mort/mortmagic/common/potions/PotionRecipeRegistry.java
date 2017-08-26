package mort.mortmagic.common.potions;

import mort.mortmagic.Content;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

import java.util.*;
import java.util.function.Consumer;

public class PotionRecipeRegistry implements Iterable<PotionRecipeRegistry.PotionRecipe> {

    private Map<ResourceLocation,PotionRecipe> list = new TreeMap<>();

    public void register( PotionRecipe recipe ){
        for( PotionRecipe existingRecipe : list.values() ){
            if( existingRecipe.checkRecipeSimilarity(recipe) )
                throw new IllegalArgumentException("Recipe collides with already existing recipe");
        }
        list.put(recipe.location,recipe);
    }

    /**
     * Returns the resulting itemstack. It is the stack template in recipe, it needs to be cloned before use.
     * @param rubedo
     * @param auredo
     * @param caerudo
     * @return
     */
    public ItemStack findPotion( float rubedo, float auredo, float caerudo ){
        for( PotionRecipe recipe : list.values() ){
            if( recipe.checkRecipe(rubedo,auredo,caerudo) )
                return recipe.result;
        }
        return null;
    }

    public PotionRecipe getRecipe( ResourceLocation loc ){
        return list.get(loc);
    }

    //<editor-fold desc="Forwarding iterable interface to list.values()">
    @Override
    public Iterator<PotionRecipe> iterator() {
        return list.values().iterator();
    }

    @Override
    public void forEach(Consumer<? super PotionRecipe> action) {
        list.values().forEach(action);
    }

    @Override
    public Spliterator<PotionRecipe> spliterator() {
        return list.values().spliterator();
    }
    //</editor-fold>

    public static class PotionRecipe {

        public ResourceLocation location;

        public final float rubedo;
        public final float auredo;
        public final float caerudo;

        public final ItemStack result;

        public PotionRecipe( ResourceLocation location, float rubedo, float auredo, float caerudo, ItemStack result) {
            this.location = location;
            this.rubedo = rubedo;
            this.auredo = auredo;
            this.caerudo = caerudo;
            this.result = result;
        }

        /**
         * Checks if given cauldron content satisfies this potions recipe
         * @param rubedo
         * @param auredo
         * @param caerudo
         * @return
         */
        public boolean checkRecipe( float rubedo, float auredo, float caerudo ){
            return Math.abs(rubedo-this.rubedo) < Content.POTION_RECIPE_TOLERANCE &&
                Math.abs(auredo-this.auredo) < Content.POTION_RECIPE_TOLERANCE &&
                Math.abs(caerudo-this.caerudo) < Content.POTION_RECIPE_TOLERANCE;
        }

        /**
         * Checks if two recipes are too simillar - in which case one cauldron content could result in both recipes beeing valid
         * @param recipe
         * @return
         */
        public boolean checkRecipeSimilarity( PotionRecipe recipe ){
            return Math.abs(recipe.rubedo-this.rubedo) < Content.POTION_RECIPE_TOLERANCE*2 &&
                    Math.abs(recipe.auredo-this.auredo) < Content.POTION_RECIPE_TOLERANCE*2 &&
                    Math.abs(recipe.caerudo-this.caerudo) < Content.POTION_RECIPE_TOLERANCE*2;
        }

    }





}
