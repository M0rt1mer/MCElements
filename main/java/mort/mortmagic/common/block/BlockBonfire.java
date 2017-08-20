package mort.mortmagic.common.block;

import mort.mortmagic.Content;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BlockBonfire extends Block{

	public BlockBonfire(Material p_i45394_1_) {
		super(p_i45394_1_);
	}

	@Override
	public boolean isFireSource(World world, BlockPos pos, EnumFacing side) {
		return true;
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if( worldIn.getBlockState( pos.add(0,0,1) ).getBlock() == Blocks.FIRE ){ //is on fire
			if( rand.nextInt(50) == 0 ){
				worldIn.setBlockState( pos, Content.ashesBlock.getDefaultState() );
			}
		}
		super.updateTick(worldIn, pos, state, rand);
	}

}
