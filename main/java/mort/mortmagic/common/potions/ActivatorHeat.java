package mort.mortmagic.common.potions;

import mort.mortmagic.common.tileentity.TileCauldron;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;

/**
 * Simple heating - fire creates heat, ice drains heat
 */
public class ActivatorHeat extends PotionActivator.Impl {

    public ActivatorHeat(ResourceLocation loc) {
        super(loc);
    }

    @Override
    public float getActivatorPassiveStrength(TileCauldron cauldron) {
        IBlockState state = cauldron.getWorld().getBlockState( cauldron.getPos().down() );
        if( state.getBlock() == Blocks.FIRE )
            return 1;
        return 0;
    }

}
