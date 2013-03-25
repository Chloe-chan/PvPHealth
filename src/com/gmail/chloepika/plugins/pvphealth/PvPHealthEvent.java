package com.gmail.chloepika.plugins.pvphealth;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PvPHealthEvent extends Event
{
	private Player victim;
	private int maxHealth;

	private static final HandlerList handlers = new HandlerList();

	public PvPHealthEvent(Player victim, int maxHealth)
	{
		this.victim = victim;
		this.maxHealth = maxHealth;
	}

	public HandlerList getHandlers()
	{
		return handlers;
	}

	public static HandlerList getHandlerList()
	{
		return handlers;
	}

	public Player getVictim()
	{
		return victim;
	}

	public int getPlayerMaxHealth()
	{
		return maxHealth;
	}

	public void setPlayerMaxHealth(int health)
	{
		maxHealth = health;
	}
}