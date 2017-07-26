package mort.mortmagic.world.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;

public class BlockLiveDirt extends Block{

	public BlockLiveDirt(Material p_i45394_1_) {
		super(p_i45394_1_);
	}
	
    @SideOnly(Side.CLIENT)
    private IIcon top;
    @SideOnly(Side.CLIENT)
    private IIcon side;

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int p_149691_1_, int p_149691_2_)
    {
        if (p_149691_1_ == 1){
            return this.top;
        }

        if (p_149691_1_ != 0){
            return this.side;
        }
        
        return Blocks.dirt.getIcon(0, 0);
    }

    @SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister p_149651_1_) {
		this.side = p_149651_1_.registerIcon( getTextureName()+"_side" );
		this.top = p_149651_1_.registerIcon( getTextureName()+"_top" );
	}

}
