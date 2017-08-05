package mort.mortmagic.world.block;

import com.google.common.collect.Maps;
import mort.mortmagic.MortMagic;
import mort.mortmagic.Resource;
import mort.mortmagic.runes.RuneCharacter;
import mort.mortmagic.runes.RuneMaterial;
import mort.mortmagic.spells.Element;
import mort.mortmagic.world.block.states.UnlistedPropertyRegistryEntry;
import mort.mortmagic.world.tileentity.TileRune;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
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
import java.util.Map;

public class BlockRune extends Block implements ITileEntityProvider{

    public static final UnlistedPropertyRegistryEntry runeCharacterProperty = new UnlistedPropertyRegistryEntry( new ResourceLocation( MortMagic.MODID, "rune_characters" ) );
    public static final UnlistedPropertyRegistryEntry runeMaterialProperty = new UnlistedPropertyRegistryEntry( new ResourceLocation( MortMagic.MODID, "rune_materials" ) );
    public static final PropertyDirection FACING = BlockHorizontal.FACING;


    public BlockRune() {
        super( Material.WOOD );
    }

    @SideOnly(Side.CLIENT)
    public void initModels(){
                BlockRune thisBlockRune = this;
                ModelLoader.setCustomStateMapper(this, new StateMapperBase() {
                    @Override
                    protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
                        IExtendedBlockState ext = (IExtendedBlockState) state;
                        return thisBlockRune.getModelResLocation( ext.getValue(runeCharacterProperty), ext.getValue(runeMaterialProperty), false );
//                        return new ModelResourceLocation(
  //                              MortMagic.MODID + ":rune_"+ ext.getValue(runeCharacterProperty).getResourcePath() + "_"+ ext.getValue(runeMaterialProperty).getResourcePath() );
                    }

                    @Override
                    public Map<IBlockState, ModelResourceLocation> putStateModelLocations(Block blockIn) {
                        Map<IBlockState, ModelResourceLocation> locations = new HashMap<>();
                        for( IBlockState state : blockIn.getBlockState().getValidStates() ){
                            for( RuneMaterial mat : GameRegistry.findRegistry( RuneMaterial.class ) )
                                for( RuneCharacter chr : GameRegistry.findRegistry( RuneCharacter.class )) {
                                    IExtendedBlockState ext = ((IExtendedBlockState)state)
                                            .withProperty( runeMaterialProperty, mat.getRegistryName() )
                                            .withProperty( runeCharacterProperty, chr.getRegistryName() );
                                    locations.put( ext, getModelResourceLocation(ext) );
                                }
                        }
                        return locations;
                    }
                } );
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {

        if (this.canAttachTo(world, pos.offset(facing.getOpposite()), facing))
        {
            return this.getDefaultState().withProperty(FACING, facing);
        }
        return this.getDefaultState();
    }

    @Override
    public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {
        return this.canAttachTo( worldIn, pos.offset( side.getOpposite() ), side );
    }

    @Override
    public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
        return false;
    }

    protected boolean canAttachTo(World world, BlockPos pos, EnumFacing facing ){
        return world.isSideSolid( pos, facing );
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getIndex();
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty( FACING, EnumFacing.values()[meta]);
    }

    //<editor-fold desc="ItemStack integration">
    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        TileRune tile = (TileRune) worldIn.getTileEntity( pos );
        tile.character = getRuneCharacterFromStack( stack );
        tile.material = getRuneMaterialFromStack( stack );
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        for( RuneMaterial mat : GameRegistry.findRegistry( RuneMaterial.class ) )
            for( RuneCharacter chr : GameRegistry.findRegistry( RuneCharacter.class ))
                items.add( createItemStack( chr, mat ) );
    }

    public RuneMaterial getRuneMaterialFromStack( ItemStack stack ){
        return GameRegistry.findRegistry( RuneMaterial.class ).getValue( new ResourceLocation( stack.getTagCompound().getString("rune_material") ) );
    }
    public RuneCharacter getRuneCharacterFromStack( ItemStack stack ){
        return GameRegistry.findRegistry( RuneCharacter.class ).getValue( new ResourceLocation( stack.getTagCompound().getString("rune_character") ) );
    }

    public ItemStack createItemStack( RuneCharacter chr, RuneMaterial mat ){
        ItemStack stk = new ItemStack( this, 1 );
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString( "rune_material", mat.getRegistryName().toString() );
        tag.setString( "rune_character", chr.getRegistryName().toString() );
        stk.setTagCompound( tag );
        return stk;
    }

    @SideOnly(Side.CLIENT)
    public void initItemModels() {
        HashMap<ResourceLocation,ModelResourceLocation[]> models = new HashMap<>();
        Item myItem = Item.getItemFromBlock(this);

        for( RuneMaterial mat : GameRegistry.findRegistry( RuneMaterial.class ) )
            for( RuneCharacter chr : GameRegistry.findRegistry( RuneCharacter.class )){
                ModelResourceLocation mrl = getModelResLocation(chr.getRegistryName(),mat.getRegistryName(), true);
                ModelBakery.registerItemVariants( myItem, mrl );
            }

        ModelLoader.setCustomMeshDefinition(myItem, new ItemMeshDefinition() {
            @Override
            public ModelResourceLocation getModelLocation(ItemStack stack) {
                return getModelResLocation( getRuneCharacterFromStack(stack).getRegistryName(), getRuneMaterialFromStack(stack).getRegistryName(), true );
            }
        });
    }

    //</editor-fold>

    protected ModelResourceLocation getModelResLocation(ResourceLocation chr, ResourceLocation mat, boolean isInventory ){
        if(isInventory)
            return new ModelResourceLocation( MortMagic.MODID + ":rune_" + chr.getResourcePath() + "_" + mat.getResourcePath(), "inventory" );
        else
            return new ModelResourceLocation( MortMagic.MODID + ":rune_" + chr.getResourcePath() + "_" + mat.getResourcePath() );
    }

    @Override
    public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos) {
        TileRune thisRune = (TileRune) world.getTileEntity(pos);
        if( state instanceof IExtendedBlockState) {
            return ((IExtendedBlockState)super.getExtendedState(state, world, pos))
                    .withProperty( runeCharacterProperty, thisRune.character.getRegistryName() )
                    .withProperty( runeMaterialProperty, thisRune.material.getRegistryName() );
        }
        return state;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileRune();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new ExtendedBlockState(this, new IProperty[]{FACING}, new IUnlistedProperty[]{runeCharacterProperty,runeMaterialProperty});
    }
}
