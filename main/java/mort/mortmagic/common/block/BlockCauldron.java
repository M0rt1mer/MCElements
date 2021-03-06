package mort.mortmagic.common.block;

import mort.mortmagic.common.potions.PotionColoringHelper;
import mort.mortmagic.common.tileentity.TileCauldron;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.common.property.Properties;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockCauldron extends Block implements ITileEntityProvider, IBlockColorAdapter {

    //public static PropertyBool WATER_STATE = PropertyBool.create("water");
    public static IProperty<Integer> IS_WATER = PropertyInteger.create("is_water",0,2);

    public BlockCauldron() {
        super(Material.IRON);
        setDefaultState( blockState.getBaseState().withProperty(IS_WATER,0) );
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState();
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return 0;
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{IS_WATER} );
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

    @Override
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        if( !worldIn.isRemote && entityIn instanceof EntityItem ){
            EntityItem itm = (EntityItem)entityIn;
            if( ((TileCauldron)worldIn.getTileEntity(pos)).throwItemIn( itm.getItem() ) ) //if it was accepted,
                worldIn.removeEntity( itm );
        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack itemstack = playerIn.getHeldItem(hand);
        if (itemstack.isEmpty()) {
            return true;
        } else {
            TileCauldron tile = (TileCauldron) worldIn.getTileEntity(pos);
            Item item = itemstack.getItem();
            if (item == Items.WATER_BUCKET && !tile.hasWater() ) {
                if (!worldIn.isRemote) {
                    if (!playerIn.capabilities.isCreativeMode)
                        playerIn.setHeldItem(hand, new ItemStack(Items.BUCKET));
                    tile.setHasWater( true );
                    worldIn.playSound((EntityPlayer) null, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
                }
                return true;
            } else if (item == Items.BUCKET && tile.hasWater() ) {
                if (!playerIn.capabilities.isCreativeMode)
                    playerIn.setHeldItem(hand, new ItemStack(Items.WATER_BUCKET));
                tile.setHasWater(false);
                worldIn.playSound((EntityPlayer) null, pos, SoundEvents.ITEM_BUCKET_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                return true;
            }
            else if( item == Items.GLASS_BOTTLE ){
                TileCauldron cauldron = (TileCauldron)worldIn.getTileEntity(pos);
                if( cauldron == null )
                    return false;
                if(worldIn.isRemote)
                    return true;
                ItemStack stk = cauldron.bottleItem( itemstack );
                playerIn.addItemStackToInventory( stk );
                tile.setHasWater(false);
            }
            else if( tile.hasWater() )
                return ((TileCauldron)worldIn.getTileEntity(pos)).throwItemIn( itemstack );
            return false;
        }
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileCauldron();
    }



    @Override
    public int colorMultiplier(IBlockState state,  IBlockAccess worldIn, BlockPos pos, int tintIndex) {
        TileCauldron cauldron = (TileCauldron)worldIn.getTileEntity(pos);
        if( cauldron == null )
            return PotionColoringHelper.TINT_WHITE; //white
        else return cauldron.getColor();
    }

}
