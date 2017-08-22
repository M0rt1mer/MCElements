package mort.mortmagic.common.block;

import mort.mortmagic.MortMagic;
import mort.mortmagic.common.tileentity.TileCauldron;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class BlockCauldron extends Block implements IBlockColor, ITileEntityProvider {

    public BlockCauldron() {
        super(Material.IRON);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex) {
        return 354353;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
        return false;
    }

    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        if( !worldIn.isRemote && entityIn instanceof EntityItem ){
            EntityItem itm = (EntityItem)entityIn;
            if( ((TileCauldron)worldIn.getTileEntity(pos)).throwItemIn( itm.getItem() ) ) //if it was accepted,
                worldIn.removeEntity( itm );
        }
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileCauldron();
    }
}
