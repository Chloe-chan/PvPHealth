package com.gmail.chloepika.plugins.pvphealth;

public enum LocalMessage
{
	SpoutDetected("spoutDetected"),
	healthHiden("healthHidden"),
	healthAlreadyHidden("healthAlreadyHidden"),
	healthShown("healthShown"),
	healthAlreadyShown("healthAlreadyShown"),
	playerHealthHidden("playerHealthHidden"),
	playerNotOnline("playerNotOnline");

	private String message;

	private LocalMessage(String message)
	{
		this.message = message;
	}

	public String getMessage()
	{
		return message;
	}
}