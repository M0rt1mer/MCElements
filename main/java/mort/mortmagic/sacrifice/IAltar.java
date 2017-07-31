package mort.mortmagic.sacrifice;

import mort.mortmagic.api.SacrificeRegistry.ISacrifice;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public interface IAltar {

	void addSacrifice( ISacrifice sacr );
	
	ISacrifice[] finishOffering();
	
	Vec3d getLocation();
	
	World getWorld();
	
}
