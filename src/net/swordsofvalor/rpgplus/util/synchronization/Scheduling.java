package net.swordsofvalor.rpgplus.util.synchronization;

import net.swordsofvalor.rpgplus.RPGPlus;

import org.bukkit.Bukkit;

public final class Scheduling {
	
	public static void runTaskLater(Runnable task, long delay) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(RPGPlus.getInstance(), task, delay);
	}
	
	public static void runTaskRepeating(Runnable task, long delay, long period) {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(RPGPlus.getInstance(), task, delay, period);
	}
	
}
