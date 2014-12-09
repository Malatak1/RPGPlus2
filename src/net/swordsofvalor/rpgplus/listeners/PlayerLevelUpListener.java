package net.swordsofvalor.rpgplus.listeners;

import net.swordsofvalor.rpgplus.RPGPlus;
import net.swordsofvalor.rpgplus.events.PlayerLevelUpEvent;
import net.swordsofvalor.rpgplus.util.text.TextUtil;
import net.swordsofvalor.rpgplus.util.visuals.FireworkEffectPlayer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Sound;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerLevelUpListener implements Listener {
	
	@EventHandler
	public void onPlayerLevelUpEvent(final PlayerLevelUpEvent event) {
		final Player p = event.getPlayer();
		
		for (int i = 0; i < 3 + (event.getSkill().getSkillLevel() / 5); i++) {
			Bukkit.getScheduler().scheduleSyncDelayedTask(RPGPlus.getInstance(), new Runnable() {
				@Override
				public void run() {
					Color color = event.getSkill().getSkillType().getLevelUpColor();
					try {
						FireworkEffectPlayer.playFirework(p.getWorld(), p.getLocation().add(0, 4, 0), FireworkEffect.builder().with(Type.BURST).withColor(color).build());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}, (long) (10 * (i + Math.random())));
		}
		
		p.getWorld().playSound(p.getLocation(), Sound.LEVEL_UP, 2, 1);
		p.getWorld().playSound(p.getLocation(), Sound.FIREWORK_LAUNCH, 1, 1);
		
		p.sendMessage(ChatColor.AQUA + "You have reached level " + event.getSkill().getSkillLevel()
				+ " in " + TextUtil.capitalize(event.getSkill().getSkillType().name()) + "!");
		for (Entity nearby : p.getNearbyEntities(30.0, 15.0, 30.0)) {
			if (nearby instanceof Player) {
				((Player) nearby).sendMessage(ChatColor.AQUA + p.getName() + " has reached level " + event.getSkill().getSkillLevel()
						+ " in " + TextUtil.capitalize(event.getSkill().getSkillType().name()) + "!");
			}
		}
	}
	
}
