package mort.mortmagic.api;

import mort.mortmagic.Content;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PotionRecipeRegistry {

    private List<PotionRecipe> list = new ArrayList<>();

    public void register( PotionRecipe recipe ){
        for( PotionRecipe existingRecipe : list ){
            if( existingRecipe.checkRecipeSimilarity(recipe) )
                throw new IllegalArgumentException("Recipe collides with already existing recipe");
        }
        list.add(recipe);
    }

    /**
     * Returns the resulting itemstack. It is the stack template in recipe, it needs to be cloned before use.
     * @param rubedo
     * @param auredo
     * @param caerudo
     * @return
     */
    public ItemStack findPotion( float rubedo, float auredo, float caerudo ){
        for(PotionRecipe recipe : list){
            if( recipe.checkRecipe(rubedo,auredo,caerudo) )
                return recipe.result;
        }
        return null;
    }

    public static class PotionRecipe {

        public final float rubedo;
        public final float auredo;
        public final float caerudo;

        public final ItemStack result;

        public PotionRecipe(float rubedo, float auredo, float caerudo, ItemStack result) {
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
        public boolean checkRecipe(float rubedo, float auredo, float caerudo ){
            return Math.abs(rubedo-this.rubedo) < Content.POTION_RECIPE_TOLERANCE &&
                Math.abs(auredo-this.auredo) < Content.POTION_RECIPE_TOLERANCE &&
                Math.abs(caerudo-this.caerudo) < Content.POTION_RECIPE_TOLERANCE;
        }

        /**
         * Checks if two recipes are too simillar - in which case one cauldron content could result in both recipes beeing valid
         * @param recipe
         * @return
         */
        public boolean checkRecipeSimilarity(PotionRecipe recipe ){
            return Math.abs(recipe.rubedo-this.rubedo) < Content.POTION_RECIPE_TOLERANCE*2 &&
                    Math.abs(recipe.auredo-this.auredo) < Content.POTION_RECIPE_TOLERANCE*2 &&
                    Math.abs(recipe.caerudo-this.caerudo) < Content.POTION_RECIPE_TOLERANCE*2;
        }

    }





}
