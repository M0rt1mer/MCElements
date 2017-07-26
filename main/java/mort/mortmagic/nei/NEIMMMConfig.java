package mort.mortmagic.nei;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;

public class NEIMMMConfig implements IConfigureNEI {

	@Override
	public String getName() {
		return "Morts Minecraft Magic";
	}

	@Override
	public String getVersion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void loadConfig() {
		//API.registerRecipeHandler(handler);
		//API.registerUsageHandler(handler);
		System.out.println("NEI recipe handler");
	}

	
}
