package mort.mortmagic;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import mort.mortmagic.api.RobesRegistry;
import mort.mortmagic.spells.BaseFire;
import mort.mortmagic.spells.BaseLife;
import mort.mortmagic.spells.Element;
import mort.mortmagic.spells.FireCold;
import mort.mortmagic.spells.ISpell;
import mort.mortmagic.spells.life.MagicalBonemeal;
import mort.mortmagic.spells.life.TreeOfLife;
import mort.mortmagic.world.EnchantmentMagical;
import mort.mortmagic.world.PotionManaRegen;
import mort.mortmagic.world.block.BlockBonfire;
import mort.mortmagic.world.block.BlockLiveDirt;
import mort.mortmagic.world.block.BlockNightfern;
import mort.mortmagic.world.block.BlockTreeOfLiveCore;
import mort.mortmagic.world.block.BlockTreeRoot;
import mort.mortmagic.world.block.BlockWildFire;
import mort.mortmagic.world.block.TileEntityTreeOfLifeCore;
import mort.mortmagic.world.block.TileEntityWildfire;
import mort.mortmagic.world.items.ItemCharge;
import mort.mortmagic.world.items.ItemDagger;
import mort.mortmagic.world.items.ItemEnchantable;
import mort.mortmagic.world.items.ItemMPotion;
import mort.mortmagic.world.items.ItemMagicalResource;
import mort.mortmagic.world.items.ItemScroll;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSand;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.potion.Potion;
import net.minecraftforge.common.util.EnumHelper;
import scala.actors.threadpool.Arrays;
import cpw.mods.fml.common.registry.GameRegistry;

public class Resource {

	//--------------------------------SPELLS
	public static Element fire = new Element("fire");
	public static Element force = new Element("force");
	public static Element life = new Element("life");
	
	public static ISpell baseFire = new BaseFire("baseFireSpell").setTextureName("mortmagic:fire"); 
	public static ISpell baseLife = new BaseLife("baseLifeSpell").setTextureName("mortmagic:life");
	public static ISpell fireCold = new FireCold("baseIceSpell").setTextureName("mortmagic:ice");
	
	//--------------------------------BLOCK
	//public static Block fern = new BlockFern("fern").setBlockTextureName("mortmagic:fern");
	public static Block bonfire = new BlockBonfire(Material.wood).setHardness(1).setTickRandomly(true).setBlockName("bonfire").setBlockTextureName("mortmagic:bonfire");
	public static Block liveDirt = new BlockLiveDirt(Material.ground).setHardness(1).setBlockName("liveDirt").setBlockTextureName("mortmagic:livedirt");
	public static Block wildfire = new BlockWildFire( ).setBlockName("blockWildFire").setLightLevel(1.0F).setBlockTextureName("fire");
	public static Block ashesBlock = new BlockSand().setStepSound(Block.soundTypeSand).setBlockName("ashes").setBlockTextureName("mortmagic:ashesBlock");
	
	public static Block nightFern = new BlockNightfern( "nightFern" ).setBlockTextureName("mortmagic:fern");
	
	public static Block treeCore = new BlockTreeOfLiveCore().setBlockName("treeoflifecore").setBlockTextureName("mortmagic:treeCore");
	public static Block treeRoot = new BlockTreeRoot().setBlockName("treeofliferoot").setBlockTextureName("mortmagic:treeRoot");
	//--------------------------------ITEMS
	public static Item fireSpell = new ItemScroll("fireSpell").setElement(fire).setTextureName("mortmagic:fireSpell");
	public static Item lifeSpell = new ItemScroll("lifeSpell").setElement(life).setTextureName("mortmagic:lifeSpell");
	public static Item magicalEssence = new Item().setUnlocalizedName("magicalEssence").setTextureName("mortmagic:magicalEssence");
	
	public static Item stoneDagger = new ItemDagger( ToolMaterial.STONE, false ).setUnlocalizedName("stoneDagger").setTextureName("mortmagic:stoneDagger");
	public static Item stoneDaggerSacred = new ItemDagger( ToolMaterial.STONE, true ).setUnlocalizedName("stoneDaggerSacred").setTextureName("mortmagic:stoneDaggerSacred");
	
	public static Item fern = new Item().setUnlocalizedName("magicalFern").setTextureName("mortmagic:fern");
	
