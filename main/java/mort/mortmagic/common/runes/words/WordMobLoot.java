package mort.mortmagic.common.runes.words;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import mort.mortmagic.Content;
import mort.mortmagic.common.items.ItemDagger;
import mort.mortmagic.common.runes.RuneCircleStorage;
import mort.mortmagic.common.runes.RuneWord;
import mort.mortmagic.common.utils.LootConditionSerializerSingleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.conditions.LootConditionManager;

import java.util.Random;

public class WordMobLoot extends RuneWord implements LootCondition {

    public WordMobLoot(ResourceLocation loc) {
        super(loc);
        LootConditionManager.registerCondition( new LootConditionSerializerSingleton<WordMobLoot>( this.getRegistryName(), WordMobLoot.class,this) );
    }

    @Override
    public boolean testCondition(Random rand, LootContext context) {
        EntityPlayer plr = (EntityPlayer) context.getKillerPlayer();
        if(plr!=null){
            if( plr.getHeldItem( EnumHand.MAIN_HAND ).getItem() instanceof ItemDagger){
                if( RuneCircleStorage.get( context.getLootedEntity().getEntityWorld() ).findCircle(context.getLootedEntity().getPositionVector(),this).size() > 0 )
                    return true;
            }
        }
        return false;
    }

}
