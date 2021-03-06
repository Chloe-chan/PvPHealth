package com.gmail.chloepika.plugins.pvphealth;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class HealthString
{
	public static char filledHeartIcon = '\u2764';
	public static char halfHeartIcon = '\u2665';
	public static char emptyHeartIcon = '\u2661';

	private static final FileConfiguration pluginConfig = Bukkit.getServer().getPluginManager().getPlugin("PvP Health").getConfig();

	private static final boolean useNumericalDisplay()
	{
		return pluginConfig.getBoolean("useNumericalDisplay", false);
	}
	
	private static final boolean halfNumericalDisplay()
	{
		return pluginConfig.getBoolean("halfNumericalDisplay", true);
	}

	private static final String getHitString()
	{
		return pluginConfig.getString("hitString", "$9[&6PvP Health&9]&r " + "$b%VICTIM%$c's health %COLON% $6%VICTIM-HEALTH%");
	}



	public static String getFinalString(Player attacker, Player victim, int damageDone)
	{
		int victimHealth = victim.getHealth();
		String hitString = getHitString();
		String preferredHealthString = getPreferredHealthString(victimHealth, victim);
		String returnString = hitString;
		{
			returnString = ChatColor.translateAlternateColorCodes('$', returnString);
			returnString = returnString.replaceAll("%QUOTE%", "'");
			returnString = returnString.replaceAll("%ATTACKER%", attacker.getName());
			returnString = returnString.replaceAll("%VICTIM%", victim.getName());
			returnString = returnString.replaceAll("%VICTIM-HEALTH%", preferredHealthString);
			returnString = returnString.replaceAll("%DAMAGE-DONE%", Integer.toString(damageDone));
		}
		return returnString;
	}

	public static String getPreferredHealthString(int health, Player victim)
	{
		if (useNumericalDisplay())
		{
			return getNumericalHealthString(health, victim, halfNumericalDisplay());
		} else
		{
			return getIconHealthString(health, victim);
		}
	}

	public static String getNumericalHealthString(int health, Player victim, boolean half)
	{
		int totalHealth = victim.getMaxHealth();
		double halfTotalHealth = ((double) totalHealth) / 2;
		double halfHealth = ((double) health) / 2;
		if (half)
		{
			return ("[ " + halfHealth + " / " + halfTotalHealth + " ]");
		} else
		{
			return ("[ " + health + " / " + totalHealth + " ]");
		}
	}

	public static String getIconHealthString(int health, Player player)
	{
		final int maxHealth = player.getMaxHealth();
		String filled = "";
		if (health > maxHealth)
		{
			health = maxHealth;
		}
		{
			if ((health % 2) != 0)
			{
				for (int filledHealth = 0; filledHealth < (health / 2); filledHealth ++)
				{
					filled = (filled + filledHeartIcon);
				}
				filled = (filled + halfHeartIcon);
				if ((health + 1) < maxHealth)
				{
					for (int emptyHealth = 0; emptyHealth < ((maxHealth - (health + 1)) / 2); emptyHealth ++)
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
				if (health < maxHealth)
				{
					for (int emptyHealth = 0; emptyHealth < ((maxHealth - health) / 2); emptyHealth ++)
					{
						filled = (filled + emptyHeartIcon);
					}
				}
			}
		}
		return filled;
	}
}
