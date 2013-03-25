package com.gmail.chloepika.plugins.pvphealth;

import org.bukkit.ChatColor;

public enum LocalMessage
{
	SpoutDetected("spoutDetected"),
	healthHidden("healthHidden"),
	healthAlreadyHidden("healthAlreadyHidden"),
	healthShown("healthShown"),
	healthAlreadyShown("healthAlreadyShown"),
	playerHealthHidden("playerHealthHidden"),
	playerNotOnline("playerNotOnline"),
	noPerm("noPerm");

	private String message;

	private LocalMessage(String message)
	{
		this.message = message;
	}

	public String getMessage()
	{
		return message;
	}

	public String getLocalisedMessage()
	{
		return ChatColor.translateAlternateColorCodes('&', Local.getString(this));
	}
}