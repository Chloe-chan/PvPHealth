package com.gmail.chloepika.plugins.pvphealth;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class PvPHealth extends JavaPlugin implements Listener
{
	public static final String
	invalidmessage = (ChatColor.RED + "You have entered an invalid "),
	pluginPrefix = (ChatColor.BLUE + "[" + ChatColor.GOLD + "PvP Health" + ChatColor.BLUE + "] " + ChatColor.RESET),
	pluginPrefixNC = ("[PvP Health] ");

	public void onEnable()
	{
		saveDefaultConfig();
		Local.setLocale("en");
		Local.reloadConfig();
		HideHealth.readHidden();
		getServer().getPluginManager().registerEvents(this, this);
		if (Bukkit.getServer().getPluginManager().getPlugin("Spout") != null)
		{
			Bukkit.getServer().getLogger().log(Level.WARNING, (pluginPrefixNC + Local.getString(LocalMessage.SpoutDetected)));
		}
	}

	public void onDisable()
	{
		HideHealth.saveHidden();
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		if (command.getName().equalsIgnoreCase("health"))
		{
			if (args.length == 0)
			{
				sender.sendMessage(help(sender));
				return true;
			}
			if (args.length == 1)
			{
				if (args[0].equalsIgnoreCase("help"))
				{
					sender.sendMessage(help(sender));
					return true;
				}
				if (args[0].equalsIgnoreCase("hide") || args[0].equalsIgnoreCase("unhide"))
				{
					if (sender instanceof Player)
					{
						Player player = (Player) sender;
						if (player.hasPermission("pvphealth.hide"))
						{
							if (args[0].equalsIgnoreCase("hide"))
							{
								if (HideHealth.isHidden(player))
								{
									player.sendMessage(ChatColor.RED + "Your health is already hidden.");
								} else
								{
									HideHealth.hideHealth(player);
									player.sendMessage(ChatColor.RED + "Your health is now hidden.");
								}
							}
							if (args[0].equalsIgnoreCase("unhide"))
							{
								if (HideHealth.isHidden(player))
								{
									HideHealth.unhideHealth(player);
									player.sendMessage(ChatColor.RED + "Your health is now unhidden.");
								} else
								{
									player.sendMessage(ChatColor.RED + "Your health is already unhidden.");
								}
							}
						} else
						{
							sender.sendMessage(Messages.noperm.getMessage());
						}
						return true;
					}
				}
			}
			if (args.length == 2)
			{
				if (args[0].equalsIgnoreCase("get"))
				{
					if (sender instanceof Player)
					{
						Player player = (Player) sender;
						if (player.hasPermission("pvphealth.query"))
						{
							Player target = Bukkit.getServer().getPlayer(args[1]);
							if (target != null)
							{
								if (!HideHealth.isHidden(target))
								{
									int playerHealth = target.getHealth();
									String healthString = (pluginPrefix + ChatColor.AQUA + target.getName() + ChatColor.RED + "'s health : " + ChatColor.GOLD + HealthString.getPreferredHealthString(playerHealth, target));
									player.sendMessage(healthString);
								} else
								{
									player.sendMessage(ChatColor.RED + "Sorry, " + ChatColor.AQUA + target.getName() + ChatColor.RED + " have hid his/her health.");
								}
							} else
							{
								player.sendMessage(ChatColor.AQUA + args[1] + ChatColor.RED + " is not online.");
							}
							return true;
						} else
						{
							sender.sendMessage(Messages.noperm.getMessage());
						}
					} else
					{
						Player target = Bukkit.getServer().getPlayer(args[1]);
						if (target != null)
						{
							int playerHealth = target.getHealth();
							String healthString = HealthString.getPreferredHealthString(playerHealth, target);
							sender.sendMessage(healthString);
						} else
						{
							sender.sendMessage(ChatColor.AQUA + args[1] + ChatColor.RED + " is not online.");
						}
						return true;
					}
				}
			}
			if (args.length > 2)
			{
				sender.sendMessage(error(Messages.manytarget, "/health help"));
				return true;
			}
		}
		return false;
	}

	public void callScheduler(Player attacker, Player victim)
	{
		callScheduler(this, attacker, victim);
	}

	public static void callScheduler(Plugin plugin, Player attacker, Player victim)
	{
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new HealthGrabber(attacker, victim), 1);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerReceiveDamage(EntityDamageByEntityEvent event)
	{
		if (event.getDamage() > 0)
		{
			if (!event.isCancelled())
			{
				if (event.getEntity() instanceof Player)
				{
					Player victim = (Player) event.getEntity();
					if (event.getDamager() instanceof Player)
					{
						Player attacker = (Player) event.getDamager();
						callScheduler(attacker, victim);
					}
					if (event.getDamager() instanceof Projectile)
					{
						Projectile projectile = (Projectile) event.getDamager();
						LivingEntity shooter = projectile.getShooter();
						if (shooter instanceof Player)
						{
							Player attacker = (Player) shooter;
							callScheduler(attacker, victim);
						}
					}
					if (event.getDamager() instanceof Wolf)
					{
						Wolf wolf = (Wolf) event.getDamager();
						AnimalTamer owner = wolf.getOwner();
						if (owner instanceof Player)
						{
							Player attacker = (Player) owner;
							callScheduler(attacker, victim);
						}
					}
				}
			}
		}
	}



	public String[] error(Messages reason, String usage)
	{
		String[] string = {
				reason.getMessage(),
				(ChatColor.RED + "Correct usage:"),
				(ChatColor.AQUA + usage)};
		return string;
	}

	public String helpMaker(String command, String description)
	{
		return (ChatColor.AQUA + "/" + command + ChatColor.GOLD + " : " + ChatColor.RED + description);
	}

	public String[] help(CommandSender sender)
	{
		List<String> help = new ArrayList<String>();
		help.add(ChatColor.RED + "==========" + ChatColor.AQUA + "PvP Health Help" + ChatColor.RED + "==========");
		help.add(helpMaker("health help", "Displays this help page."));
		if (sender instanceof Player)
		{
			Player player = (Player) sender;
			if (player.hasPermission("pvphealth.query"))
			{
				help.add(helpMaker("health get <player>", "Displays the health of the player."));
			}
			if (player.hasPermission("pvphealth.hide"))
			{
				help.add(helpMaker("health hide", "Hides your health from others."));
				help.add(helpMaker("health unhide", "Unhides your health from others."));
			}
		} else
		{
			help.add(helpMaker("health get <player>", "Displays the health of the player."));
		}
		help.add(ChatColor.RED + "=================================");
		String[] helpArray = help.toArray(new String[help.size()]);
		return helpArray;
	}
}