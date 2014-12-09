package net.swordsofvalor.rpgplus.database;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import net.swordsofvalor.rpgplus.RPGPlus;
import net.swordsofvalor.rpgplus.datatypes.player.PlayerData;

public final class DatabaseManager {

	public static final Map<String, PlayerData> PLAYER_MAP = new HashMap<>();
	
	private static FileConfiguration config = RPGPlus.getSettings();
	private static File datafolder;
	
	public static void openDataBase() {
		Bukkit.getLogger().info("Opening Database");
		if (config.contains("Database")) {
			datafolder = new File(config.getString("Database"));
		} else {
			StringBuilder path = new StringBuilder(RPGPlus.getInstance().getDataFolder().getAbsolutePath());
			path.append(File.separator + "PlayerData");
			datafolder = new File(path.toString());
			config.set("Database", datafolder.toString());
			try {
				config.save(RPGPlus.getInstance().getDataFolder().getAbsolutePath() + File.separator + "config.yml");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (!datafolder.exists()) {
			datafolder.mkdir();
		}
		for (Player p : Bukkit.getOnlinePlayers()) {
			loadPlayer(p);
		}
	}
	
	public static void closeDataBase() {
		Bukkit.getLogger().info("Closing Database");
		for (Player p : Bukkit.getOnlinePlayers()) {
			try {
				savePlayer(p);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static File getPlayerFile(UUID id) {
		File file = new File(datafolder.getAbsolutePath() + File.separator + id.toString() + ".yml");
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return file;
	}
	
	public static PlayerData getPlayerData(Player p) {
		return PLAYER_MAP.get(p.getName());
	}
	
	public static void loadPlayer(Player p) {
		PLAYER_MAP.put(p.getName(), new PlayerData(p, getPlayerFile(p.getUniqueId())));
	}
	
	public static void savePlayer(Player p) throws IOException {
		PLAYER_MAP.get(p.getName()).getAbilities().closeAbilities();
		PLAYER_MAP.get(p.getName()).save(getPlayerFile(p.getUniqueId()));
		PLAYER_MAP.remove(p.getName());
	}
	
}
