package mort.mortmagic.world.block.states;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.registries.RegistryManager;

public class UnlistedPropertyRegistryEntry implements IUnlistedProperty<String> {

    ResourceLocation regLocation;

    public UnlistedPropertyRegistryEntry(ResourceLocation registryLocation) {
        this.regLocation = registryLocation;
    }

    @Override
    public String getName() {
        return regLocation.toString();
    }

    @Override
    public boolean isValid(String value) {
        return RegistryManager.ACTIVE.getRegistry( regLocation ).containsKey( new ResourceLocation(value) );
    }

    @Override
    public Class<String> getType() {
        return String.class;
    }

    @Override
    public String valueToString(String value) {
        return value;
    }
}
