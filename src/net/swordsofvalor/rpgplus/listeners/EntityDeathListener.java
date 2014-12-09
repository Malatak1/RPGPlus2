package net.swordsofvalor.rpgplus.listeners;

import net.swordsofvalor.rpgplus.database.DatabaseManager;
import net.swordsofvalor.rpgplus.datatypes.skills.SkillType;

import org.bukkit.Material;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class EntityDeathListener implements Listener {
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onEntityDeath(EntityDeathEvent event) {
		event.setDroppedExp(0);
		if (event.getEntity().getKiller() != null) {
			Player p = event.getEntity().getKiller();
			if (!p.hasMetadata("NPC")) {
				if (event.getEntity().getLastDamageCause().getCause().equals(DamageCause.PROJECTILE)) {
					int xp = 1;
					if (event.getEntity() instanceof Monster) xp = 7;
					DatabaseManager.getPlayerData(p).getSkill(SkillType.DEXTERITY).handleXpGain(xp);
				} else if (event.getEntity().getLastDamageCause().getCause().equals(DamageCause.ENTITY_ATTACK)) {
					int xp = 1;
					if (event.getEntity() instanceof Monster) xp = 4;
					if (p.getItemInHand().getType().equals(Material.SHEARS)) {
						DatabaseManager.getPlayerData(p).getSkill(SkillType.DEXTERITY).handleXpGain(xp);
					} else {
						DatabaseManager.getPlayerData(p).getSkill(SkillType.STRENGTH).handleXpGain(xp);
					}
				}
			}
		}
	}
	
}
