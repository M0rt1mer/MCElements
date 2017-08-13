package mort.mortmagic.common.runes;

import mort.mortmagic.MortMagic;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class RuneCircleStorage extends WorldSavedData {

    public static final String DATA_NAME = MortMagic.MODID + "_RuneCircleStorage";

    public RuneCircleStorage() {
        super( DATA_NAME );
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {

    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        return null;
    }

    public static RuneCircleStorage get(World world) {
        MapStorage storage =  world.getPerWorldStorage();
        RuneCircleStorage instance = (RuneCircleStorage) storage.getOrLoadData(RuneCircleStorage.class, DATA_NAME);

        if (instance == null) {
            instance = new RuneCircleStorage();
            storage.setData(DATA_NAME, instance);
        }
        return instance;
    }

    @SubscribeEvent
    public static void onWorldLoad(WorldEvent.Load event){ //attaches world storage to all worlds
        get(event.getWorld());
    }

    public void createCircle( RuneCircle circle ){
        System.out.println( "creating new runeCircle" );
    }


}
