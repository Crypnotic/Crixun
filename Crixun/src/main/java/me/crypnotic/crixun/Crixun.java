package me.crypnotic.crixun;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

import me.crypnotic.crixun.api.IPlatform;
import me.crypnotic.crixun.api.utilities.Reflections;

public class Crixun extends JavaPlugin {

	private Logger logger;

	public void onEnable() {
		this.logger = getLogger();

		String version = Reflections.getFriendlyVersion();
		try {
			Class<?> klass = Class.forName(getClass().getPackage().getName() + ".platform.Platform_" + version);
			if (!IPlatform.class.isAssignableFrom(klass)) {
				logger.severe("Version `" + version + "` is not yet supported!");
			}

			Reflections.setPlatform((IPlatform) klass.newInstance());

			logger.info("Platform implementation `" + version + "` successfully hooked.");
		} catch (ClassNotFoundException exception) {
			logger.severe("Version `" + version + "` is not yet supported!");
		} catch (InstantiationException exception) {
			logger.severe("Version `" + version + "` is not yet supported!");
		} catch (IllegalAccessException exception) {
			logger.severe("IPlatform instance is already initialized. Dirty Haxors?");
		}
	}
}
