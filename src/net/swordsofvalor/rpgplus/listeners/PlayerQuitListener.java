package net.swordsofvalor.rpgplus.listeners;

import java.io.IOException;

import net.swordsofvalor.rpgplus.database.DatabaseManager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {
	
	@EventHandler(priority=EventPriority.LOW)
	public void onPlayerQuit(PlayerQuitEvent event) {
		try {
			DatabaseManager.savePlayer(event.getPlayer());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
