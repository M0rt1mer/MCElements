package mort.mortmagic.sacrifice;

import mort.mortmagic.api.SacrificeRegistry.ISacrifice;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public interface IAltar {

	void addSacrifice( ISacrifice sacr );
	
	ISacrifice[] finishOffering();
	
	Vec3 getLocation();
	
	World getWorld();
	
}
