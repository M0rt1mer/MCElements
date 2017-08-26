package mort.mortmagic.client;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IInitializeMyOwnModels {

    @SideOnly(Side.CLIENT)
    public void initModels();

}