package net.swordsofvalor.rpgplus.runnables;

import net.swordsofvalor.rpgplus.util.player.PlayerUtil;

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
			PlayerUtil.setStamina(p, PlayerUtil.getStamina(p) + 0.01F);
		}
	}
	
	private void regenerateMana() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			PlayerUtil.setMana(p, PlayerUtil.getMana(p) + 1);
		}
	}
	
}
