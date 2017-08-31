package mort.mortmagic.common;

import mort.mortmagic.Content;
import mort.mortmagic.common.utils.LootConditionSerializerSingleton;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.conditions.LootConditionManager;

import java.util.Random;

public class ConditionBleeding implements LootCondition {

    public ConditionBleeding( ResourceLocation resourceLocation ) {
        LootConditionManager.registerCondition( new LootConditionSerializerSingleton<>( resourceLocation, ConditionBleeding.class, this ));
    }

    @Override
    public boolean testCondition(Random rand, LootContext context) {
        if( context.getLootedEntity() instanceof EntityLiving){
            EntityLiving lvng = (EntityLiving)context.getLootedEntity();
            if( lvng.isPotionActive(Content.potion_bleed ) )
                return true;
        }
        return false;
    }

}
