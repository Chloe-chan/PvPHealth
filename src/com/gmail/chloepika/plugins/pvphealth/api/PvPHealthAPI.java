package com.gmail.chloepika.plugins.pvphealth.api;

import java.io.InputStream;

import org.bukkit.entity.Player;

import com.gmail.chloepika.plugins.pvphealth.HealthString;
import com.gmail.chloepika.plugins.pvphealth.Local;
import com.gmail.chloepika.plugins.pvphealth.nametag.PlayerTagManager;

public class PvPHealthAPI
{
	public static final String VERSION = "1.3.4";

	/**
	 * Sets custom locale file to be used.
	 * 
	 * @param is InputStream of the locale file.
	 */
	public static void registerCustomLocale(InputStream is)
	{
		Local.registerCustomLocale(is);
	}

	public static char getFilledHeart()
	{
		return HealthString.filledHeartIcon;
	}

	public static void setFilledHeart(char filledHeartIcon)
	{
		HealthString.filledHeartIcon = filledHeartIcon;
	}

	public static char getHalfHeart()
	{
		return HealthString.halfHeartIcon;
	}

	public static void setHalfHeart(char halfHeartIcon)
	{
		HealthString.halfHeartIcon = halfHeartIcon;
	}

	public static char getEmptyHeart()
	{
		return HealthString.emptyHeartIcon;
	}

	public static void setEmptyHeart(char emptyHeartIcon)
	{
		HealthString.emptyHeartIcon = emptyHeartIcon;
	}

	/**
	 * Gets the health icon string.
	 * 
	 * @param health The health to draw the string.
	 * 
	 * @return Returns the health icon string.
	 */
	public static String getIconHealth(int health)
	{
		return HealthString.getIconHealthString(health, null);
	}

	/**
	 * Gets the preferred plus filled health string.
	 * 
	 * @param attacker The player who attacked.
	 * @param victim The player who got hit.
	 * @param damageDone The damage done by the attacker.
	 * 
	 * @return Returns the preferred plus filled health string.
	 * 
	 * @deprecated Not recommended, use {@link #getIconHealth(health)} instead.
	 */
	public static String getFinalisedString(Player attacker, Player victim, int damageDone)
	{
		return HealthString.getFinalString(attacker, victim, damageDone);
	}

	/**
	 * Updates the health under name tag.
	 * 
	 * @param player Player to update health.
	 */
	public static void updateHealthtag(Player player)
	{
		PlayerTagManager.updateHealth(player);
	}
}