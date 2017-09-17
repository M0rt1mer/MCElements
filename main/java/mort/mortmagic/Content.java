package mort.mortmagic;

import com.google.common.collect.ImmutableMap;
import mort.mortmagic.common.ConditionBleeding;
import mort.mortmagic.common.block.*;
import mort.mortmagic.common.items.*;
import mort.mortmagic.common.potions.*;
import mort.mortmagic.common.runes.RuneCharacter;
import mort.mortmagic.common.runes.RuneDictionary;
import mort.mortmagic.common.runes.RuneMaterial;
import mort.mortmagic.common.runes.RuneWord;
import mort.mortmagic.common.runes.words.WordBlockProtection;
import mort.mortmagic.common.runes.words.WordMobLoot;
import mort.mortmagic.common.spells.*;
import mort.mortmagic.common.tileentity.TileCauldron;
import mort.mortmagic.common.tileentity.TileRune;
import mort.mortmagic.obsolete.RobesRegistry;
import mort.mortmagic.obsolete.SacrificeRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.*;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryBuilder;

import static mort.mortmagic.common.potions.PotionIngredientRegistry.AspectPower.NONE;
import static mort.mortmagic.common.potions.PotionIngredientRegistry.AspectPower.NORMAL;

@Mod.EventBusSubscriber
public class Content {

