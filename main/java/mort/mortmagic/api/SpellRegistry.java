package mort.mortmagic.api;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import mort.mortmagic.spells.Element;
import mort.mortmagic.spells.ISpell;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

/**
 * Registers spells for element/implement combinations
 * @author Martin
 *
 */
public class SpellRegistry {

	Table<Element, Item, ISpell> registry = HashBasedTable.create();
	Map<Element, ISpell> defaults = new HashMap();
	
	Map<String,ISpell> nameRegistry = new HashMap<String,ISpell>();
	Map<ISpell,Element> backSearch = new HashMap<ISpell, Element>();
	
	//@SideOnly(Side.CLIENT)
	public Map<ISpell,IIcon> iconMap = new HashMap<ISpell, IIcon>();
	
	/**
	 * Registers Element/implement combination
	 * @param ele
	 * @param impl
	 * @param spl
	 */
	public void registerSpell( Element ele, Item impl, ISpell spl ){
		registry.put(ele, impl, spl);
		if( nameRegistry.containsKey( spl.getUnlocalizedName() ) )
			throw new RuntimeException("Two spells with the name "+spl.getUnlocalizedName());
		nameRegistry.put( spl.getUnlocalizedName(), spl );
		backSearch.put(spl, ele);
	}
	
	/**
	 * Registers Element default spell
	 * @param ele
	 * @param spl
	 */
	public void registerSpellDefault( Element ele, ISpell spl ){
		defaults.put(ele,spl);
		nameRegistry.put( spl.getUnlocalizedName(), spl );
		backSearch.put(spl, ele);
	}
	
	public ISpell getSpellByName( String str ){
		return nameRegistry.get(str);
	}
	
	public Element getSpellsElement( ISpell spl ){
		return backSearch.get(spl);
	}
	
	
	
	public ISpell getSpell( Element ele, Item impl ){
		if(registry.get(ele, impl)!=null)
			return registry.get(ele, impl);
		
		return defaults.get(ele);
		
	}
	
	public Set<ISpell> getAllSpells(){
		return backSearch.keySet();
	}
	
	public void setSpellTextures(){
		
	}
}
