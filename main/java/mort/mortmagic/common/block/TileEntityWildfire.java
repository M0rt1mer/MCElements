package mort.mortmagic.common.block;

import java.util.LinkedList;

import mort.mortmagic.obsolete.SacrificeRegistry;
import mort.mortmagic.obsolete.SacrificeRegistry.ISacrifice;
import mort.mortmagic.common.sacrifice.IAltar;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.Vec3d;

public class TileEntityWildfire extends TileEntity implements IAltar{

	protected LinkedList<ISacrifice> sacrifices = new LinkedList<SacrificeRegistry.ISacrifice>();
	
	@Override
	public void addSacrifice(ISacrifice sacr) {
		System.out.println( "sacrifice" );
		if( sacrifices.isEmpty() || !sacrifices.getLast().stack(sacr) )
			sacrifices.addLast(sacr);
	}

	@Override
	public ISacrifice[] finishOffering() {
		ISacrifice[] offering = (ISacrifice[])sacrifices.toArray(new ISacrifice[0]);
		sacrifices.clear();
		return offering;
	}

	@Override
	public Vec3d getLocation() {
		return null;
	}


}
