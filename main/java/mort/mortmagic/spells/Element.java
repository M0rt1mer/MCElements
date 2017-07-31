package mort.mortmagic.spells;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class Element extends IForgeRegistryEntry.Impl<Element> {

	public Element(ResourceLocation res){
		this.setRegistryName(res);
	}

}
