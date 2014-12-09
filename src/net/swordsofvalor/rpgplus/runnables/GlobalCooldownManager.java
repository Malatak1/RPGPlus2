package net.swordsofvalor.rpgplus.runnables;

import net.swordsofvalor.rpgplus.database.DatabaseManager;
import net.swordsofvalor.rpgplus.util.player.PlayerUtil;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class GlobalCooldownManager extends RPGRunnable {

	public GlobalCooldownManager() {
		super(1);
	}

	@Override
	public void run() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (PlayerUtil.globalCooldown(p) > 0) {
				DatabaseManager.getPlayerData(p).GLOBAL_COOLDOWN--;
			}
		}
	}

}
