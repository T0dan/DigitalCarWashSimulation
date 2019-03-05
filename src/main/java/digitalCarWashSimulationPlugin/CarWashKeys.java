package digitalCarWashSimulationPlugin;

import de.neemann.digital.core.element.Key;

public final class CarWashKeys {
	public static final Key<Integer> CARW_CARS = new Key.KeyInteger("carwcars", 1).setMin(0).setName(PluginLang.getTranslation("carwcars"))
		    .setDescription(PluginLang.getTranslation("carwcars_des"));
	public static final Key<Integer> CARW_WIDTH = new Key.KeyInteger("carwwidth", 2000).setName(PluginLang.getTranslation("carwwidth"))
			.setDescription(PluginLang.getTranslation("carwwidth_des"));
	public static final Key<Integer> CARW_HEIGHT = new Key.KeyInteger("carwheight", 800).setName(PluginLang.getTranslation("carwheight"))
			.setDescription(PluginLang.getTranslation("carwheight_des"));	
	public static final Key<Boolean> CARW_CLOCK = new Key<>("carwclock", false).setName(PluginLang.getTranslation("carwclock"))
			.setDescription(PluginLang.getTranslation("carwclock_des"));
}