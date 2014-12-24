package net.swordsofvalor.rpgplus.util.player;

import net.swordsofvalor.rpgplus.abilities.AbilitiesManager;
import net.swordsofvalor.rpgplus.database.DatabaseManager;

import org.bukkit.entity.Player;

public final class PlayerUtil {
	
	public static AbilitiesManager getAbilities(Player p) {
		return DatabaseManager.getPlayerData(p).getAbilities();
	}
	
	public static int globalCooldown(Player p) {
		return DatabaseManager.getPlayerData(p).GLOBAL_COOLDOWN;
	}
	
	public static void setGlobalCooldown(Player p, int n) {
		DatabaseManager.getPlayerData(p).GLOBAL_COOLDOWN = n;
	}
	
	public static boolean isOnGlobalCooldown(Player p) {
		return globalCooldown(p) > 0;
	}
	
	public static int getMana(Player p) {
		return p.getLevel();
	}
	
	public static void setMana(Player p, int mana) {
		if (mana < getMaxMana(p))
			p.setLevel(mana);
		else
			p.setLevel(getMaxMana(p));
	}
	
	public static int getMaxMana(Player p) {
		return 100;
	}
	
	public static float getStamina(Player p) {
		return p.getExp();
	}
	
	public static void setStamina(Player p, float stamina) {
		if (stamina < 1.0)
			p.setExp(stamina);
		else
			p.setExp(0.9999F);
	}
	
}