	//magical resources
	public static String[] resources = new String[]{ "rumen", "pigtail","talon","horn","succups","larynx","horsehair" };
	public static Item mobDrop = new ItemMagicalResource( resources ).setUnlocalizedName("mobDrop").setTextureName("mortmagic:mobdrop");
	public static String[] metaItems = new String[]{"flowerring","grate", "meatball"};
	public static Item metaItem = new ItemMagicalResource( metaItems ).setUnlocalizedName("mi").setTextureName("mortmagic:mi");
	//organs
	//public static Item abomasum = new Item().setUnlocalizedName("abomasum").setTextureName("mortmagic:abomasum");
	//public static Item pigtail = new Item().setUnlocalizedName("pigtail").setTextureName("mortmagic:pigtail");
	//public static Item rumen = new Item().setUnlocalizedName("rumen").setTextureName("mortmagic:rumen");
	//public static Item talon = new Item().setUnlocalizedName("talon").setTextureName("mortmagic:talon");

	//public static Item meatBall = new Item().setUnlocalizedName("meatBall").setTextureName("mortmagic:meatBall");
	
	public static Item testImplement = new Item().setUnlocalizedName("wand").setTextureName("mortmagic:wand");	
	
	public static Item ashes = new ItemEnchantable().setUnlocalizedName("ashes").setTextureName("mortmagic:ashes"); 
	//public static Item grate = new Item().setUnlocalizedName("grate").setTextureName("mortmagic:grate");
	
	public static Enchantment mag = new EnchantmentMagical(150, 1, EnumEnchantmentType.all).setName("magicalResource");
	
	public static ItemCharge charge = (ItemCharge) new ItemCharge().setUnlocalizedName("charge");
	public static Item scroll = new Item().setUnlocalizedName("scroll").setTextureName("mortmagic:scroll");
	
	//---- equipment
	public static Item commonRobe;
	
	//------------------------------potions
	
	public static Item potion = new ItemMPotion().setUnlocalizedName("potion").setTextureName("potion");
	
	public static Potion regenPotion;
	
	//------------------------------init
	
	public static void registerItems(){
		GameRegistry.registerItem(fireSpell, fireSpell.getUnlocalizedName());
		GameRegistry.registerItem(lifeSpell, lifeSpell.getUnlocalizedName());
		
		//GameRegistry.registerBlock(fern, fern.getUnlocalizedName() );
		GameRegistry.registerBlock(bonfire, bonfire.getUnlocalizedName() );
		GameRegistry.registerBlock(wildfire, wildfire.getUnlocalizedName() );
		GameRegistry.registerBlock(ashesBlock, ashesBlock.getUnlocalizedName());
		GameRegistry.registerBlock(liveDirt, liveDirt.getUnlocalizedName());
		
		GameRegistry.registerBlock(treeCore, treeCore.getUnlocalizedName());
		GameRegistry.registerBlock(treeRoot, treeRoot.getUnlocalizedName());
		
		GameRegistry.registerItem( magicalEssence, magicalEssence.getUnlocalizedName());
		GameRegistry.registerItem( stoneDagger, stoneDagger.getUnlocalizedName() );
		GameRegistry.registerItem( stoneDaggerSacred, stoneDaggerSacred.getUnlocalizedName() );
		
		GameRegistry.registerItem( fern, fern.getUnlocalizedName() );
		
		ArmorMaterial robe = EnumHelper.addArmorMaterial("mmRobe", 10, new int[]{1,1,1,1}, 20);
		int robeRender = MortMagic.proxy.addArmor("mmRobe");
		commonRobe =  new ItemArmor( robe, robeRender, 2).setUnlocalizedName("commonRobe").setTextureName("mortmagic:robe");
		GameRegistry.registerItem( commonRobe, commonRobe.getUnlocalizedName() );
		
		MortMagic.robes.registerRobe( commonRobe , new RobesRegistry.RobeInfo( 2 ) );
		
		GameRegistry.registerItem( mobDrop, mobDrop.getUnlocalizedName() );  
		//GameRegistry.registerItem( meatBall, meatBall.getUnlocalizedName() );
		
		GameRegistry.registerItem( testImplement , testImplement.getUnlocalizedName() );
		
		GameRegistry.registerItem( ashes, ashes.getUnlocalizedName() );
		GameRegistry.registerItem( metaItem, metaItem.getUnlocalizedName() );
		
		GameRegistry.registerItem( charge, charge.getUnlocalizedName() );
		
		GameRegistry.registerBlock( nightFern, nightFern.getUnlocalizedName() );
		GameRegistry.registerWorldGenerator( (BlockNightfern)nightFern, 10);
		
		GameRegistry.registerTileEntity(TileEntityWildfire.class, "TEwildfire");
		GameRegistry.registerTileEntity(TileEntityTreeOfLifeCore.class, "TETOLCore");
		
		GameRegistry.registerItem( potion, potion.getUnlocalizedName() );
		
	}
	
