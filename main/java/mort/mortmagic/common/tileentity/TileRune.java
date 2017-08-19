package mort.mortmagic.common.tileentity;

import mort.mortmagic.Resource;
import mort.mortmagic.common.runes.RuneCharacter;
import mort.mortmagic.common.runes.RuneCircle;
import mort.mortmagic.common.runes.RuneCircleStorage;
import mort.mortmagic.common.runes.RuneMaterial;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.annotation.Nullable;

public class TileRune extends TileEntity {

    private RuneCharacter character;
    private RuneMaterial material;

    public ResourceLocation getMaterialResLoc(){
        return (material==null)?null:material.getRegistryName();
    }

    public ResourceLocation getCharacterResLoc(){
        return (character==null)?null:character.getRegistryName();
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        character = Resource.RUNE_CHARACTER_REGISTRY.getValue( new ResourceLocation(compound.getString("character")) );
        material = Resource.RUNE_MATERIAL_REGISTRY.getValue( new ResourceLocation(compound.getString("material")) );
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound = super.writeToNBT(compound);
        compound.setString( "character", getCharacterResLoc().toString() );
        compound.setString( "material", getMaterialResLoc().toString() );
        return compound;
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


    public RuneCharacter getCharacter() {
        return character;
    }

    public RuneMaterial getMaterial() {
        return material;
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return writeToNBT( new NBTTagCompound() );
    }

    /*@Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        if(world.isRemote)
            this.readFromNBT( pkt.getNbtCompound() );
    }*/
}
