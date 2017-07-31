package mort.mortmagic.world.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;


/**
 * Block representing TileEntity-containing fire. Either normal fire, temporarily converted to altar fire, or WildFire or any other special kind of fire
 * damage - 0 regular fire, 1 wildfire
 * @author Martin
 *
 */
public class BlockWildFire extends BlockContainer {

	public BlockWildFire() {
		super(Material.FIRE);
		setBlockUnbreakable();
		setHardness(6000000);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityWildfire();
	
	}

	public boolean onBlockActivated(World world, int x,
			int y, int z, EntityPlayer player,
			int p_149727_6_, float p_149727_7_, float p_149727_8_,
			float p_149727_9_) {
		
		/*if( player.getHeldItem() != null ){
			ItemStack stk = player.getHeldItem().splitStack(1);
			if( SacrificeHelper.canBeOffered(player.getHeldItem()) )
				((TileEntityWildfire)world.getTileEntity(x, y, z)).addSacrifice( new ItemSacrifice( stk ) );
			else
				SacrificeHelper.finishOfferingAtAltar(((TileEntityWildfire)world.getTileEntity(x, y, z)), stk);
			return true;
		}*/
			
		return false;
	}

	
	
}
