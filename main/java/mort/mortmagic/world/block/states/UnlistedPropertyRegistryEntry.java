package mort.mortmagic.world.block.states;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.registries.RegistryManager;

public class UnlistedPropertyRegistryEntry implements IUnlistedProperty<ResourceLocation> {

    ResourceLocation regLocation;

    public UnlistedPropertyRegistryEntry(ResourceLocation registryLocation) {
        this.regLocation = registryLocation;
    }

    @Override
    public String getName() {
        return regLocation.toString();
    }

    @Override
    public boolean isValid(ResourceLocation value) {
        return RegistryManager.ACTIVE.getRegistry( regLocation ).containsKey( value );
    }

    @Override
    public Class<ResourceLocation> getType() {
        return ResourceLocation.class;
    }

    @Override
    public String valueToString(ResourceLocation value) {
        return null;
    }

    @Override
    public String toString() {
        return "Property [" + regLocation + "]";
    }
}
