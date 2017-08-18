package mort.mortmagic.common.block.states;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryManager;

public class UnlistedPropertyRegistryEntry implements IUnlistedProperty<ResourceLocation> {

    IForgeRegistry<?> registry;

    public UnlistedPropertyRegistryEntry(IForgeRegistry<?> registry ) {
        this.registry = registry;
    }

    @Override
    public String getName() {
        return RegistryManager.ACTIVE.getName(registry).toString();
    }

    @Override
    public boolean isValid(ResourceLocation value) {
        return value == null || registry.containsKey(value);
    }

    @Override
    public Class<ResourceLocation> getType() {
        return ResourceLocation.class;
    }

    @Override
    public String valueToString(ResourceLocation value) {
        return value.toString();
    }
}
