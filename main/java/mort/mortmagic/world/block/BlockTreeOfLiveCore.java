package mort.mortmagic.world.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockTreeOfLiveCore extends BlockContainer{

	public BlockTreeOfLiveCore() {
		super( Material.wood );
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityTreeOfLifeCore();
	}

	@SideOnly(Side.CLIENT)
    private IIcon top;
    @SideOnly(Side.CLIENT)
    private IIcon side;
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int p_149691_2_)
    {
        if (side == 1 || side == 0){
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
