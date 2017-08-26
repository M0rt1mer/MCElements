package mort.mortmagic.common.sacrifice;

import mort.mortmagic.Content;
import mort.mortmagic.MortMagic;
import mort.mortmagic.obsolete.SacrificeRegistry.IBlessing;
import mort.mortmagic.obsolete.SacrificeRegistry.ISacrifice;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;

public abstract class SacrificeHelper {

	
	public static boolean canBeOffered( ItemStack stk ){
		return stk.getItem()!= Content.metaItem;
	}
	
	public static void finishOfferingAtAltar( IAltar altar, ItemStack finalizer ){
		
		ISacrifice[] offering = altar.finishOffering();
		if( finalizer.getItem() == Content.metaItem ){
			IBlessing bless = MortMagic.sacrReg.findMatch( offering );
			if(bless!=null){
				Vec3d loc = altar.getLocation();
				bless.reward( altar.getWorld(), loc.x, loc.y, loc.z );
			}
		}
		
		
	}
	
	
}
