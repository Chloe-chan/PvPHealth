package com.gmail.chloepika.plugins.pvphealth.api;

import java.io.InputStream;

import org.bukkit.entity.Player;

import com.gmail.chloepika.plugins.pvphealth.HealthString;
import com.gmail.chloepika.plugins.pvphealth.Local;

public class PvPHealthAPI
{
	/**
	 * Sets custom locale file to be used.
	 * 
	 * @param is InputStream of the locale file.
	 */
	public static void registerCustomLocale(InputStream is)
	{
		Local.registerCustomLocale(is);
	}

	public static String getFilledHeart()
	{
		return "❤";
	}

	public static String getHalfHeart()
	{
		return "♥";
	}

	public static String getEmptyHeart()
	{
		return "♡";
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
		return HealthString.getFinalString(attacker, victim, damageDone, false);
	}

	/**
	 * Gets the overall total health set to calculate.
	 * Use {@link com.gmail.chloepika.plugins.pvphealth.PvPHealthEvent} for specific players.
	 * 
	 * @return Returns the total health.
	 */
	public static int getTotalHealth()
	{
		return HealthString.getTotalHealth();
	}

	/**
	 * Sets the overall total health set to calculate.
	 * Use {@link com.gmail.chloepika.plugins.pvphealth.PvPHealthEvent} for specific players.
	 * 
	 * @param health The total health.
	 */
	public static void setTotalHealth(int health)
	{
		HealthString.setTotalHealth(health);
	}
}