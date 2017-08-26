package mort.mortmagic.common.block;

import mort.mortmagic.MortMagic;
import mort.mortmagic.client.IInitializeMyOwnModels;
import mort.mortmagic.common.block.states.UnlistedPropertyRegistryEntry;
import mort.mortmagic.common.runes.RuneCharacter;
import mort.mortmagic.common.runes.RuneMaterial;
import mort.mortmagic.common.tileentity.TileRune;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;

import static net.minecraft.util.EnumFacing.values;

public class BlockRune extends Block implements ITileEntityProvider, IInitializeMyOwnModels{

    public static final UnlistedPropertyRegistryEntry runeCharacterProperty = new UnlistedPropertyRegistryEntry( GameRegistry.findRegistry( RuneCharacter.class ) );
    public static final UnlistedPropertyRegistryEntry runeMaterialProperty = new UnlistedPropertyRegistryEntry( GameRegistry.findRegistry(RuneMaterial.class) );
    public static final PropertyDirection FACING = BlockDirectional.FACING;


    public static final double AABB_WIDTH = 0.0625D;
    protected static final AxisAlignedBB RUNE_EAST_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, AABB_WIDTH, 1.0D, 1.0D);
    protected static final AxisAlignedBB RUNE_WEST_AABB = new AxisAlignedBB(1-AABB_WIDTH, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
    protected static final AxisAlignedBB RUNE_SOUTH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, AABB_WIDTH);
    protected static final AxisAlignedBB RUNE_NORTH_AABB = new AxisAlignedBB(0.0D, 0.0D, 1-AABB_WIDTH, 1.0D, 1.0D, 1.0D);
    protected static final AxisAlignedBB RUNE_DOWN_AABB = new AxisAlignedBB(0.0D, 1-AABB_WIDTH, 0.0D, 1.0D, 1.0D, 1.0D);
    protected static final AxisAlignedBB RUNE_UP_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, AABB_WIDTH, 1.0D);

    public BlockRune() {
        super( Material.WOOD );
    }

    @Override
    public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {
        return this.canAttachTo( worldIn, pos.offset( side.getOpposite() ), side );
    }

    protected boolean canAttachTo(World world, BlockPos pos, EnumFacing facing ){
        return world.isSideSolid( pos, facing );
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
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        EnumFacing enumfacing = state.getValue(FACING);

        if (!this.canAttachTo(worldIn, pos.offset(enumfacing.getOpposite()), enumfacing))
        {
            worldIn.setBlockToAir(pos);
        }

    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        switch( state.getValue(FACING) ){
            case NORTH: return RUNE_NORTH_AABB;
            case DOWN: return RUNE_DOWN_AABB;
            case EAST: return RUNE_EAST_AABB;
            case SOUTH: return RUNE_SOUTH_AABB;
            case UP: return RUNE_UP_AABB;
            case WEST: return RUNE_WEST_AABB;
        }
        return RUNE_WEST_AABB;
    }

    @Override
    public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
        return false;
    }

    //<editor-fold desc="ItemStack integration">
    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        TileRune tile = (TileRune) worldIn.getTileEntity( pos );
        tile.onRunePlaced( getRuneCharacterFromStack( stack ), getRuneMaterialFromStack( stack ) );
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        for( RuneMaterial mat : GameRegistry.findRegistry( RuneMaterial.class ) )
            for( RuneCharacter chr : GameRegistry.findRegistry( RuneCharacter.class ))
                items.add( createItemStack( chr, mat ) );
    }

    public RuneMaterial getRuneMaterialFromStack( ItemStack stack ){
        if( stack.hasTagCompound() && stack.getTagCompound().hasKey("rune_material") )
            return GameRegistry.findRegistry( RuneMaterial.class ).getValue( new ResourceLocation( stack.getTagCompound().getString("rune_material") ) );
        else
            return null;
    }
    public RuneCharacter getRuneCharacterFromStack( ItemStack stack ){
        if( stack.hasTagCompound() && stack.getTagCompound().hasKey("rune_character") )
            return GameRegistry.findRegistry( RuneCharacter.class ).getValue( new ResourceLocation( stack.getTagCompound().getString("rune_character") ) );
        else
            return null;
    }
    public boolean isStackValidRune( ItemStack stack ){
        return stack.hasTagCompound() && stack.getTagCompound().hasKey("rune_character") && stack.getTagCompound().hasKey("rune_material");
    }

    public ItemStack createItemStack( RuneCharacter chr, RuneMaterial mat ){
        ItemStack stk = new ItemStack( this, 1 );
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString( "rune_material", mat.getRegistryName().toString() );
        tag.setString( "rune_character", chr.getRegistryName().toString() );
        stk.setTagCompound( tag );
        return stk;
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        TileRune rune = ((TileRune)world.getTileEntity(pos));
        if( rune != null )
            drops.add( createItemStack( rune.getCharacter(), rune.getMaterial() ) );
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void initModels() {
        HashMap<ResourceLocation,ModelResourceLocation[]> models = new HashMap<>();
        Item myItem = Item.getItemFromBlock(this);

        for( RuneMaterial mat : GameRegistry.findRegistry( RuneMaterial.class ) )
            for( RuneCharacter chr : GameRegistry.findRegistry( RuneCharacter.class )){
                ModelResourceLocation mrl = getModelResLocation(chr.getRegistryName(),mat.getRegistryName(), true);
                ModelBakery.registerItemVariants( myItem, mrl );
            }

        ModelLoader.setCustomMeshDefinition(myItem,
                stack -> isStackValidRune(stack)?getModelResLocation( getRuneCharacterFromStack(stack).getRegistryName(), getRuneMaterialFromStack(stack).getRegistryName(), true )
                        :new ModelResourceLocation( "minecraft:missingTexture", "inventory" )  );
    }

    //</editor-fold>

    //<editor-fold desc="Rendering">
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    @SideOnly(Side.CLIENT)
    protected ModelResourceLocation getModelResLocation(ResourceLocation chr, ResourceLocation mat, boolean isInventory ){
        if(isInventory)
            return new ModelResourceLocation( MortMagic.MODID + ":rune_" + chr.getResourcePath() + "_" + mat.getResourcePath(), "inventory" );
        else
            return new ModelResourceLocation( MortMagic.MODID + ":rune_" + chr.getResourcePath() + "_" + mat.getResourcePath() );
    }
    //</editor-fold>

    //<editor-fold desc="Blockstate">
    /*@Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        TileRune thisRune = (TileRune) worldIn.getTileEntity(pos);
        if(thisRune == null || !(state instanceof IExtendedBlockState) )
            return state;
        return ((IExtendedBlockState)state).withProperty( runeMaterialProperty, thisRune.getMaterialResLoc() )
                                            .withProperty( runeCharacterProperty, thisRune.getCharacterResLoc() );
    }*/

    @Override
    public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos) {
        TileRune thisRune = (TileRune) world.getTileEntity(pos);
        if(thisRune == null || !(state instanceof IExtendedBlockState) )
            return state;
        return ((IExtendedBlockState)state).withProperty( runeMaterialProperty, thisRune.getMaterialResLoc() )
                .withProperty( runeCharacterProperty, thisRune.getCharacterResLoc() );
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getIndex();
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty( FACING, values()[meta]);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        //return new BlockStateContainer(this,new IProperty[]{FACING});
        return new ExtendedBlockState(this, new IProperty[]{FACING}, new IUnlistedProperty[]{runeCharacterProperty,runeMaterialProperty});
    }
    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {

        if (this.canAttachTo(world, pos.offset(facing.getOpposite()), facing))
        {
            return this.getDefaultState().withProperty(FACING, facing);
        }
        return this.getDefaultState();
    }
    //</editor-fold>

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileRune();
    }



    //-----------------------DEBUG


    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add( stack.getTagCompound().toString() );
    }
}
