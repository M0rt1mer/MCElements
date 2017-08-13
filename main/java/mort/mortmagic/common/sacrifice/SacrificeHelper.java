package mort.mortmagic.common.sacrifice;

import mort.mortmagic.MortMagic;
import mort.mortmagic.Resource;
import mort.mortmagic.api.SacrificeRegistry.IBlessing;
import mort.mortmagic.api.SacrificeRegistry.ISacrifice;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;

public abstract class SacrificeHelper {

	
	public static boolean canBeOffered( ItemStack stk ){
		return stk.getItem()!=Resource.metaItem;
	}
	
	public static void finishOfferingAtAltar( IAltar altar, ItemStack finalizer ){
		
		ISacrifice[] offering = altar.finishOffering();
		if( finalizer.getItem() == Resource.metaItem ){
			IBlessing bless = MortMagic.sacrReg.findMatch( offering );
			if(bless!=null){
				Vec3d loc = altar.getLocation();
				bless.reward( altar.getWorld(), loc.x, loc.y, loc.z );
			}
		}
		
		
	}
	
	
}
