package mort.mortmagic.sacrifice;

import mort.mortmagic.MortMagic;
import mort.mortmagic.Resource;
import mort.mortmagic.api.SacrificeRegistry.IBlessing;
import mort.mortmagic.api.SacrificeRegistry.ISacrifice;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;

public abstract class SacrificeHelper {

	
	public static boolean canBeOffered( ItemStack stk ){
		return stk.getItem()!=Resource.ashes;
	}
	
	public static void finishOfferingAtAltar( IAltar altar, ItemStack finalizer ){
		
		ISacrifice[] offering = altar.finishOffering();
		if( finalizer.getItem() == Resource.ashes ){
			IBlessing bless = MortMagic.sacrReg.findMatch( offering );
			if(bless!=null){
				Vec3 loc = altar.getLocation();
				bless.reward( altar.getWorld(), loc.xCoord, loc.yCoord, loc.zCoord);
			}
		}
		
		
	}
	
	
}
