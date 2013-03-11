package com.gmail.chloepika.plugins.pvphealth;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class HealthString
{
	private static final FileConfiguration pluginConfig = Bukkit.getServer().getPluginManager().getPlugin("PvP Health").getConfig();

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
		String preferredHealthString = getPreferredHealthString(victimHealth, victim);
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

	private static int getTotalHealth(Player player)
	{
		return 20;
	}

	public static String getPreferredHealthString(int health, Player victim)
	{
		if (useNumericalDisplay())
		{
			return getNumericalHealthString(health, victim);
		} else
		{
			return getIconHealthString(health, victim);
		}
	}

	public static String getNumericalHealthString(int health, Player victim)
	{
		int totalHealth = getTotalHealth(victim);
		return ("[ " + health + " / " + totalHealth + " ]");
	}

	public static String getIconHealthString(int health, Player victim)
	{
		int totalHealth = getTotalHealth(victim);
		final String filledHeartIcon = "❤";
		final String halfHeartIcon = "♥";
		final String emptyHeartIcon = "♡";
		if (health > totalHealth)
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
				if ((health + 1) < totalHealth)
				{
					for (int emptyHealth = 0; emptyHealth < ((totalHealth - (health + 1)) / 2); emptyHealth ++)
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
				if (health < totalHealth)
				{
					for (int emptyHealth = 0; emptyHealth < ((totalHealth - health) / 2); emptyHealth ++)
					{
						filled = (filled + emptyHeartIcon);
					}
				}
			}
			return filled;
		}
	}
}