package mort.mortmagic.world.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class BlockSolid extends Block{
	
	private String [] classes;
	private IIcon [] icons;

	public BlockSolid( String[] names, Material p_i45394_1_) {
		super(p_i45394_1_);
		this.classes = names;
	}

	@Override
	public IIcon getIcon(int p_149691_1_, int meta) {
		return icons[meta];
	}

	@Override
	public void registerBlockIcons(IIconRegister p_149651_1_) {
		icons = new IIcon[classes.length];
		for(int i=0;i<classes.length;i++)
			icons[i] = p_149651_1_.registerIcon( getTextureName() + "_" + classes[i] );
	}

	@Override
	public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_,
			List p_149666_3_) {
		for(int i = 0; i<classes.length;i++)
			p_149666_3_.add( new ItemStack(this,1,i) );

	}
	
	
	
	
	
	
}
