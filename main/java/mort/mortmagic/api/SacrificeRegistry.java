package mort.mortmagic.api;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.LinkedList;
import java.util.List;

public class SacrificeRegistry {

	public List<SacrificeRecipe> recipes = new LinkedList<SacrificeRegistry.SacrificeRecipe>();
	
	public IBlessing findMatch(ISacrifice[] sac){
		float bestMatch = 0;
		SacrificeRecipe bestRec = null;
		for( SacrificeRecipe rec : recipes ){
			float match = measureMatch(sac, rec.offering);
			if( match>bestMatch){
				bestMatch = match;
				bestRec = rec;
			}
		}
		if(bestRec==null)
			return null;
		return bestRec.blessing;
	}
	
	public void addRecipe( ISacrifice[] offering, IBlessing blessing ){
			this.recipes.add( new SacrificeRecipe(offering, blessing) );
	}
	
	protected float measureMatch(ISacrifice[] sac, ISacrifice[] rec){
		
		float match = 1;
		int i = 0; int j = 0;
		while( i<sac.length || j<rec.length ){
			float thisMatch = rec[j].matches( sac[i] );
			if( thisMatch >0 ){
				match *= thisMatch;
				i++; j++;
			}
			else{
				i++;
			}
			
		}
		return match;
	}
	
	public static class SacrificeRecipe{
		
		public ISacrifice[] offering;
		public IBlessing blessing;
		public SacrificeRecipe(ISacrifice[] offering, IBlessing blessing) {
			this.offering = offering;
			this.blessing = blessing;
		}
	}
	
	public static interface ISacrifice{
		/**
		 * Alternative to equals() for checking match in recipes. This is the recipe stack(e.q. the amount can be higher here)
		 * @param sac
		 * @return percentual match. <0 for mismatch, 1 for 100% match. Recipe can be performed even with 1% match, as long as there is no recipe with higher match
		 */
		float matches(ISacrifice sac);
		/**
		 * Tries to stack given sacrifice into this one.
		 * @param sac
		 * @return true if stacking was successful and sac should be removed. On false, sac still contains something and should not be thrown away
		 */
		boolean stack(ISacrifice sac);
	}
	
	public static class ItemSacrifice implements ISacrifice{
		public ItemStack stack;

		public ItemSacrifice(ItemStack stack) {
			super();
			this.stack = stack;
		}

		@Override
		public float matches(ISacrifice sac) {
			if( !(sac instanceof ItemSacrifice) )
				return -1;
			ItemSacrifice sac2 = ((ItemSacrifice)sac);
			if( stack.getItem() != sac2.stack.getItem() )
				return -1;
			if( sac2.stack.getCount() < stack.getCount() )
				return -1;
			if( (stack.getTagCompound()==null^sac2.stack.getTagCompound()==null) ||
				(stack.getTagCompound()!=null && sac2.stack.getTagCompound()!=null && !stack.getTagCompound().equals(sac2.stack.getTagCompound()) ) )
					return -1; //if only one of them has NBT tag, or both have and they are different
			if( stack.getItemDamage()==-1 || stack.getItemDamage()==sac2.stack.getItemDamage() )
				return 1;
			if( stack.isStackable() && stack.getItemDamage()>sac2.stack.getItemDamage()  )
				return stack.getCount()/sac2.stack.getCount();
			return -1;
			
		}

		@Override
		public boolean stack(ISacrifice sac) {
			if( !(sac instanceof ItemSacrifice) )
				return false;
			return false;
		}
		
	}

	public static class ExpSacrifice implements ISacrifice{
		int amount;
		
		
		public ExpSacrifice(int amount) {
			super();
			this.amount = amount;
		}

		@Override
		public float matches(ISacrifice sac) {
			if( !(sac instanceof ExpSacrifice) )
				return -1;
			if( amount < ((ExpSacrifice)sac).amount )
				return -1;
			return ((ExpSacrifice)sac).amount/amount;
		}

		@Override
		public boolean stack(ISacrifice sac) {
			if( !(sac instanceof ExpSacrifice) )
				return false;
			this.amount += ((ExpSacrifice)sac).amount;
			return true;
		}
		
	}
	
	public static interface IBlessing {
		void reward( World wld, double x, double y, double z );
	}
	
	public static class ItemReward implements IBlessing{
		public ItemStack reward;
		public ItemReward(ItemStack reward) {
			super();
			this.reward = reward;
		}
		@Override
		public void reward(World wld, double x, double y, double z) {
			EntityItem itm = new EntityItem( wld, x,y+1,z, reward.copy() );
			wld.spawnEntity(itm);
		}
	}
	
}
