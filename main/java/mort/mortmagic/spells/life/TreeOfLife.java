package mort.mortmagic.spells.life;

import mort.mortmagic.Resource;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
/*
public class TreeOfLife implements IMagicallyGrowable{

	@Override
	public boolean isApplicable(World wld, int x, int y, int z) {
		Block b = wld.getBlock(x, y, z); 
		return b == Resource.liveDirt || b == Resource.treeCore || b == Resource.treeRoot;
	}

	@Override
	public void apply(World wld, int x, int y, int z) {
		Block b = wld.getBlock(x, y, z);
		if( b == Resource.liveDirt ){
			wld.setBlock(x, y+1, z, Resource.treeCore);
			wld.setBlock(x, y, z, Blocks.dirt);
		}
		else if( b == Resource.treeCore ){
			int shift = wld.rand.nextInt(6);
			for(int i = 0; i<6;i++){
				int side = (i+shift)%6;					
				if( wld.isAirBlock(x+Facing.offsetsXForSide[side], y+Facing.offsetsYForSide[side], z+Facing.offsetsZForSide[side]) ){
					if(side==1){
					/*	wld.setBlock(x+Facing.offsetsXForSide[side], y+Facing.offsetsYForSide[side], z+Facing.offsetsZForSide[side],
							Resource.treeRoot, 12+ Direction.facingToDirection[side], 3 );*/
					/*}
					else if(side!=0){
						wld.setBlock(x+Facing.offsetsXForSide[side], y+Facing.offsetsYForSide[side], z+Facing.offsetsZForSide[side],
								Resource.treeRoot, 12+Direction.facingToDirection[side], 3 );
						System.out.println("starting root with facing "+side+"("+Direction.facingToDirection[side]);
					}
					return;
				}
			}
			
		}
		else if( b == Resource.treeRoot ){
			int side = Direction.directionToFacing[ wld.getBlockMetadata(x, y, z)%3 ];
			System.out.println("found tree rooot with facing "+side);
			int grow = wld.getBlockMetadata(x, y, z)/4;
			boolean canGrow = grow>0;
			//can grow
			for( int i = 0; i<6; i++){
				if( wld.getBlock(x+Facing.offsetsXForSide[i], y+Facing.offsetsYForSide[i], z+Facing.offsetsZForSide[i])==Resource.treeRoot && 
						wld.getBlockMetadata(x+Facing.offsetsXForSide[i], y+Facing.offsetsYForSide[i], z+Facing.offsetsZForSide[i])/4 < grow )
					canGrow = false;
			}
			if(canGrow){
				if(Math.random()<0.25){ //choose different direction to grow
					side = wld.rand.nextInt(6);
				}
				for(int i = 0; i<6;i++){
					int side2 = (i+side)%6;					
					if( wld.isAirBlock(x+Facing.offsetsXForSide[side2], y+Facing.offsetsYForSide[side2], z+Facing.offsetsZForSide[side2]) ){
						wld.setBlock(x+Facing.offsetsXForSide[side2], y+Facing.offsetsYForSide[side2], z+Facing.offsetsZForSide[side2],
								Resource.treeRoot, (1<<(grow-1))+Direction.facingToDirection[wld.getBlockMetadata(x, y, z)%3], 3 );
						return;
					}
				}
			}
		}
		
		
	}

	@Override
	public float getCost() {
		return 20;
	}
	
	

}*/
