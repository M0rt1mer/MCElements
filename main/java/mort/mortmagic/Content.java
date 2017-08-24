package mort.mortmagic;

import com.google.common.collect.ImmutableMap;
import mort.mortmagic.api.RobesRegistry;
import mort.mortmagic.api.RuneDictionary;
import mort.mortmagic.api.SacrificeRegistry;
import mort.mortmagic.common.block.*;
import mort.mortmagic.common.items.ItemCharge;
import mort.mortmagic.common.items.ItemDagger;
import mort.mortmagic.common.items.ItemMagicalResource;
import mort.mortmagic.common.items.ItemScroll;
import mort.mortmagic.api.PotionIngredientRegistry;
import mort.mortmagic.common.runes.RuneCharacter;
import mort.mortmagic.common.runes.RuneMaterial;
import mort.mortmagic.common.runes.RuneWord;
import mort.mortmagic.common.runes.words.WordBlockProtection;
import mort.mortmagic.common.runes.words.WordMobLoot;
import mort.mortmagic.common.spells.*;
import mort.mortmagic.common.tileentity.TileCauldron;
import mort.mortmagic.common.tileentity.TileRune;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.*;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import static mort.mortmagic.api.PotionIngredientRegistry.AspectPower.NONE;
import static mort.mortmagic.api.PotionIngredientRegistry.AspectPower.NORMAL;

@Mod.EventBusSubscriber
public class Content {

    public static CreativeTabs elementsTab = new CreativeTabs( 0, "myTab" ) {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack( Content.metaItem, 1 );
        }
    };

    //--------------------------------REGISTRIES
    public static IForgeRegistry<Element> ELEMENT_REGISTRY;
    public static IForgeRegistry<Spell> SPELL_REGISTRY;
    public static IForgeRegistry<RuneCharacter> RUNE_CHARACTER_REGISTRY;
    public static IForgeRegistry<RuneMaterial> RUNE_MATERIAL_REGISTRY;
    public static IForgeRegistry<RuneWord> RUNE_WORD_REGISTRY;

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
    @GameRegistry.ObjectHolder("mortmagic:stonedagger")
    public static Item stoneDagger;
    @GameRegistry.ObjectHolder("mortmagic:stonedaggersacred")
    public static Item stoneDaggerSacred;
    @GameRegistry.ObjectHolder("mortmagic:wand")
	public static Item testImplement;

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

