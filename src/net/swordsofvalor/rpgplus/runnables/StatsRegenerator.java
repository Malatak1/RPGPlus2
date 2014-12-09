package net.swordsofvalor.rpgplus.runnables;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class StatsRegenerator extends RPGRunnable {
	
	private long c = 0;
	
	public StatsRegenerator() {
		super(2);
	}

	@Override
	public void run() {
		if (c % 4 == 0) regenerateStamina();
		if (c % 8 == 0) regenerateMana();
		c++;
	}
	
	private void regenerateStamina() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			//TODO: implementation
			p.getExp();
		}
	}
	
	private void regenerateMana() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			//TODO: implementation
			p.getLevel();
		}
	}
	
}
