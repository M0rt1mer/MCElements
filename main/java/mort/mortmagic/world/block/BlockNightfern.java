package mort.mortmagic.world.block;

import java.util.ArrayList;
import java.util.Random;

import mort.mortmagic.Resource;
import net.minecraft.block.BlockBush;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import cpw.mods.fml.common.IWorldGenerator;

public class BlockNightfern extends BlockBush implements IWorldGenerator{

	public BlockNightfern(String uName) {
		setBlockName(uName);
	}



	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z,
			int metadata, int fortune) {
		if( Math.abs( world.getCelestialAngle(0) - 0.5f ) < 0.05f )
			return super.getDrops(world, x, y, z, metadata, fortune);
		else
			return new ArrayList<ItemStack>();
	}

	

	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World p_149633_1_,
			int p_149633_2_, int p_149633_3_, int p_149633_4_) {
		if( p_149633_1_.getWorldTime() % 20000 < 10000 )
			return null;
		else return super.getSelectedBoundingBoxFromPool(p_149633_1_, p_149633_2_,
				p_149633_3_, p_149633_4_);
	}

	

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {

		int x = (chunkX<<4) + random.nextInt(16);
		int z = (chunkZ<<4) + random.nextInt(16);
		
		if( BiomeDictionary.isBiomeOfType( world.getBiomeGenForCoords(x, z), Type.FOREST ) ){
				int y = world.getHeightValue(x, z);
				if( world.getBlock(x, y, x)==Blocks.grass ){
				//	world.setBlock(x, y+1, z, Resource.fern);
					//System.out.println( x+" "+y+" "+z );
				}
		}
		
	}

		
	
}
