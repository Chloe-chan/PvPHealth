package com.gmail.chloepika.plugins.pvphealth;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class StopSpam
{
	private static Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("PvP Health");
	private static List<String> pauseList = new ArrayList<String>();

	private static String combineNames(Player attacker, Player victim)
	{
		return (attacker.getName() + "-" + victim.getName());
	}

	public static boolean havePause(Player attacker, Player victim)
	{
		String combinedName = combineNames(attacker, victim);
		return pauseList.contains(combinedName);
	}

	public static void addToList(Player attacker, Player victim)
	{
		String combinedName = combineNames(attacker, victim);
		if (!havePause(attacker, victim))
		{
			pauseList.add(combinedName);
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new RemovePause(combinedName), (plugin.getConfig().getLong("preventSpamTime", 2) * 20));
		}
	}

	public static void removeFromList(String combinedName)
	{
		pauseList.remove(combinedName);
	}
}