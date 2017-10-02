package mort.mortmagic.common.spells;

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMultimap;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;

/**
 * Stores all block transformation recipes. These block transformations are applied when any 'normal' spell is cast (can be overriden in spells, but basic spells will use this)
 */
public class RegistrySpellBlockTransformation {

    public ImmutableMultimap< Spell, IBlockTransformation> transforms;
    public ImmutableMultimap< Spell, IBlockGrowth> growths;

    private ImmutableListMultimap.Builder< Spell, IBlockTransformation> transformationBuilder = ImmutableListMultimap.builder();
    private ImmutableListMultimap.Builder< Spell, IBlockGrowth> growthBuilder = ImmutableListMultimap.builder();

    public void register( Spell spl, IBlockTransformation trn ){
        if( transformationBuilder == null )
            throw new IllegalArgumentException( "Trying to modify spell block transformation registry after finalizing" );
        transformationBuilder.put( spl, trn );
    }

    public void register( Spell spl, IBlockGrowth growth ){
        if( growthBuilder == null )
            throw new IllegalArgumentException( "Trying to modify spell block growth registry after finalizing" );
        growthBuilder.put( spl, growth );
    }

    /**
     * finalizes registries, locks further modifications
     */
    public void finalize(){
        transforms = transformationBuilder.build();
        transformationBuilder = null;
        growths = growthBuilder.build();
        growthBuilder = null;
    }

    @FunctionalInterface
    public interface IBlockTransformation {
        IBlockState transform(IBlockState state);
    }

    @FunctionalInterface
    public interface IBlockGrowth {
        IBlockState growth( IBlockState state, EnumFacing appliedFacing );
    }

}
