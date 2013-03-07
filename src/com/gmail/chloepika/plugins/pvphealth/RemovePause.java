package com.gmail.chloepika.plugins.pvphealth;

public class RemovePause implements Runnable
{
	private String combinedName;

	public RemovePause(String combinedName)
	{
		this.combinedName = combinedName;
	}

	public void run()
	{
		StopSpam.removeFromList(combinedName);
	}
}