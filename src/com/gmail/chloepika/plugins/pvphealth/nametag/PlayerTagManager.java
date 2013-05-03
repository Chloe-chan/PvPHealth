package com.gmail.chloepika.plugins.pvphealth.nametag;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import com.gmail.chloepika.plugins.pvphealth.HealthString;
import com.gmail.chloepika.plugins.pvphealth.HideHealth;

public class PlayerTagManager implements Listener
{
	private static Scoreboard healthScoreboard;
	private static Objective healthObjective;

	public static void registerScoreboard()
	{
		Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective obj = sb.registerNewObjective("health", "dummy");
		obj.setDisplayName(String.valueOf(HealthString.filledHeartIcon));
		obj.setDisplaySlot(DisplaySlot.BELOW_NAME);
		healthScoreboard = sb;
		healthObjective = obj;
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
			updateHealth(event.getPlayer());
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerDamage(EntityDamageEvent event)
	{
		if (!event.isCancelled())
		{
			if (event.getEntity() instanceof Player)
			{
				Player player = (Player) event.getEntity();
				updateHealth(player);
			}
		}
	}

	public static void updateHealth(Player player)
	{
		Score score = healthObjective.getScore(player);
		score.setScore(player.getHealth());
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