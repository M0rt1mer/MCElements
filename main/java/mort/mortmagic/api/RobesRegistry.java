package mort.mortmagic.api;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.Item;

public class RobesRegistry {
	
	private Map<Item,RobeInfo> map = new HashMap<Item, RobesRegistry.RobeInfo>();
			
	
	public void registerRobe(Item itm, RobeInfo info){
		map.put(itm, info);
	}
	
	public RobeInfo getRobe(Item itm){
		return map.get( itm );
	}
			
	public static class RobeInfo{
		
		public float maxMana;

		public RobeInfo(float maxMana) {
			super();
			this.maxMana = maxMana;
		}
		
	};
}