    public static CreativeTabs elementsTab = new CreativeTabs( 0, "myTab" ) {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack( Content.metaItem, 1 );
        }
    };

    //-------------------------------- GAMEPLAY CONSTANTS

    /**
     * Tolerance between potion recipe and cauldron content, which will still result in creating given potion
     */
    public static final float POTION_RECIPE_TOLERANCE = 0.05f;
    public static final float POTION_MAXIMUM_ACTIVATION_LEVEL = 0.1f;
    public static final float POTION_ACTIVATION_PER_FRAME = POTION_MAXIMUM_ACTIVATION_LEVEL/(20*10); //takes 5 seconds to fully activate/deacitvate
    public static final float POTION_DEACTIVATION_PER_FRAME = POTION_ACTIVATION_PER_FRAME * 0.5f;

    //--------------------------------- Enums

    public static DamageSource DAMAGE_BLEED = new DamageSource("bleeding");

    //--------------------------------REGISTRIES
    public static IForgeRegistry<Element> ELEMENT_REGISTRY;
    public static IForgeRegistry<Spell> SPELL_REGISTRY;
    public static IForgeRegistry<RuneCharacter> RUNE_CHARACTER_REGISTRY;
    public static IForgeRegistry<RuneMaterial> RUNE_MATERIAL_REGISTRY;
    public static IForgeRegistry<RuneWord> RUNE_WORD_REGISTRY;
    public static IForgeRegistry<PotionActivator> POTION_ACTIVATOR_REGISTRY;

	//--------------------------------SPELLS
    @GameRegistry.ObjectHolder("mortmagic:fire")
    public static Element fire;
    @GameRegistry.ObjectHolder("mortmagic:force")
	public static Element force;
    @GameRegistry.ObjectHolder("mortmagic:life")
	public static Element life;

	@GameRegistry.ObjectHolder("mortmagic:baseFire")
	public static Spell baseFire;
    @GameRegistry.ObjectHolder("mortmagic:baseLife")
	public static Spell baseLife;
    @GameRegistry.ObjectHolder("mortmagic:fireCold")
	public static Spell fireCold;
	
	//--------------------------------BLOCK
	//public static Block fern = new BlockFern("fern").setBlockTextureName("mortmagic:fern");
    @GameRegistry.ObjectHolder("mortmagic:bonfire")
	public static Block bonfire;
    @GameRegistry.ObjectHolder("mortmagic:livedirt")
	public static Block liveDirt;
    @GameRegistry.ObjectHolder("mortmagic:wildfire")
	public static Block wildfire;
    @GameRegistry.ObjectHolder("mortmagic:ashesblock")
	public static Block ashesBlock;
    @GameRegistry.ObjectHolder("mortmagic:treecore")
	public static Block treeCore;
    @GameRegistry.ObjectHolder("mortmagic:treeofliferoot")
	public static Block treeRoot;
    @GameRegistry.ObjectHolder("mortmagic:runeblock")
    public static BlockRune runeBlock;

    @GameRegistry.ObjectHolder("mortmagic:cauldron")
    public static BlockCauldron cauldron;

	//--------------------------------ITEMS
	
	//magical resources
    @GameRegistry.ObjectHolder("mortmagic:mi")
	public static ItemMagicalResource metaItem;
    @GameRegistry.ObjectHolder("mortmagic:spellscroll")
    public static ItemScroll spellScroll;
    @GameRegistry.ObjectHolder("mortmagic:charge")
    public static ItemCharge charge;
    @GameRegistry.ObjectHolder("mortmagic:dagger_stone")
    public static Item dagger_stone;
    @GameRegistry.ObjectHolder("mortmagic:dagger_iron")
    public static Item dagger_iron;
    @GameRegistry.ObjectHolder("mortmagic:dagger_gold")
    public static Item dagger_gold;
    @GameRegistry.ObjectHolder("mortmagic:dagger_diamond")
    public static Item dagger_diamond;
    @GameRegistry.ObjectHolder("mortmagic:dagger_obsidian")
    public static Item dagger_obsidian;

    @GameRegistry.ObjectHolder("mortmagic:wand")
	public static Item testImplement;
    @GameRegistry.ObjectHolder("mortmagic:potion_recipe")
    public static ItemPotionRecipe potion_recipe;
    @GameRegistry.ObjectHolder("mortmagic:potion_recipe_advanced")
    public static ItemPotionRecipe potion_recipe_advanced;

    //--------------------------------- Rune words

    @GameRegistry.ObjectHolder("mortmagic:ta")
    public static RuneCharacter ta;
    @GameRegistry.ObjectHolder("mortmagic:ra")
    public static RuneCharacter ra;
    @GameRegistry.ObjectHolder("mortmagic:ot")
    public static RuneCharacter ot;

    @GameRegistry.ObjectHolder("mortmagic:test")
    public static RuneWord test;
    @GameRegistry.ObjectHolder("mortmagic:blockprot")
    public static RuneWord blockProt;
    @GameRegistry.ObjectHolder("mortmagic:sacrifice")
    public static RuneWord word_sacrifice;

    //--------------------------------- Potions
    @GameRegistry.ObjectHolder("mortmagic:mana")
    public static Potion potion_mana;
    @GameRegistry.ObjectHolder("mortmagic:bleed")
    public static Potion potion_bleed;
    //--------------------------------- PotionTypes

    @GameRegistry.ObjectHolder("mortmagic:mana")
    public static PotionType potionType_mana;

    @GameRegistry.ObjectHolder("mortmagic:heat")
    public static PotionActivator heat;

	//---- equipment
	public static Item commonRobe;


	@SubscribeEvent
	public static void event_createRegistries(RegistryEvent.NewRegistry event){
        ELEMENT_REGISTRY = new RegistryBuilder().setType(Element.class).setName(new ResourceLocation( MortMagic.MODID,"elements") ).create();
        SPELL_REGISTRY = new RegistryBuilder().setType(Spell.class).setName(new ResourceLocation( MortMagic.MODID,"spells") ).create();
        RUNE_CHARACTER_REGISTRY = new RegistryBuilder().setType(RuneCharacter.class).setName(new ResourceLocation( MortMagic.MODID,"rune_characters") ).create();
        RUNE_MATERIAL_REGISTRY = new RegistryBuilder().setType(RuneMaterial.class).setName(new ResourceLocation( MortMagic.MODID,"rune_materials") ).create();
        RUNE_WORD_REGISTRY = new RegistryBuilder().setType(RuneWord.class).setName( new ResourceLocation(MortMagic.MODID, "rune_words") ).create();
        POTION_ACTIVATOR_REGISTRY = new RegistryBuilder().setType(PotionActivator.class).setName( new ResourceLocation(MortMagic.MODID, "potion_activator") ).create();
        MortMagic.robes = new RobesRegistry();
        MortMagic.sacrReg = new SacrificeRegistry();
        MortMagic.dictionary = new RuneDictionary();
        MortMagic.potReg = new PotionIngredientRegistry();
        MortMagic.potionRecipeRegistry = new PotionRecipeRegistry();
        MortMagic.spellMapping = new ElementAndItemToSpellMapping();
        MortMagic.spellBlockTransformation = new RegistrySpellBlockTransformation();
    }

    @SubscribeEvent
    public static void event_registerBlocks( RegistryEvent.Register<Block> event){
        IForgeRegistry<Block> blockReg = event.getRegistry();
        registerBlock( blockReg, new BlockBonfire(Material.WOOD).setHardness(1).setTickRandomly(true), "bonfire" );
        registerBlock( blockReg, new Block(Material.GROUND).setHardness(1),"livedirt");
        registerBlock( blockReg,new BlockWildFire( ).setLightLevel(1.0F),"wildfire" );
        registerBlock( blockReg, new Block( Material.SAND ),"ashesblock");
        registerBlock( blockReg, new Block(Material.WOOD),"treecore");
        registerBlock( blockReg, new Block(Material.WOOD),"treeofliferoot");
        registerBlock( blockReg, new BlockRune(), "runeblock" );
        registerBlock( blockReg, new BlockCauldron(), "cauldron" );

        //public static Block runeLifeRa = new BlockRune(Material.wood, RuneCharacter.ra).setBlockName("rune_life_ra").setBlockTextureName("mortmagic:rune_twig_ra").setCreativeTab(CreativeTabs.tabBlock);
    }

    public static enum magicalResources {
        scroll,magical_essence,fern,flowerring, grate, meatball, twig,
        rumen, pigtail,talon,horn,succups,larynx,horsehair,ashes
    }

    @SubscribeEvent
    public static void event_registerItems( RegistryEvent.Register<Item> event ){
        IForgeRegistry<Item> itemReg = event.getRegistry();
        for (Block blk : new Block[]{ bonfire, liveDirt, wildfire, ashesBlock, treeRoot, treeCore, runeBlock, cauldron } )
            registerBlockItem(itemReg, blk);

        registerItem( itemReg, new ItemScroll(),  "spellscroll" ) ;
        registerItem( itemReg, new ItemDagger( ToolMaterial.STONE ),"dagger_stone" );
        registerItem( itemReg, new ItemDagger( ToolMaterial.IRON ),"dagger_iron");
        registerItem( itemReg, new ItemDagger( ToolMaterial.GOLD ),"dagger_gold");
        registerItem( itemReg, new ItemDagger( ToolMaterial.DIAMOND ),"dagger_diamond");
        registerItem( itemReg, new ItemDagger( EnumHelper.addToolMaterial( "obsidian", 2, 200, 5.0F, 2.0F, 16 ) ),"dagger_obsidian");
        registerItem( itemReg, new ItemPotionRecipe(false), "potion_recipe" );
        registerItem( itemReg, new ItemPotionRecipe(true), "potion_recipe_advanced" );
        registerItem( itemReg, new ItemCharge(), "charge" );

        String[] metaItems = new String[]{"scroll","magicalEssence","fern","flowerring", "grate", "meatball", "twig",
                "rumen", "pigtail","talon","horn","succups","larynx","horsehair","ashes" };
        registerItem( itemReg, new ItemMagicalResource( magicalResources.class ),"mi") ;
        registerItem( itemReg, new Item(), "wand" );
    }

    @SubscribeEvent
    public  static void event_registerRuneCharacters( RegistryEvent.Register<RuneCharacter> event ){
        IForgeRegistry<RuneCharacter> reg = event.getRegistry();
        registerRuneCharacter(reg,"ra");
        registerRuneCharacter(reg,"ot");
        registerRuneCharacter(reg,"ta");
    }

    @SubscribeEvent
    public  static void event_registerRuneMaterial( RegistryEvent.Register<RuneMaterial> event ){
        IForgeRegistry<RuneMaterial> reg = event.getRegistry();
        registerRuneMaterial(reg,"twig");
    }

    @SubscribeEvent
    public static void event_registerRuneWords( RegistryEvent.Register<RuneWord> event ){
        IForgeRegistry<RuneWord> reg = event.getRegistry();
        reg.register( new RuneWord( new ResourceLocation(MortMagic.MODID, "test") ) );
        reg.register( new WordBlockProtection(new ResourceLocation(MortMagic.MODID, "blockprot")));
        reg.register( new WordMobLoot(new ResourceLocation(MortMagic.MODID, "sacrifice")));
    }


    public static void registerPotions( IForgeRegistry<Potion> registry  ){
        potion_mana = registerPotion( registry, new PotionManaRegen( false, 153154),"mana" );
        potion_bleed = registerPotion( registry, new PotionBleed( false, 153154),"bleed" );
    }

    public static void registerPotionTypes( IForgeRegistry<PotionType> registry ){
        potionType_mana = registerPotionType( registry, new PotionType( new PotionEffect( potion_mana , 1) ), "mana");
    }

    @SubscribeEvent
    public static void event_registerPotionActivators( RegistryEvent.Register<PotionActivator> event ){
        IForgeRegistry<PotionActivator> registry = event.getRegistry();
        registry.register( new ActivatorHeat(new ResourceLocation(MortMagic.MODID, "heat")));
    }

    private static void registerBlockItem( IForgeRegistry<Item> registry, Block block ){
        registry.register( new ItemBlock(block).setRegistryName( block.getRegistryName() ) );
    }

    private static void registerBlock(IForgeRegistry<Block> registry, Block block, String names ){
        registry.register(block.setRegistryName( new ResourceLocation(MortMagic.MODID,names) ).setUnlocalizedName( block.getRegistryName().toString() ).setCreativeTab( elementsTab) );
    }
    private static void registerItem(IForgeRegistry<Item> registry, Item item, String names ){
        registry.register(item.setRegistryName( new ResourceLocation(MortMagic.MODID,names) ).setUnlocalizedName( item.getRegistryName().toString() ).setCreativeTab(elementsTab));
    }
    private static void registerRuneCharacter( IForgeRegistry<RuneCharacter> registry, String name ){
        registry.register( new RuneCharacter( new ResourceLocation(MortMagic.MODID,name), name ) );
    }
    private static void registerRuneMaterial( IForgeRegistry<RuneMaterial> registry, String name ){
        registry.register( new RuneMaterial( new ResourceLocation(MortMagic.MODID,name), name ) );
    }
    private static PotionType registerPotionType(IForgeRegistry<PotionType> registry, PotionType potion, String name){
        registry.register( potion.setRegistryName( new ResourceLocation( MortMagic.MODID, name ) ) );
        return potion;
    }
    private static Potion registerPotion(IForgeRegistry<Potion> registry, Potion potion, String name){
        registry.register( potion.setRegistryName( new ResourceLocation( MortMagic.MODID, name ) ) );
        return potion;
    }

    @SubscribeEvent
    public static void event_registerSpells( RegistryEvent.Register<Spell> event){

        IForgeRegistry<Spell> splReg = event.getRegistry();

        splReg.register( new BaseFire( new ResourceLocation( MortMagic.MODID,"baseFire") ) );
        splReg.register( new BaseLife( new ResourceLocation( MortMagic.MODID,"baseLife") ) );
        //splReg.register( new FireCold( new ResourceLocation( MortMagic.MODID,"fireCold") ) );

        /*MortMagic.life.registry.add(new MagicalBonemeal());
        MortMagic.life.registry.add(new TreeOfLife());*/

    }

    @SubscribeEvent
    public static void event_registerElements( RegistryEvent.Register<Element> event){

        IForgeRegistry<Element> elemReg = event.getRegistry();

        elemReg.register( new Element( new ResourceLocation(MortMagic.MODID,"fire") ) );
        //elemReg.register( new Element(new ResourceLocation(MortMagic.MODID,"force") ) );
        elemReg.register( new Element(new ResourceLocation(MortMagic.MODID,"life") ) );

    }

	//------------------------------init

    public static void preInit(){
        registerPotions(GameRegistry.findRegistry( Potion.class ));
        registerPotionTypes( GameRegistry.findRegistry( PotionType.class ) );
    }

    private static ImmutableMap<ResourceLocation,ResourceLocation> extraLootTables = ImmutableMap.of(
            new ResourceLocation("minecraft:entities/cow"), new ResourceLocation(MortMagic.MODID,"cow")
    );

    public static void init(){
        for( ResourceLocation res : extraLootTables.values() )
            LootTableList.register( res );
    }

	public static void registerTileEntities(){

		GameRegistry.registerTileEntity(TileEntityWildfire.class, "TEwildfire");
		GameRegistry.registerTileEntity(TileEntityTreeOfLifeCore.class, "TETOLCore");
        TileEntity.register( "mortmagic:rune", TileRune.class);
        TileEntity.register( MortMagic.MODID + ":cauldron", TileCauldron.class );
		
	}
	
	public static void registerRecipes(){
		
		GameRegistry.addSmelting( new ItemStack(Content.metaItem,1,2), new ItemStack(Content.metaItem,1,1), 1);

		MortMagic.dictionary.register( new RuneCharacter[]{ ta, ra}, word_sacrifice );
        MortMagic.dictionary.register( new RuneCharacter[]{ ta, ot}, blockProt );

        //MortMagic.potReg.register( new PotionIngredientRegistry.Entry( Item.getItemFromBlock(Blocks.RED_FLOWER), 0.5f, 0,0 ) );
        MortMagic.potReg.register( new PotionIngredientRegistry.Entry( Item.getItemFromBlock(Blocks.DEADBUSH), NORMAL, NONE, NONE, null ) );
        MortMagic.potReg.register( new PotionIngredientRegistry.Entry( metaItem, magicalResources.magical_essence.ordinal() , NONE, NONE, NORMAL, heat ) );
        MortMagic.potReg.register( new PotionIngredientRegistry.Entry( Item.getItemFromBlock(Blocks.YELLOW_FLOWER), NONE, NORMAL, NONE, null ) );

        MortMagic.potionRecipeRegistry.register( new PotionRecipeRegistry.PotionRecipe( potionType_mana.getRegistryName(), NORMAL.value, NONE.value, NORMAL.value + POTION_MAXIMUM_ACTIVATION_LEVEL, PotionUtils.addPotionToItemStack( new ItemStack(Items.POTIONITEM), potionType_mana )) );

        MortMagic.spellMapping.registerDefaultSpell( fire, baseFire );
        MortMagic.spellMapping.registerDefaultSpell( life, baseLife );

        MortMagic.spellBlockTransformation.register( baseLife, (IBlockState state) -> {if(state.getBlock() == Blocks.DIRT) return Blocks.GRASS.getDefaultState(); return null;} );
        //MortMagic.spellMapping.registerDefaultSpell( force, base );

 		/*GameRegistry.addSmelting(mobDrop, new ItemStack(magicalEssence,1), 1);
		GameRegistry.addSmelting(ashes, new ItemStack(magicalEssence,1), 1);*/
		/*
		GameRegistry.addRecipe( new ShapelessRecipes( new ItemStack(metaItem,1,0), Arrays.asList(new ItemStack[]{ new ItemStack(Blocks.yellow_flower,1),new ItemStack(Blocks.red_flower,1),new ItemStack(Blocks.red_flower,1,1),new ItemStack(Blocks.red_flower,1,2),new ItemStack(Blocks.red_flower,1,3),new ItemStack(Blocks.red_flower,1,4),new ItemStack(Blocks.red_flower,1,5),new ItemStack(Blocks.red_flower,1,6),new ItemStack(Blocks.red_flower,1,7) } ) ) );
		*/
		//GameRegistry.addSmelting(pigtail, new ItemStack(magicalEssence,1), 1);
		//GameRegistry.addSmelting(rumen, new ItemStack(magicalEssence,1), 1);
		//GameRegistry.addSmelting(talon, new ItemStack(magicalEssence,1), 1);
		
	}

	public static final ConditionBleeding condition_isBleeding = new ConditionBleeding( new ResourceLocation(MortMagic.MODID, "bleeding") );

	@SubscribeEvent
	public static void event_lootTables(LootTableLoadEvent envt){

	    if( extraLootTables.containsKey(envt.getName()) ) {
	        LootTable injectTable = envt.getLootTableManager().getLootTableFromLocation(extraLootTables.get(envt.getName()));
            LootPool injectPool = null;
            for( int i = 0; (injectPool=injectTable.getPool("inject_"+i)) != null; i++ )    //as long as there are further inject pools
                envt.getTable().addPool( injectPool );
        }

    }

    private static LootPool getInjectPool(ResourceLocation entryName) {
        return new LootPool(new LootEntry[] { getInjectEntry(entryName, 1) }, new LootCondition[0], new RandomValueRange(1), new RandomValueRange(0, 1), "mortmagic_inject_entry");
    }

    private static LootEntryTable getInjectEntry(ResourceLocation name, int weight) {
        return new LootEntryTable( name, weight, 0, new LootCondition[0], "mortmagic_inject_entry");
    }
	
}
