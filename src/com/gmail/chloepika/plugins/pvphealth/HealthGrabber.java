package com.gmail.chloepika.plugins.pvphealth;

import org.bukkit.entity.Player;

public class HealthGrabber implements Runnable
{
	private Player attacker;
	private Player victim;
	private int victimOriginalHealth;

	public HealthGrabber(Player attacker, Player victim)
	{
		this.attacker = attacker;
		this.victim = victim;
		this.victimOriginalHealth = victim.getHealth();
	}

	public void run()
	{
		int damageDone = (victimOriginalHealth - victim.getHealth());
		if (victim.getHealth() < 4 || damageDone > 6 || !StopSpam.havePause(attacker, victim))
		{
			attacker.sendMessage(HealthString.getFinalString(attacker, victim, damageDone));
			StopSpam.addToList(attacker, victim);
		}
	}
}