//	public static Enchantment mag = new EnchantmentMagical(150, 1, EnumEnchantmentType.all).setName("magicalResource");
	
	public static ItemCharge charge = (ItemCharge) new ItemCharge().setUnlocalizedName("charge");
	
	//---- equipment
	public static Item commonRobe;
	
	//------------------------------potions
	
	//public static Item potion = new ItemMPotion().setUnlocalizedName("potion").setTextureName("potion");
	
	//public static Potion regenPotion;

	@SubscribeEvent
	public static void event_createRegistries(RegistryEvent.NewRegistry event){
	    //System.out.println( "RUNNING CREATE REGISTRIES" );
        ELEMENT_REGISTRY = new RegistryBuilder().setType(Element.class).setName(new ResourceLocation( MortMagic.MODID,"elements") ).create();
        SPELL_REGISTRY = new RegistryBuilder().setType(Spell.class).setName(new ResourceLocation( MortMagic.MODID,"spells") ).create();
        RUNE_CHARACTER_REGISTRY = new RegistryBuilder().setType(RuneCharacter.class).setName(new ResourceLocation( MortMagic.MODID,"rune_characters") ).create();
        RUNE_MATERIAL_REGISTRY = new RegistryBuilder().setType(RuneMaterial.class).setName(new ResourceLocation( MortMagic.MODID,"rune_materials") ).create();
        RUNE_WORD_REGISTRY = new RegistryBuilder().setType(RuneWord.class).setName( new ResourceLocation(MortMagic.MODID, "rune_words") ).create();
        MortMagic.robes = new RobesRegistry();
        MortMagic.sacrReg = new SacrificeRegistry();
        MortMagic.dictionary = new RuneDictionary();
        MortMagic.potReg = new PotionIngredientRegistry();
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

        registerItem( itemReg, new ItemDagger( ToolMaterial.STONE, false ),"stonedagger" );
        registerItem( itemReg, new ItemDagger( ToolMaterial.STONE, true ),"stonedaggersacred");

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

    private static void registerBlockItem( IForgeRegistry<Item> registry, Block block ){
        registry.register( new ItemBlock(block).setRegistryName( block.getRegistryName() ) );
    }

    private static void registerBlock(IForgeRegistry<Block> registry, Block block, String names ){
        registry.register(block.setRegistryName( new ResourceLocation(MortMagic.MODID,names) ).setUnlocalizedName(names).setCreativeTab( elementsTab) );
    }
    private static void registerItem(IForgeRegistry<Item> registry, Item item, String names ){
        registry.register(item.setRegistryName( new ResourceLocation(MortMagic.MODID,names) ).setUnlocalizedName(names).setCreativeTab(elementsTab));
    }
    private static void registerRuneCharacter( IForgeRegistry<RuneCharacter> registry, String name ){
        registry.register( new RuneCharacter( new ResourceLocation(MortMagic.MODID,name), name ) );
    }
    private static void registerRuneMaterial( IForgeRegistry<RuneMaterial> registry, String name ){
        registry.register( new RuneMaterial( new ResourceLocation(MortMagic.MODID,name), name ) );
    }

    @SubscribeEvent
    public static void event_registerSpells( RegistryEvent.Register<Spell> event){

        IForgeRegistry<Spell> splReg = event.getRegistry();

        splReg.register( new BaseFire( new ResourceLocation( MortMagic.MODID,"baseFire") ) );
        splReg.register( new BaseLife( new ResourceLocation( MortMagic.MODID,"baseLife") ) );
        splReg.register( new FireCold( new ResourceLocation( MortMagic.MODID,"fireCold") ) );

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
		
		//GameRegistry.addRecipe( new ShapedRecipes( 2, 3, new ItemStack[]{ null, cobble, cobble, cobble, cobble, stick } , new ItemStack(stoneDagger,1) ) );
		//GameRegistry.addRecipe( new ShapelessRecipes( new ItemStack(stoneDaggerSacred,1), Arrays.asList(new ItemStack[]{ new ItemStack(stoneDagger,1,0), new ItemStack(magicalEssence,1) }) ) );
		
		//GameRegistry.addRecipe( new ShapedRecipes(3,3,new ItemStack[]{stick,stick,stick,stick,stick,null,stick,stick,stick,stick},grateI) );
		//GameRegistry.addRecipe( new ShapedRecipes(3,3,new ItemStack[]{grateI,grateI,grateI,grateI,grateI,mag,grateI,grateI,grateI,grateI},new ItemStack(bonfire,8)) );
		
		/*GameRegistry.addRecipe( new ShapelessRecipes( new ItemStack(scroll,1), Arrays.asList(new ItemStack[]{new ItemStack(Items.paper,1),new ItemStack(magicalEssence,1)} ) ) );
		GameRegistry.addRecipe( new ShapelessRecipes( new ItemStack(fireSpell,1), Arrays.asList(new ItemStack[]{new ItemStack(scroll,1),new ItemStack(ashes,1)} ) ) );
		
		GameRegistry.addRecipe( new ShapelessRecipes( new ItemStack(lifeSpell,1), Arrays.asList(new ItemStack[]{new ItemStack(Items.paper,1),new ItemStack(metaItem,1,2)} ) ) );
		//GameRegistry.addRecipe( new ShapelessRecipes( new ItemStack(meatBall,1), Arrays.asList(new ItemStack[]{new ItemStack(abomasum,1),new ItemStack(rumen,1),new ItemStack(pigtail,1),new ItemStack(talon,1)} ) ) );
		//meatBall
		GameRegistry.addRecipe( new ShapelessRecipes( new ItemStack(metaItem,1,2), Arrays.asList(new ItemStack[]{new ItemStack(mobDrop,1,0),new ItemStack(mobDrop,1,1),new ItemStack(mobDrop,1,2),new ItemStack(mobDrop,1,3)} ) ) );
		GameRegistry.addRecipe( new ShapelessRecipes( new ItemStack(liveDirt,1), Arrays.asList(new ItemStack[]{  new ItemStack( Blocks.dirt,1), new ItemStack(metaItem,1,0), new ItemStack(metaItem,1,2) } ) ) );
		
		*/
		GameRegistry.addSmelting( new ItemStack(Content.metaItem,1,2), new ItemStack(Content.metaItem,1,1), 1);

		MortMagic.dictionary.register( new RuneCharacter[]{ ta, ra}, word_sacrifice );
        MortMagic.dictionary.register( new RuneCharacter[]{ ta, ot}, blockProt );

        //MortMagic.potReg.register( new PotionIngredientRegistry.Entry( Item.getItemFromBlock(Blocks.RED_FLOWER), 0.5f, 0,0 ) );
        MortMagic.potReg.register( new PotionIngredientRegistry.Entry( Item.getItemFromBlock(Blocks.DEADBUSH), NORMAL, NONE, NONE ) );
        MortMagic.potReg.register( new PotionIngredientRegistry.Entry( metaItem, magicalResources.magical_essence.ordinal() , NONE, NONE, NORMAL ) );
        MortMagic.potReg.register( new PotionIngredientRegistry.Entry( Item.getItemFromBlock(Blocks.YELLOW_FLOWER), NONE, NORMAL, NONE ) );

 		/*GameRegistry.addSmelting(mobDrop, new ItemStack(magicalEssence,1), 1);
		GameRegistry.addSmelting(ashes, new ItemStack(magicalEssence,1), 1);*/
		/*
		GameRegistry.addRecipe( new ShapelessRecipes( new ItemStack(metaItem,1,0), Arrays.asList(new ItemStack[]{ new ItemStack(Blocks.yellow_flower,1),new ItemStack(Blocks.red_flower,1),new ItemStack(Blocks.red_flower,1,1),new ItemStack(Blocks.red_flower,1,2),new ItemStack(Blocks.red_flower,1,3),new ItemStack(Blocks.red_flower,1,4),new ItemStack(Blocks.red_flower,1,5),new ItemStack(Blocks.red_flower,1,6),new ItemStack(Blocks.red_flower,1,7) } ) ) );
		*/
		//GameRegistry.addSmelting(pigtail, new ItemStack(magicalEssence,1), 1);
		//GameRegistry.addSmelting(rumen, new ItemStack(magicalEssence,1), 1);
		//GameRegistry.addSmelting(talon, new ItemStack(magicalEssence,1), 1);
		
	}

	@SubscribeEvent
	public static void event_lootTables(LootTableLoadEvent envt){

	    if( extraLootTables.containsKey(envt.getName()) )
            envt.getTable().addPool( envt.getLootTableManager().getLootTableFromLocation( extraLootTables.get(envt.getName()) ).getPool("inject") );

    }

    private static LootPool getInjectPool(ResourceLocation entryName) {
        return new LootPool(new LootEntry[] { getInjectEntry(entryName, 1) }, new LootCondition[0], new RandomValueRange(1), new RandomValueRange(0, 1), "mortmagic_inject_entry");
    }

    private static LootEntryTable getInjectEntry(ResourceLocation name, int weight) {
        return new LootEntryTable( name, weight, 0, new LootCondition[0], "mortmagic_inject_entry");
    }

	/*public static void registerPotions(){
		
		for (Field f : Potion.class.getDeclaredFields()) {
	        f.setAccessible(true);
	        try {
	            if (f.getName().equals("potionTypes") || f.getName().equals("field_76425_a")) {
	                Field modfield = Field.class.getDeclaredField("modifiers");
	                modfield.setAccessible(true);
	                modfield.setInt(f, f.getModifiers() & ~Modifier.FINAL);

	                Potion[] potionTypes = (Potion[])f.get(null);
	                final Potion[] newPotionTypes = new Potion[256];
	                System.arraycopy(potionTypes, 0, newPotionTypes, 0, potionTypes.length);
	                f.set(null, newPotionTypes);
	            }
	        } catch (Exception e) {
	            System.err.println("Severe error changing potions");
	            System.err.println(e);
	        }
	    }
		
		regenPotion = new PotionManaRegen(120, false, 38151).setPotionName("potion.manaRegen");
	}*/
	
}
