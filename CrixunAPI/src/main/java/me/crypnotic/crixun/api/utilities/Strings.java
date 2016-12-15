package me.crypnotic.crixun.api.utilities;

import net.md_5.bungee.api.ChatColor;

public class Strings {

	public static String color(String text) {
		return ChatColor.translateAlternateColorCodes('&', text);
	}
}
