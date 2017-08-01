package mort.mortmagic;

import mort.mortmagic.runes.RuneCharacter;
import mort.mortmagic.runes.RuneMaterial;
import mort.mortmagic.spells.*;
import mort.mortmagic.world.block.*;
import mort.mortmagic.world.items.ItemCharge;
import mort.mortmagic.world.items.ItemDagger;
import mort.mortmagic.world.items.ItemMagicalResource;
import mort.mortmagic.world.items.ItemScroll;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSand;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryBuilder;

@Mod.EventBusSubscriber
public class Resource {

    public static CreativeTabs elementsTab = new CreativeTabs( 0, "myTab" ) {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack( Resource.metaItem, 1 );
        }
    };

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
    @GameRegistry.ObjectHolder("mortmagic:rune")
    public static Block runeBlock;
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
	

//	public static Enchantment mag = new EnchantmentMagical(150, 1, EnumEnchantmentType.all).setName("magicalResource");
	
	public static ItemCharge charge = (ItemCharge) new ItemCharge().setUnlocalizedName("charge");
	
	//---- equipment
	public static Item commonRobe;
	
	//------------------------------potions
	
	//public static Item potion = new ItemMPotion().setUnlocalizedName("potion").setTextureName("potion");
	
	//public static Potion regenPotion;

	@SubscribeEvent
	public static void event_createRegistries(RegistryEvent.NewRegistry event){
	    System.out.println( "RUNNING CREATE REGISTRIES" );
        new RegistryBuilder().setType(Element.class).setName(new ResourceLocation( MortMagic.MODID,"elements") ).create();
	    new RegistryBuilder().setType(Spell.class).setName(new ResourceLocation( MortMagic.MODID,"spells") ).create();
        new RegistryBuilder().setType(RuneCharacter.class).setName(new ResourceLocation( MortMagic.MODID,"rune_characters") ).create();
        new RegistryBuilder().setType(RuneMaterial.class).setName(new ResourceLocation( MortMagic.MODID,"rune_materials") ).create();
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
        //public static Block runeLifeRa = new BlockRune(Material.wood, RuneCharacter.ra).setBlockName("rune_life_ra").setBlockTextureName("mortmagic:rune_twig_ra").setCreativeTab(CreativeTabs.tabBlock);
    }

    @SubscribeEvent
    public static void event_registerItems( RegistryEvent.Register<Item> event ){
        IForgeRegistry<Item> itemReg = event.getRegistry();
        for (Block blk : new Block[]{ bonfire, liveDirt, wildfire, ashesBlock, treeRoot, treeCore  } )
            registerBlockItem(itemReg, blk);

        registerItem( itemReg, new ItemScroll(),  "spellscroll" ) ;

        registerItem( itemReg, new ItemDagger( ToolMaterial.STONE, false ),"stonedagger" );
        registerItem( itemReg, new ItemDagger( ToolMaterial.STONE, true ),"stonedaggersacred");

        String[] metaItems = new String[]{"scroll","magicalEssence","fern","flowerring", "grate", "meatball", "twig",
                "rumen", "pigtail","talon","horn","succups","larynx","horsehair","ashes" };
        registerItem( itemReg, new ItemMagicalResource( metaItems ),"mi") ;
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

    private static void registerBlockItem( IForgeRegistry<Item> registry, Block block ){
        registry.register( new ItemBlock(block).setRegistryName(block.getRegistryName()).setCreativeTab( elementsTab ) );
    }

    private static void registerBlock(IForgeRegistry<Block> registry, Block block, String names ){
        registry.register(block.setRegistryName( new ResourceLocation(MortMagic.MODID,names) ).setUnlocalizedName(names));
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
        elemReg.register( new Element(new ResourceLocation(MortMagic.MODID,"force") ) );
        elemReg.register( new Element(new ResourceLocation(MortMagic.MODID,"life") ) );

    }

	//------------------------------init

	public static void registerItems(){

		/*ArmorMaterial robe = EnumHelper.addArmorMaterial("mmRobe", 10, new int[]{1,1,1,1}, 20);
		int robeRender = MortMagic.proxy.addArmor("mmRobe");
		commonRobe =  new ItemArmor( robe, robeRender, 2).setUnlocalizedName("commonRobe").setTextureName("mortmagic:robe");
		GameRegistry.registerItem( commonRobe, commonRobe.getUnlocalizedName() );
		
		MortMagic.robes.registerRobe( commonRobe , new RobesRegistry.RobeInfo( 2 ) );*/

		GameRegistry.registerTileEntity(TileEntityWildfire.class, "TEwildfire");
		GameRegistry.registerTileEntity(TileEntityTreeOfLifeCore.class, "TETOLCore");
		
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
		GameRegistry.addSmelting( new ItemStack(Resource.metaItem,1,2), new ItemStack(Resource.metaItem,1,1), 1);
		/*GameRegistry.addSmelting(mobDrop, new ItemStack(magicalEssence,1), 1);
		GameRegistry.addSmelting(ashes, new ItemStack(magicalEssence,1), 1);*/
		/*
		GameRegistry.addRecipe( new ShapelessRecipes( new ItemStack(metaItem,1,0), Arrays.asList(new ItemStack[]{ new ItemStack(Blocks.yellow_flower,1),new ItemStack(Blocks.red_flower,1),new ItemStack(Blocks.red_flower,1,1),new ItemStack(Blocks.red_flower,1,2),new ItemStack(Blocks.red_flower,1,3),new ItemStack(Blocks.red_flower,1,4),new ItemStack(Blocks.red_flower,1,5),new ItemStack(Blocks.red_flower,1,6),new ItemStack(Blocks.red_flower,1,7) } ) ) );
		*/
		//GameRegistry.addSmelting(pigtail, new ItemStack(magicalEssence,1), 1);
		//GameRegistry.addSmelting(rumen, new ItemStack(magicalEssence,1), 1);
		//GameRegistry.addSmelting(talon, new ItemStack(magicalEssence,1), 1);
		
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
