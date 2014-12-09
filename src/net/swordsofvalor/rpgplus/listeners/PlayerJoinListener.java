package net.swordsofvalor.rpgplus.listeners;

import net.swordsofvalor.rpgplus.database.DatabaseManager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

	@EventHandler(priority=EventPriority.LOW)
	public void onPlayerJoin(PlayerJoinEvent event) {
		DatabaseManager.loadPlayer(event.getPlayer());
	}
	
}
