package mort.mortmagic.client;

import mort.mortmagic.Content;
import mort.mortmagic.MortMagic;
import mort.mortmagic.client.item.ItemColorWrapper;
import mort.mortmagic.client.rendering.CauldronRenderer;
import mort.mortmagic.client.rendering.McElementsModelLoader;
import mort.mortmagic.common.CommonProxy;
import mort.mortmagic.client.block.BlockColorWrapper;
import mort.mortmagic.common.SpellCaster;
import mort.mortmagic.common.entity.EntitySpellMissile;
import mort.mortmagic.common.inventory.SpellbookContainer;
import mort.mortmagic.common.net.MessageSyncStats;
import mort.mortmagic.common.potions.PotionIngredientRegistry;
import mort.mortmagic.common.spells.Spell;
import mort.mortmagic.common.tileentity.TileCauldron;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCauldron;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,	int x, int y, int z) {
		return new GuiSpellbook(new SpellbookContainer(player));
	}

	public KeyBindingManager keyBind;
	
	public void preInit(){
		super.preInit();
		keyBind = new KeyBindingManager();
		keyBind.initAndRegister();
		ModelLoaderRegistry.registerLoader( new McElementsModelLoader());
        RenderingRegistry.registerEntityRenderingHandler( EntitySpellMissile.class, MissileRenderer::new );
	}

	@Override
	public void init() {
		super.init();
		MinecraftForge.EVENT_BUS.register( new GuiManaOverlay() );
		Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler( new BlockColorWrapper(Content.cauldron), Content.cauldron );
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler( new ItemColorWrapper(Content.potion_recipe), Content.potion_recipe );
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler( new ItemColorWrapper(Content.potion_recipe_advanced), Content.potion_recipe_advanced );
	}

	@SubscribeEvent
	public static void event_registerModels(ModelRegistryEvent event) {

        for( Item itm : Item.REGISTRY )
		    if(itm.getRegistryName().getResourceDomain().equals(MortMagic.MODID) ) {
                if (itm instanceof IInitializeMyOwnModels)
                    ((IInitializeMyOwnModels) itm).initModels();
                else if (!(itm instanceof ItemBlock)) //don't register ItemBlocks - some blocks want to custom initialize models
                    ModelLoader.setCustomModelResourceLocation(itm, 0, new ModelResourceLocation(itm.getRegistryName(), null));
            }

        for( Block blk : Block.REGISTRY )
            if( blk.getRegistryName().getResourceDomain().equals(MortMagic.MODID) ) {
                if (blk instanceof IInitializeMyOwnModels)
                    ((IInitializeMyOwnModels) blk).initModels();
                else
                    ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(blk), 0, new ModelResourceLocation(blk.getRegistryName(), "inventory"));
            }

		ClientRegistry.bindTileEntitySpecialRenderer(TileCauldron.class, new CauldronRenderer());
	}

	@SubscribeEvent
	public static void event_tooltip(ItemTooltipEvent event){

		PotionIngredientRegistry.Entry entry = MortMagic.potReg.findItemEntry( event.getItemStack() );
		if( entry != null ){
			StringBuilder bld = new StringBuilder();
			if( entry.rubedo != PotionIngredientRegistry.AspectPower.NONE )
				bld.append( TextFormatting.RED + I18n.format(MortMagic.MODID+ ".tooltip.aspectpower."+entry.rubedo.toString().toLowerCase(), I18n.format( MortMagic.MODID + ".tooltip.aspect.rubedo" ) )  );
			if( entry.auredo != PotionIngredientRegistry.AspectPower.NONE )
                bld.append( TextFormatting.YELLOW + I18n.format(MortMagic.MODID+ ".tooltip.aspectpower."+entry.auredo.toString().toLowerCase(), I18n.format( MortMagic.MODID + ".tooltip.aspect.auredo" ) )  );
			if( entry.caerudo != PotionIngredientRegistry.AspectPower.NONE )
                bld.append( TextFormatting.BLUE + I18n.format(MortMagic.MODID+ ".tooltip.aspectpower."+entry.caerudo.toString().toLowerCase(), I18n.format( MortMagic.MODID + ".tooltip.aspect.caerudo" ) )  );
			event.getToolTip().add( bld.toString() );
		}

	}

	public void postInit(){
		super.postInit();
	}

	public void spawnParticle( World wld, double p1, double p2, double p3, double p4, double p5, double p6, Spell spell){
        Minecraft.getMinecraft().effectRenderer.addEffect( new RuneParticle( wld, p1, p2, p3 ) );
    }

	@Override
	public void spawnDebugParticle(World wld, BlockPos pos, EnumDebugParticle type ){
		if( wld == Minecraft.getMinecraft().player.getEntityWorld() ){
			Particle p = new RuneParticle( wld, pos.getX() + 0.3f + wld.rand.nextDouble() * 0.4f,
                    pos.getY() + 0.3f + wld.rand.nextDouble() * 0.4f, pos.getZ() + 0.3f + wld.rand.nextDouble() * 0.4f );
			switch ( type ){
				case CIRCLE_CANDIDATE: p.setRBGColorF( 1,1,1 );
				case CIRCLE_COMPLETED: p.setRBGColorF( 0,1,0 );
			}
			Minecraft.getMinecraft().effectRenderer.addEffect( p );
		}
	}

    @Override
    public void handle_syncStatsMessage( MessageSyncStats message ) {
        Minecraft.getMinecraft().addScheduledTask( () -> {
            SpellCaster.getPlayerSpellcasting(Minecraft.getMinecraft().player).setMana(message.newMana);
        } );
    }
}
