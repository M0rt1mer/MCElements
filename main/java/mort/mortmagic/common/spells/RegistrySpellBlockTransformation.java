package mort.mortmagic.common.spells;

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMultimap;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;

/**
 * Stores all block transformation recipes. These block transformations are applied when any 'normal' spell is cast (can be overriden in spells, but basic spells will use this)
 */
public class RegistrySpellBlockTransformation {

    public ImmutableMultimap< Spell, IBlockTransformation> tranforms;
    public ImmutableMultimap< Spell, IBlockGrowth> growths;

    private ImmutableListMultimap.Builder< Spell, IBlockTransformation> tranformationBuilder = ImmutableListMultimap.builder();
    private ImmutableListMultimap.Builder< Spell, IBlockGrowth> growthBuilder = ImmutableListMultimap.builder();

    public void register( Spell spl, IBlockTransformation trn ){
        if( tranformationBuilder == null )
            throw new IllegalArgumentException( "Trying to modify spell block transformation registry after finalizing" );
        tranformationBuilder.put( spl, trn );
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
        tranforms = tranformationBuilder.build();
        tranformationBuilder = null;
        growths = growthBuilder.build();
        growths = null;
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
