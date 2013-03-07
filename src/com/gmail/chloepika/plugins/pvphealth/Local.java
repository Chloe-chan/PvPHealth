package com.gmail.chloepika.plugins.pvphealth;

import java.io.InputStream;
import java.util.Locale;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class Local
{
	private static final Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("PvP Health");
	private static Locale currentLocale = Locale.ENGLISH;
	private static final String configNameStart = "local_";
	private static YamlConfiguration config;

	public static void setLocale(Locale locale)
	{
		Local.currentLocale = locale;
	}

	public static void reloadConfig()
	{
		String localeName = currentLocale.getLanguage();
		String configName = (configNameStart + localeName + ".yml");
		InputStream configStream = plugin.getResource(configName);
		YamlConfiguration config = YamlConfiguration.loadConfiguration(configStream);
		Local.config = config;
	}

	public static String getString(LocalMessage lm)
	{
		String message = lm.getMessage();
		return config.getString(message);
	}
}