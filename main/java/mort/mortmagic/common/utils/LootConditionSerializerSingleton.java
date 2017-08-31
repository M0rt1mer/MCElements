package mort.mortmagic.common.utils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.conditions.LootCondition;

public class LootConditionSerializerSingleton<T extends LootCondition> extends LootCondition.Serializer<T> {

    public final T INSTANCE;

    public LootConditionSerializerSingleton(ResourceLocation location, Class clazz, T instance ) {
        super(location, clazz);
        INSTANCE = instance;
    }

    @Override
    public void serialize(JsonObject json, LootCondition value, JsonSerializationContext context) {

    }

    @Override
    public T deserialize(JsonObject json, JsonDeserializationContext context) {
        return INSTANCE;
    }
}
