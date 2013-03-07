package com.gmail.chloepika.plugins.pvphealth;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class HealthString
{
	private static FileConfiguration pluginConfig = Bukkit.getServer().getPluginManager().getPlugin("PvP Health").getConfig();

	private static final boolean useNumericalDisplay()
	{
		return pluginConfig.getBoolean("useNumericalDisplay", false);
	}

	private static final String getHitString()
	{
		return pluginConfig.getString("hitString", "$b%VICTIM%$c's health %COLON% $6%VICTIM-HEALTH%");
	}



	public static String getFinalString(Player attacker, Player victim, int damageDone, int victimHealth)
	{
		String pluginPrefix = PvPHealth.pluginPrefix;
		String hitString = getHitString();
		String preferredHealthString = getPreferredHealthString(victimHealth);
		String returnString = hitString;
		{
			returnString = ChatColor.translateAlternateColorCodes('$', returnString);
			returnString = returnString.replaceAll("%COLON%", ":");
			returnString = returnString.replaceAll("%ATTACKER%", attacker.getName());
			returnString = returnString.replaceAll("%VICTIM%", victim.getName());
			returnString = returnString.replaceAll("%VICTIM-HEALTH%", preferredHealthString);
			returnString = returnString.replaceAll("%DAMAGE-DONE%", Integer.toString(damageDone));
			returnString = pluginPrefix + returnString;
		}
		return returnString;
	}

	public static String getPreferredHealthString(int health)
	{
		if (useNumericalDisplay())
		{
			return getNumericalHealthString(health);
		} else
		{
			return getIconHealthString(health);
		}
	}

	public static String getNumericalHealthString(int health)
	{
		return ("[ " + health + " / 20 ]");
	}

	public static String getIconHealthString(int health)
	{
		final String filledHeartIcon = "❤";
		final String halfHeartIcon = "♥";
		final String emptyHeartIcon = "♡";
		if (health > 20)
		{
			return null;
		} else
		{
			String filled = "";
			if ((health % 2) != 0)
			{
				for (int filledHealth = 0; filledHealth < (health / 2); filledHealth ++)
				{
					filled = (filled + filledHeartIcon);
				}
				filled = (filled + halfHeartIcon);
				if ((health + 1) < 20)
				{
					for (int emptyHealth = 0; emptyHealth < ((20 - (health + 1)) / 2); emptyHealth ++)
					{
						filled = (filled + emptyHeartIcon);
					}
				}
			} else
			{
				for (int filledHealth = 0; filledHealth < (health / 2); filledHealth ++)
				{
					filled = (filled + filledHeartIcon);
				}
				if (health < 20)
				{
					for (int emptyHealth = 0; emptyHealth < ((20 - health) / 2); emptyHealth ++)
					{
						filled = (filled + emptyHeartIcon);
					}
				}
			}
			return filled;
		}
	}
}