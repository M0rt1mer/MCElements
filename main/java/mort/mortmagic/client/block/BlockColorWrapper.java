package mort.mortmagic.client.block;

import mort.mortmagic.common.block.IBlockColorAdapter;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import javax.annotation.Nullable;

public class BlockColorWrapper implements IBlockColor {

    IBlockColorAdapter wrapped;

    public BlockColorWrapper(IBlockColorAdapter wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex){
        return wrapped.colorMultiplier(state,worldIn,pos,tintIndex);
    }

}
