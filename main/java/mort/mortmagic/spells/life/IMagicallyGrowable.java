package mort.mortmagic.spells.life;

import net.minecraft.world.World;

public interface IMagicallyGrowable {

	public boolean isApplicable( World wld, int x, int y, int z );
	
	public void apply( World wld, int x, int y, int z );
	
	public float getCost();
	
}
