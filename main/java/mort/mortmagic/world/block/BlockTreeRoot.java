package mort.mortmagic.world.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.Direction;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;

public class BlockTreeRoot extends Block{

	public BlockTreeRoot() {
		super(Material.wood);
	}

	@SideOnly(Side.CLIENT)
    private IIcon top;
    @SideOnly(Side.CLIENT)
    private IIcon side;
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metadata)
    {
    	int rotation = metadata%3;
    	side = Direction.facingToDirection[side];
        if (side == rotation || side == Direction.rotateOpposite[rotation] ){
            return this.top;
        }
        return this.side;
    }

    @SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister p_149651_1_) {
		this.side = p_149651_1_.registerIcon( getTextureName()+"_side" );
		this.top = p_149651_1_.registerIcon( getTextureName()+"_top" );
	}
	
	
}
