package mort.mortmagic.world.block;

import com.google.common.collect.Maps;
import mort.mortmagic.MortMagic;
import mort.mortmagic.runes.RuneCharacter;
import mort.mortmagic.runes.RuneMaterial;
import mort.mortmagic.world.block.states.UnlistedPropertyRegistryEntry;
import mort.mortmagic.world.tileentity.TileRune;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.tileentity.TileEntity;
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

    public BlockRune() {
        super( Material.WOOD );
    }

    @SideOnly(Side.CLIENT)
    public void initModels(){

                ModelLoader.setCustomStateMapper(this, new StateMapperBase() {
                    @Override
                    protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
                        IExtendedBlockState ext = (IExtendedBlockState) state;
                        return new ModelResourceLocation(
                                MortMagic.MODID + ":rune_"+ ext.getValue(runeCharacterProperty).getResourcePath() + "_"+ ext.getValue(runeMaterialProperty).getResourcePath() );
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
        return new ExtendedBlockState(this, new IProperty[0], new IUnlistedProperty[]{runeCharacterProperty,runeMaterialProperty});
    }
}
