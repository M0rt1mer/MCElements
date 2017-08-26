package mort.mortmagic.common.tileentity;

import com.sun.javafx.geom.Vec3f;
import mort.mortmagic.Content;
import mort.mortmagic.MortMagic;
import mort.mortmagic.common.potions.PotionActivator;
import mort.mortmagic.common.potions.PotionColoringHelper;
import mort.mortmagic.common.potions.PotionIngredientRegistry;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.potion.PotionUtils;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.FMLCommonHandler;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class TileCauldron extends TileEntity implements ITickable {

    //public List<ItemStack> items = new ArrayList<>();
    /**
     * a mirror of @link(items), saves on ingredient lookups.
     * Is not saved, but refreshed after load
     */
    //private Set<PotionIngredientRegistry.Entry> ingredientCache = new HashSet<>();

    private List<IngredientSolution> solvedItems = new ArrayList<>();

    public boolean throwItemIn( ItemStack stk ){
        PotionIngredientRegistry.Entry entry = MortMagic.potReg.findItemEntry( stk );
        if( entry==null ) //item is not an ingredient
            return false;
        for( IngredientSolution solution : solvedItems )
            if( solution.cachedEntry == entry ) // ingredient is already in cauldron. Must compare by entry, since the item can be damage-aware
                return false;
        if( world.isRemote )
            return true; //if world is remote, don't actually change anything
        solvedItems.add( new IngredientSolution(stk.splitStack(1),entry) );
        IBlockState state = world.getBlockState( pos );
        markDirty();
        world.notifyBlockUpdate( pos, state, state, 3 );
        return true;
    }

    public ItemStack bottleItem( ItemStack source ){
        source.shrink(1);
        Vec3f aspects = getAspects();
        solvedItems.clear();
        markDirty();
        ItemStack recipe = MortMagic.potionRecipeRegistry.findPotion( aspects.x, aspects.y, aspects.z );
        if(recipe!=null)
            return recipe.copy();
        else
            return PotionUtils.addPotionToItemStack( new ItemStack(Items.POTIONITEM), PotionTypes.WATER );
    }

    public Vec3f getAspects(){
        float rubedo = 0; int rubedoCnt = 0;
        float auredo = 0; int auredoCnt = 0;
        float caerudo = 0; int caerudoCnt = 0;

        for( IngredientSolution solution : solvedItems ){
            PotionIngredientRegistry.Entry entry = solution.cachedEntry;
            if( entry.rubedo != PotionIngredientRegistry.AspectPower.NONE ){
                rubedo += entry.rubedo.value + solution.activationLevel;
                rubedoCnt++;
            }
            if( entry.auredo != PotionIngredientRegistry.AspectPower.NONE ){
                auredo += entry.auredo.value + solution.activationLevel;
                auredoCnt++;
            }
            if( entry.caerudo != PotionIngredientRegistry.AspectPower.NONE ){
                caerudo += entry.caerudo.value + solution.activationLevel;
                caerudoCnt++;
            }
        }
        rubedo = rubedoCnt>0 ? (rubedo/rubedoCnt) : 0;
        auredo = auredoCnt>0 ? (auredo/auredoCnt) : 0;
        caerudo = caerudoCnt>0 ? (caerudo/caerudoCnt) : 0;
        return new Vec3f( rubedo, auredo, caerudo );
    }

    public int getColor(){
        return PotionColoringHelper.getColorFromAspects( getAspects() );
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        solvedItems.clear();
        for( NBTBase bas : compound.getTagList( "solutions", 10 ) ){
            IngredientSolution solution = IngredientSolution.load( (NBTTagCompound) bas );
            if(solution != null) //only add to list if it is ingredient (an item can stop beeing an ingredient between saves, if updated)
                solvedItems.add( solution );
        }
        world.markBlockRangeForRenderUpdate(pos,pos);
    }
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound = super.writeToNBT(compound);
        NBTTagList list = new NBTTagList();
        for( IngredientSolution solution : solvedItems ){
            list.appendTag( solution.save() );
        }
        compound.setTag("solutions",list);
        return compound;
    }
    @Override
    public NBTTagCompound getUpdateTag() {
        return writeToNBT(new NBTTagCompound() );
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity( pos, 0, getUpdateTag() );
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        if( world.isRemote )
            this.readFromNBT( pkt.getNbtCompound() );
    }

    @Override
    public void update() {

        for( PotionActivator act : Content.POTION_ACTIVATOR_REGISTRY ){
            float strength = act.getActivatorPassiveStrength(this );
            if(strength != 0){ //comparing assigned zero, not calculated zero
                for (IngredientSolution solution : solvedItems)
                    if(solution.cachedEntry.activator == act)
                        solution.applyActivator(strength);
            }
        }

        markDirty();
        if(!world.isRemote)
            world.markBlockRangeForRenderUpdate(pos,pos);

        if( world.isRemote | FMLCommonHandler.instance().getMinecraftServerInstance().getTickCounter() % 20 == 0 ){
            IBlockState state = world.getBlockState(pos);
            world.notifyBlockUpdate( pos, state, state, 3 );
        }


    }

    private static class IngredientSolution{
        public final ItemStack stack;
        public final PotionIngredientRegistry.Entry cachedEntry;
        public float activationLevel;

        /**
         * new item thrown into cauldron
         * @param stack
         * @param cachedEntry
         */
        public IngredientSolution(ItemStack stack, PotionIngredientRegistry.Entry cachedEntry ) {
            this.stack = stack;
            this.cachedEntry = cachedEntry;
            this.activationLevel = 0;
        }

        private IngredientSolution(ItemStack stack, PotionIngredientRegistry.Entry cachedEntry, float activationLevel) {
            this.stack = stack;
            this.cachedEntry = cachedEntry;
            this.activationLevel = activationLevel;
        }

        public static IngredientSolution load(NBTTagCompound compound ){
            ItemStack stack = new ItemStack( compound.getCompoundTag( "item" ) );
            PotionIngredientRegistry.Entry entry = MortMagic.potReg.findItemEntry( stack );
            if(stack != null) // can be null, if ingredient registry changes between saves
                return new IngredientSolution( stack, entry, compound.getFloat("activation") );
            else
                return null;
        }

        public NBTTagCompound save(){
            NBTTagCompound cmp = new NBTTagCompound();
            cmp.setTag( "item", stack.writeToNBT(new NBTTagCompound()) );
            cmp.setFloat("activation",activationLevel);
            return cmp;
        }


        public void applyActivator( float strength ){
            activationLevel = Math.max( -Content.POTION_MAXIMUM_ACTIVATION_LEVEL, Math.min( Content.POTION_MAXIMUM_ACTIVATION_LEVEL,activationLevel+strength*Content.POTION_ACTIVATION_PER_FRAME ) );
        }

    }

}
