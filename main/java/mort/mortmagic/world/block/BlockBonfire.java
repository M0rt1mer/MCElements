package mort.mortmagic.world.block;

import java.util.Random;

import mort.mortmagic.Resource;
import mort.mortmagic.world.EntityItemInvulnerable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockBonfire extends Block{

	public BlockBonfire(Material p_i45394_1_) {
		super(p_i45394_1_);
	}

	@Override
	public boolean isFireSource(World world, int x, int y, int z, ForgeDirection side) {
		return true;
	}

	@Override
	public void updateTick(World world, int x, int y,	int z, Random p_149674_5_) {
		if( world.getBlock(x, y, z)==Blocks.fire ){
			ItemStack stk = new ItemStack( Resource.ashes, 1 );
			EntityItem itm = new EntityItemInvulnerable( world, x+0.5f, y+0.5f, z+0.5f, stk );
			world.spawnEntityInWorld(itm);
			world.setBlock(x, y, z, Blocks.fire);
		}
		
	}

}
