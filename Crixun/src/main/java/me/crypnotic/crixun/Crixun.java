package me.crypnotic.crixun;

import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;

import me.crypnotic.crixun.api.IPlatform;
import me.crypnotic.crixun.api.utilities.Reflections;

public class Crixun extends JavaPlugin {

	private Logger logger;

	public void onEnable() {
		this.logger = getLogger();

		String version = Reflections.getFriendlyVersion();
		try {
			StringBuilder platform = new StringBuilder(".platform.Platform_");
			switch (version) {
			case "v1_8_R1": {
				platform.append(version);
				break;
			}
			default: {
				platform.append("alpha");
				break;
			}
			}

			Class<?> klass = Class.forName(getClass().getPackage().getName() + platform.toString());
			if (!IPlatform.class.isAssignableFrom(klass)) {
				logger.severe("Version `" + version + "` is not yet supported!");
			}

			Reflections.setPlatform((IPlatform) klass.newInstance());

			logger.info("Platform implementation `" + version + "` successfully hooked.");

			new Metrics(this).start();
		} catch (ClassNotFoundException exception) {
			logger.severe("Version `" + version + "` is not yet supported!");
		} catch (InstantiationException exception) {
			logger.severe("Version `" + version + "` is not yet supported!");
		} catch (IllegalAccessException exception) {
			logger.severe("IPlatform instance is already initialized. Dirty Haxors?");
		} catch (IOException exception) {
			logger.warning("Failed to initialize Metrics stat-tracking.");
		}
	}
}
