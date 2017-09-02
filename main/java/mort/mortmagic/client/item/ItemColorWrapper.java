package mort.mortmagic.client.item;

import mort.mortmagic.common.items.IItemColorAdapter;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;

public class ItemColorWrapper implements IItemColor {

    IItemColorAdapter wrapped;

    public ItemColorWrapper(IItemColorAdapter wrapped) {
        this.wrapped = wrapped;
    }


    @Override
    public int getColorFromItemstack(ItemStack stack, int tintIndex) {
        return wrapped.getColorFromItemstack(stack,tintIndex);
    }
}
