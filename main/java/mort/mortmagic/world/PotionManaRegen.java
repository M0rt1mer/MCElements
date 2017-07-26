package mort.mortmagic.world;

import mort.mortmagic.ExtendedPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;

public class PotionManaRegen extends Potion{

	public PotionManaRegen(int p_i1573_1_, boolean p_i1573_2_, int p_i1573_3_) {
		super(p_i1573_1_, p_i1573_2_, p_i1573_3_);
	}

	@Override
	public void performEffect(EntityLivingBase p_76394_1_, int p_76394_2_) {
		if(p_76394_1_ instanceof EntityPlayer ){
			ExtendedPlayer.getExtendedPlayer(((EntityPlayer)p_76394_1_)).gainMana( p_76394_2_ );
		}
	}

	@Override
	public boolean isReady(int p_76397_1_, int p_76397_2_) {
		return true;
	}

	
	
	
}
