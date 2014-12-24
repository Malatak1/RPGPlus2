package net.swordsofvalor.rpgplus.util.synchronization;

import net.swordsofvalor.rpgplus.RPGPlus;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public final class Scheduling {
	
	public static void runTaskLater(Runnable task, long delay) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(RPGPlus.getInstance(), task, delay);
	}
	
	public static void runTaskRepeating(BukkitRunnable task, long delay, long period) {
		task.runTaskTimer(RPGPlus.getInstance(), delay, period);
	}
	
}
