package mort.mortmagic.world.block;

import mort.mortmagic.MortMagic;
import mort.mortmagic.runes.RuneCharacter;
import mort.mortmagic.runes.RuneMaterial;
import mort.mortmagic.world.block.states.UnlistedPropertyRegistryEntry;
import mort.mortmagic.world.tileentity.TileRune;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class BlockRune extends Block implements ITileEntityProvider{

    public static final UnlistedPropertyRegistryEntry runeCharacterProperty = new UnlistedPropertyRegistryEntry( new ResourceLocation( MortMagic.MODID, "rune_characters" ) );
    public static final UnlistedPropertyRegistryEntry runeMaterialProperty = new UnlistedPropertyRegistryEntry( new ResourceLocation( MortMagic.MODID, "rune_materials" ) );

    public BlockRune() {
        super( Material.WOOD );
    }

    @SideOnly(Side.CLIENT)
    public void initModels(){

        for(RuneMaterial mat : GameRegistry.findRegistry(RuneMaterial.class)){
            for(RuneCharacter chr : GameRegistry.findRegistry(RuneCharacter.class)){
                /*ModelLoader.setCustomStateMapper( IBlockState state -> {
                    state.getUnlistedProperties();
                    return new ModelResourceLocation(
                        MortMagic.MODID + ":rune_"+ + "_"+mat.getRegistryName().getResourcePath())
                } );*/
            }
        }

    }

    @Override
    public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos) {
        TileRune thisRune = (TileRune) world.getTileEntity(pos);
        if( state instanceof IExtendedBlockState) {
            return ((IExtendedBlockState)super.getExtendedState(state, world, pos))
                    .withProperty( runeCharacterProperty, thisRune.character.getRegistryName().toString())
                    .withProperty( runeMaterialProperty, thisRune.material.getRegistryName().toString());
        }
        return state;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileRune();
    }
}
