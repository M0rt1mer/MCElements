package mort.mortmagic.spells;

import cpw.mods.fml.common.registry.LanguageRegistry;

public class Element {

	String name;
	
	public Element(String name) {
		this.name = name;
	}
	
	public String getTranslatedName(){
		return LanguageRegistry.instance().getStringLocalization(name);
	}
	
	
	
}
