package mort.mortmagic.world.tileentity;

import mort.mortmagic.runes.RuneCharacter;
import mort.mortmagic.runes.RuneMaterial;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileRune extends TileEntity {

    public RuneCharacter character;
    public RuneMaterial material;

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        return super.writeToNBT(compound);
    }
}
