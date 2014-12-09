package net.swordsofvalor.rpgplus.util.player;

import net.swordsofvalor.rpgplus.database.DatabaseManager;

import org.bukkit.entity.Player;

public final class PlayerUtil {
	
	public static int globalCooldown(Player p) {
		return DatabaseManager.getPlayerData(p).GLOBAL_COOLDOWN;
	}
	
	public static void setGlobalCooldown(Player p, int n) {
		DatabaseManager.getPlayerData(p).GLOBAL_COOLDOWN = n;
	}
	
	public static boolean isOnGlobalCooldown(Player p) {
		return globalCooldown(p) > 0;
	}
	
}
