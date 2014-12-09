package net.swordsofvalor.rpgplus.runnables;

import org.bukkit.scheduler.BukkitRunnable;

public abstract class RPGRunnable extends BukkitRunnable {
	
	private int tickRate;
	
	public RPGRunnable(int tickRate) {
		this.tickRate = tickRate;
	}
	
	public int tickRate() {
		return tickRate;
	}
	
}
