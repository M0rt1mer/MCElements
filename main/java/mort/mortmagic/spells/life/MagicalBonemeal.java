package mort.mortmagic.spells.life;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.world.World;

public class MagicalBonemeal implements IMagicallyGrowable{

	@Override
	public boolean isApplicable(World wld, int x, int y, int z) {
		Block b = wld.getBlock(x, y, z);
		if( b instanceof IGrowable ){
			return ((IGrowable)b).func_149851_a(wld, x, y, z, wld.isRemote );
		}
		return false;
	}

	@Override
	public void apply(World wld, int x, int y, int z) {
		Block b = wld.getBlock(x, y, z);
		if( b instanceof IGrowable ){
			((IGrowable)b).func_149853_b(wld, wld.rand, x, y, z);
		}
	}

	@Override
	public float getCost() {
		return 10;
	}

	
	
}
