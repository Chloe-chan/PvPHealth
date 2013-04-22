package com.gmail.chloepika.plugins.pvphealth.nametag;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import com.gmail.chloepika.plugins.pvphealth.HealthString;
import com.gmail.chloepika.plugins.pvphealth.HideHealth;

public class PlayerTagManager implements Listener
{
	public static Scoreboard healthScoreboard;

	public static void registerScoreboard()
	{
		ScoreboardManager sbm = Bukkit.getScoreboardManager();
		Scoreboard sb = sbm.getNewScoreboard();
		Objective obj = sb.registerNewObjective("health", "health");
		obj.setDisplayName(String.valueOf(HealthString.filledHeartIcon));
		obj.setDisplaySlot(DisplaySlot.BELOW_NAME);
		healthScoreboard = sb;
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerKicked(PlayerKickEvent event)
	{
		if (!event.isCancelled())
		{
			healthScoreboard.resetScores(event.getPlayer());
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerLeave(PlayerQuitEvent event)
	{
		healthScoreboard.resetScores(event.getPlayer());
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		if (!HideHealth.isHidden(event.getPlayer()))
		{
			event.getPlayer().setScoreboard(healthScoreboard);
		}
	}

	public static void resetScore(Player player)
	{
		healthScoreboard.resetScores(player);
	}

	public static void addScore(Player player)
	{
		player.setScoreboard(healthScoreboard);
	}
}