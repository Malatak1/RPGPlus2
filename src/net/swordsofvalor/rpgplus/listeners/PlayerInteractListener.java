package net.swordsofvalor.rpgplus.listeners;

import net.swordsofvalor.rpgplus.iconmenu.MainMenu;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractListener implements Listener {
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (rightClicked(event)) {
			if (event.getPlayer().getItemInHand().getType() == Material.NETHER_STAR) {
				new MainMenu(event.getPlayer()).open();
			}
		}
		if (leftClicked(event)) {
			
		}
	}
	
	private boolean rightClicked(PlayerInteractEvent event) {
		return event.getAction().equals(Action.RIGHT_CLICK_AIR) ||
				event.getAction().equals(Action.RIGHT_CLICK_BLOCK);
	}
	
	private boolean leftClicked(PlayerInteractEvent event) {
		return event.getAction().equals(Action.LEFT_CLICK_AIR) ||
				event.getAction().equals(Action.LEFT_CLICK_BLOCK);
	}
	
}