	public static void registerRecipes(){
		
		ItemStack cobble = new ItemStack( Blocks.cobblestone, 1 );
		ItemStack stick = new ItemStack( Items.stick, 1 );
		ItemStack grateI = new ItemStack( metaItem, 1, 1);
		ItemStack mag = new ItemStack( magicalEssence, 1 );
		
		GameRegistry.addRecipe( new ShapedRecipes( 2, 3, new ItemStack[]{ null, cobble, cobble, cobble, cobble, stick } , new ItemStack(stoneDagger,1) ) );
		GameRegistry.addRecipe( new ShapelessRecipes( new ItemStack(stoneDaggerSacred,1), Arrays.asList(new ItemStack[]{ new ItemStack(stoneDagger,1,0), new ItemStack(magicalEssence,1) }) ) );
		
		GameRegistry.addRecipe( new ShapedRecipes(3,3,new ItemStack[]{stick,stick,stick,stick,stick,null,stick,stick,stick,stick},grateI) );
		GameRegistry.addRecipe( new ShapedRecipes(3,3,new ItemStack[]{grateI,grateI,grateI,grateI,grateI,mag,grateI,grateI,grateI,grateI},new ItemStack(bonfire,8)) );
		
		GameRegistry.addRecipe( new ShapelessRecipes( new ItemStack(scroll,1), Arrays.asList(new ItemStack[]{new ItemStack(Items.paper,1),new ItemStack(magicalEssence,1)} ) ) );
		GameRegistry.addRecipe( new ShapelessRecipes( new ItemStack(fireSpell,1), Arrays.asList(new ItemStack[]{new ItemStack(scroll,1),new ItemStack(ashes,1)} ) ) );
		
		GameRegistry.addRecipe( new ShapelessRecipes( new ItemStack(lifeSpell,1), Arrays.asList(new ItemStack[]{new ItemStack(Items.paper,1),new ItemStack(metaItem,1,2)} ) ) );
		//GameRegistry.addRecipe( new ShapelessRecipes( new ItemStack(meatBall,1), Arrays.asList(new ItemStack[]{new ItemStack(abomasum,1),new ItemStack(rumen,1),new ItemStack(pigtail,1),new ItemStack(talon,1)} ) ) );
		//meatBall
		GameRegistry.addRecipe( new ShapelessRecipes( new ItemStack(metaItem,1,2), Arrays.asList(new ItemStack[]{new ItemStack(mobDrop,1,0),new ItemStack(mobDrop,1,1),new ItemStack(mobDrop,1,2),new ItemStack(mobDrop,1,3)} ) ) );
		GameRegistry.addRecipe( new ShapelessRecipes( new ItemStack(liveDirt,1), Arrays.asList(new ItemStack[]{  new ItemStack( Blocks.dirt,1), new ItemStack(metaItem,1,0), new ItemStack(metaItem,1,2) } ) ) );
		
		
		GameRegistry.addSmelting(fern, new ItemStack(magicalEssence,1), 1);
		GameRegistry.addSmelting(mobDrop, new ItemStack(magicalEssence,1), 1);
		GameRegistry.addSmelting(ashes, new ItemStack(magicalEssence,1), 1);
		
		GameRegistry.addRecipe( new ShapelessRecipes( new ItemStack(metaItem,1,0), Arrays.asList(new ItemStack[]{ new ItemStack(Blocks.yellow_flower,1),new ItemStack(Blocks.red_flower,1),new ItemStack(Blocks.red_flower,1,1),new ItemStack(Blocks.red_flower,1,2),new ItemStack(Blocks.red_flower,1,3),new ItemStack(Blocks.red_flower,1,4),new ItemStack(Blocks.red_flower,1,5),new ItemStack(Blocks.red_flower,1,6),new ItemStack(Blocks.red_flower,1,7) } ) ) );
		//GameRegistry.addSmelting(pigtail, new ItemStack(magicalEssence,1), 1);
		//GameRegistry.addSmelting(rumen, new ItemStack(magicalEssence,1), 1);
		//GameRegistry.addSmelting(talon, new ItemStack(magicalEssence,1), 1);
		
	}
	
	
	public static void registerSpells(){
		
		MortMagic.spellReg.registerSpellDefault(fire, baseFire);
		MortMagic.spellReg.registerSpellDefault(life, baseLife);
		MortMagic.spellReg.registerSpell(fire, testImplement, fireCold);
		
		MortMagic.life.registry.add(new MagicalBonemeal());
		MortMagic.life.registry.add(new TreeOfLife());
		
	}
	
	public static void registerPotions(){
		
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
	}
	
}
