package mort.mortmagic.common.runes;

import mort.mortmagic.MortMagic;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber
public class RuneCircleStorage extends WorldSavedData {

    public static final String DATA_NAME = MortMagic.MODID + "_RuneCircleStorage";

    List<RuneCircle> list = new ArrayList<>();


    public RuneCircleStorage( String str ) {
        super( str );
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        list.clear();
        for(NBTBase base : nbt.getTagList("list",10)){
            list.add( new RuneCircle( (NBTTagCompound)base ) );
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        NBTTagList lst = new NBTTagList();
        for( RuneCircle circle : list ){
            lst.appendTag( circle.writeToNBT() );
        }
        compound.setTag("list",lst);
        return compound;
    }

    public static RuneCircleStorage get(World world) {
        MapStorage storage =  world.getPerWorldStorage();
        RuneCircleStorage instance = (RuneCircleStorage) storage.getOrLoadData(RuneCircleStorage.class, DATA_NAME);

        if (instance == null) {
            instance = new RuneCircleStorage(DATA_NAME);
            storage.setData(DATA_NAME, instance);
        }
        return instance;
    }

    @SubscribeEvent
    public static void event_onWorldLoad(WorldEvent.Load event){ //attaches world storage to all worlds
        get(event.getWorld());
    }

    public void createCircle( RuneCircle circle ){
        System.out.println( "creating new runeCircle" );
        list.add( circle );
        markDirty();
    }

    public List<RuneCircle> findCircle(Vec3d pos, RuneWord type){
        return findCircle( new BlockPos(pos.x,pos.y, pos.x), type );
    }

    public List<RuneCircle> findCircle(BlockPos pos, RuneWord type){
        List<RuneCircle> ret = new ArrayList<>();
        for( RuneCircle c : list ){
            if( c.word == type && c.contains(pos) ){
                ret.add(c);
            }
        }
        return ret;
    }

}
