package mort.mortmagic.common.tileentity;

import mort.mortmagic.Resource;
import mort.mortmagic.common.runes.RuneCharacter;
import mort.mortmagic.common.runes.RuneCircle;
import mort.mortmagic.common.runes.RuneCircleStorage;
import mort.mortmagic.common.runes.RuneMaterial;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileRune extends TileEntity {

    public RuneCharacter character;
    public RuneMaterial material;

    public ResourceLocation getMaterialResLoc(){
        return (material==null)?null:material.getRegistryName();
    }

    public ResourceLocation getCharacterResLoc(){
        return (character==null)?null:character.getRegistryName();
    }

    @Override
    public void onChunkUnload() {
        getTileData().setString( "character", character.getRegistryName().toString() );
        getTileData().setString( "material", material.getRegistryName().toString() );
    }

    @Override
    public void onLoad() {
        character = Resource.RUNE_CHARACTER_REGISTRY.getValue( new ResourceLocation(getTileData().getString("character")) );
        material = Resource.RUNE_MATERIAL_REGISTRY.getValue( new ResourceLocation(getTileData().getString("material")) );
    }

    /**
     * Called after rune is placed, by the RuneBlock
     */
    public void onRunePlaced(RuneCharacter chr, RuneMaterial mat ){
        this.character = chr;
        this.material = mat;
        RuneCircle c = RuneCircle.tryFindCircle( getWorld(), getPos() );
        if( c!=null )
            RuneCircleStorage.get(getWorld()).createCircle( c );
        //TODO: spawn particles
    }

}
