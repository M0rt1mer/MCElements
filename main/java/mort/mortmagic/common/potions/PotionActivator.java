package mort.mortmagic.common.potions;

import mort.mortmagic.common.tileentity.TileCauldron;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

public interface PotionActivator extends IForgeRegistryEntry<PotionActivator> {

    default public float getActivatorPassiveStrength(TileCauldron cauldron){return 0;}

    default public String getUnlocalizedName(){ return getRegistryName().getResourceDomain() + ".potionactivator." + getRegistryType().getName() + ".name"; }

    public static class Impl extends IForgeRegistryEntry.Impl<PotionActivator> implements PotionActivator{

        public Impl( ResourceLocation loc ) {
            setRegistryName(loc);
        }
    }

}
