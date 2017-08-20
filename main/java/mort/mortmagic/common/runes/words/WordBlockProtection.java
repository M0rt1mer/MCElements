package mort.mortmagic.common.runes.words;

import mort.mortmagic.common.runes.RuneCircleStorage;
import mort.mortmagic.common.runes.RuneWord;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class WordBlockProtection extends RuneWord{

    public WordBlockProtection(ResourceLocation loc) {
        super(loc);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void event_playerBreakSpeed(PlayerEvent.BreakSpeed event){
        if( RuneCircleStorage.get(event.getEntityPlayer().getEntityWorld()).findCircle( event.getPos(), this ).size() > 0 ){
            event.setNewSpeed(event.getNewSpeed() * 0.1f );
        }

    }

}
