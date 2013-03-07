package com.gmail.chloepika.plugins.pvphealth;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Scanner;

import org.bukkit.entity.Player;

public class HideHealth
{
	private static List<String> hiddenHealth = new ArrayList<String>();

	public static void hideHealth(Player player)
	{
		String playerName = player.getName();
		if (!hiddenHealth.contains(playerName))
		{
			hiddenHealth.add(playerName);
		}
	}

	public static void unhideHealth(Player player)
	{
		String playerName = player.getName();
		hiddenHealth.remove(playerName);
	}

	public static boolean isHidden(Player player)
	{
		String playerName = player.getName();
		return hiddenHealth.contains(playerName);
	}

	private static File getFolder()
	{
		File pluginFolder = new File("plugins");
		File modFolder = new File(pluginFolder, "PvP Health");
		File saveFolder = new File(modFolder, "Save files");
		saveFolder.mkdirs();
		return saveFolder;
	}

	private static File getHiddenFile(boolean createFile)
	{
		File originalFolder = getFolder();
		File saveHiddenFile = new File(originalFolder, "Hidden Health.txt");
		if (!saveHiddenFile.exists())
		{
			if (createFile)
			{
				try
				{
					saveHiddenFile.createNewFile();
					return saveHiddenFile;
				} catch (IOException e)
				{
					e.printStackTrace();
					return null;
				}
			}
			{
				return null;
			}
		} else
		{
			return saveHiddenFile;
		}
	}

	public static void saveHidden()
	{
		List<String> data = hiddenHealth;
		File hiddenFile = getHiddenFile(true);
		Formatter formatter;
		try{
			formatter = new Formatter(hiddenFile);
			for(String name : data)
			{
				formatter.format("%s" +
						"%n", name);
			}
			formatter.close();
		}
		catch(FileNotFoundException ex)
		{
			ex.printStackTrace();
		}
	}

	public static void readHidden()
	{
		File hiddenFile = getHiddenFile(false);
		if (hiddenFile != null)
		{
			List<String> data = new ArrayList<String>();
			Scanner scanner;
			try{
				scanner = new Scanner(hiddenFile);
				while(scanner.hasNextLine())
				{
					String string = scanner.nextLine();
					data.add(string);
				}
				scanner.close();
				HideHealth.hiddenHealth = data;
			}
			catch(FileNotFoundException ex)
			{
				ex.printStackTrace();
			}
		}
	}
}