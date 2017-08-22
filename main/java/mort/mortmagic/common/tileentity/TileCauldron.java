package mort.mortmagic.common.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;
import java.util.List;

public class TileCauldron extends TileEntity {

    public List<ItemStack> items = new ArrayList<>();


    public boolean throwItemIn( ItemStack stk ){
        return false;

    }

}
