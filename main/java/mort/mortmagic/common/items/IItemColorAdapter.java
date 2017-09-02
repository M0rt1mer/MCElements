package mort.mortmagic.common.items;

import net.minecraft.item.ItemStack;

public interface IItemColorAdapter{

    int getColorFromItemstack(ItemStack stack, int tintIndex);

}
