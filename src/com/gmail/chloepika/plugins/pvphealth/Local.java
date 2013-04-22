package com.gmail.chloepika.plugins.pvphealth;

import java.io.InputStream;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class Local
{
	public enum LocalName
	{
		en("en");

		private String name;

		private LocalName(String name)
		{
			this.name = name;
		}

		private String getName()
		{
			return name;
		}
	}

	public enum LocalMessage
	{
		healthHidden("healthHidden"),
		healthAlreadyHidden("healthAlreadyHidden"),
		healthShown("healthShown"),
		healthAlreadyShown("healthAlreadyShown"),
		playerHealthHidden("playerHealthHidden"),
		playerNotOnline("playerNotOnline"),
		noPerm("noPerm");

		private String message;

		private LocalMessage(String message)
		{
			this.message = message;
		}

		public String getMessage()
		{
			return message;
		}

		public String getLocalisedMessage()
		{
			return ChatColor.translateAlternateColorCodes('$', Local.getString(this));
		}
	}

	private static final Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("PvP Health");
	private static String currentLocale = LocalName.en.getName();
	private static final String configNameStart = "local_";
	private static YamlConfiguration config;

	public static void setLocale(LocalName locale)
	{
		Local.currentLocale = locale.getName();
		String configName = (configNameStart + currentLocale + ".yml");
		InputStream configStream = plugin.getResource(configName);
		YamlConfiguration config = YamlConfiguration.loadConfiguration(configStream);
		Local.config = config;
	}

	public static String getString(LocalMessage lm)
	{
		String message = lm.getMessage();
		return config.getString(message);
	}

	public static void registerCustomLocale(InputStream is)
	{
		YamlConfiguration config = YamlConfiguration.loadConfiguration(is);
		Local.config = config;
	}
}