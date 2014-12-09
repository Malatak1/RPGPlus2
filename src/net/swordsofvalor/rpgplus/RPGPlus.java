package net.swordsofvalor.rpgplus;

import net.swordsofvalor.rpgplus.abilities.Ability;
import net.swordsofvalor.rpgplus.abilities.abilitieslist.wisdom.FireballAbility;
import net.swordsofvalor.rpgplus.commands.SkillsCommand;
import net.swordsofvalor.rpgplus.database.DatabaseManager;
import net.swordsofvalor.rpgplus.listeners.EntityDamageByEntityListener;
import net.swordsofvalor.rpgplus.listeners.EntityDeathListener;
import net.swordsofvalor.rpgplus.listeners.PlayerJoinListener;
import net.swordsofvalor.rpgplus.listeners.PlayerLevelUpListener;
import net.swordsofvalor.rpgplus.listeners.PlayerQuitListener;
import net.swordsofvalor.rpgplus.runnables.GlobalCooldownManager;
import net.swordsofvalor.rpgplus.runnables.RPGRunnable;
import net.swordsofvalor.rpgplus.runnables.StatsRegenerator;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class RPGPlus extends JavaPlugin {
	
	private static RPGPlus instance;
	private static FileConfiguration config;
	
	@Override
	public void onEnable() {
		instance = this;
		config = this.getConfig();
		
		registerListener(new EntityDamageByEntityListener());
		registerListener(new EntityDeathListener());
		registerListener(new PlayerJoinListener());
		registerListener(new PlayerLevelUpListener());
		registerListener(new PlayerQuitListener());
		
		registerCommand(new SkillsCommand(), "skills");
		
		startRunnableTask(new StatsRegenerator());
		startRunnableTask(new GlobalCooldownManager());
		
		addAbilities(new FireballAbility());
		
		DatabaseManager.openDataBase();
	}
	
	@Override
	public void onDisable() {
		instance = null;
		
		DatabaseManager.closeDataBase();
	}
	
	public static RPGPlus getInstance() {
		return instance;
	}
	
	public static FileConfiguration getSettings() {
		return config;
	}
	
	public void addAbilities(Ability... abilities) {
		for (Ability ability : abilities) {
			Ability.ABILITY_LIST.add(ability);
		}
	}
	
	public void registerCommand(CommandExecutor executor, String command) {
		this.getCommand(command).setExecutor(executor);
	}
	
	public void registerListener(Listener listener) {
		this.getServer().getPluginManager().registerEvents(listener, this);
	}
	
	public void startRunnableTask(RPGRunnable runnable) {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, runnable, 2, runnable.tickRate());
	}
	
}
