package net.swordsofvalor.rpgplus.abilities;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import net.swordsofvalor.rpgplus.RPGPlus;
import net.swordsofvalor.rpgplus.datatypes.abilities.AbilityType;
import net.swordsofvalor.rpgplus.datatypes.player.PlayerData;
import net.swordsofvalor.rpgplus.datatypes.skills.SkillType;
import net.swordsofvalor.rpgplus.util.player.PlayerUtil;

@SuppressWarnings("deprecation")
public class AbilitiesManager implements Listener {
	
	public final Map<Ability, Integer> ABILITY_MAP = new HashMap<>();
	public final Ability[] EQUIPPED_ABILITIES = new Ability[4];
	
	private Player player;
	private PlayerData playerData;
	
	public AbilitiesManager(Player player, PlayerData playerData) {
		this.player = player;
		this.playerData = playerData;
		loadAbilitiesFromFile();
		RPGPlus.getInstance().registerListener(this);
	}
	
	public void closeAbilities() {
		saveAbilitiesToFile();
		HandlerList.unregisterAll(this);	
	}
	
	private void loadAbilitiesFromFile() {
		for (SkillType section : SkillType.values()) {
			if (!playerData.contains("Skills." + section.name() + ".Abilities")) playerData.createSection("Skills." + section.name() + ".Abilities");
			for (String ability : playerData.getConfigurationSection("Skills." + section.name() + ".Abilities").getKeys(false)) {
				ABILITY_MAP.put(Ability.getAbilityByName(ability), playerData.getInt("Skills." + section.name() + ".Abilities." + ability));
			}
		}
		if (!playerData.contains("ActiveAbilities")) {
			playerData.createSection("ActiveAbilities");
			for (String type : AbilityType.values()) {
				playerData.set("ActiveAbilities." + type.toLowerCase(), "empty");
			}
		}
		for (String type : AbilityType.values()) {
			String ability = playerData.getString("ActiveAbilities." + type.toLowerCase());
			if (!ability.equals("empty")) {
				EQUIPPED_ABILITIES[AbilityType.convert(type)] = Ability.getAbilityByName(ability);
			}
		}
	}
	
	private void saveAbilitiesToFile() {
		Iterator<Entry<Ability, Integer>> it = ABILITY_MAP.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<Ability, Integer> pairs = (Map.Entry<Ability, Integer>) it.next();
			Ability ability = pairs.getKey();
			if (ability != null)
				playerData.set("Skills." + ability.getSkillType().name() + ".Abilities." + ability.getName().toLowerCase(), pairs.getValue());
			it.remove();
		}
		for (String type : AbilityType.values()) {
			Ability ability = EQUIPPED_ABILITIES[AbilityType.convert(type)];
			if (ability != null) {
				playerData.set("ActiveAbilities." + type.toLowerCase(), ability.getName());
			} else {
				playerData.set("ActiveAbilities." + type.toLowerCase(), "empty");
			}
		}
	}
	
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if (event.getDamager().equals(player)) {
			if (!player.isSneaking()) {
				Ability ability = EQUIPPED_ABILITIES[AbilityType.LIGHT];
				if (ability.getSkillType().isClassItem(player.getItemInHand())) {
					if (canCast(player, ability)) {
						boolean success = ability.onPlayerDamageEntity(event, player, 1);
						if (success) handleCast(player, ability);
					}
				}
			}
			else {
				Ability ability = EQUIPPED_ABILITIES[AbilityType.HEAVY];
				if (ability.getSkillType().isClassItem(player.getItemInHand())) {
					if (canCast(player, ability)) {
						boolean success = ability.onPlayerDamageEntity(event, player, 1);
						if (success) handleCast(player, ability);
					}
				}
			}
		}
		if (event.getEntity().equals(player)) {
			if (player.isBlocking()) {
				if (!player.isSneaking()) {
					Ability ability = EQUIPPED_ABILITIES[AbilityType.MEDIUM];
					if (canCast(player, ability)) {
						boolean success = ability.onPlayerBlockEntity(event, player, 1);
						if (success) handleCast(player, ability);
					}
				} else {
					Ability ability = EQUIPPED_ABILITIES[AbilityType.ULTIMATE];
					if (canCast(player, ability)) {
						boolean success = ability.onPlayerBlockEntity(event, player, 1);
						if (success) handleCast(player, ability);
					}
				}
			}			
		}
	}
	
	@EventHandler
	public void onProjecitleLaunch(ProjectileLaunchEvent event) {
		if (event.getEntity() instanceof Arrow) {
			if (event.getEntity().getShooter().equals(player)) {
				if (!player.isSneaking()) {
					Ability ability = EQUIPPED_ABILITIES[AbilityType.MEDIUM];
					if (canCast(player, ability)) {
						boolean success = ability.onArrowShoot(event, player, 1);
						if (success) handleCast(player, ability);
					}
				} else {
					Ability ability = EQUIPPED_ABILITIES[AbilityType.ULTIMATE];
					if (canCast(player, ability)) {
						boolean success = ability.onArrowShoot(event, player, 1);
						if (success) handleCast(player, ability);
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onProjectileHit(ProjectileHitEvent event) {
		if (event.getEntity() instanceof Arrow) {
			if (event.getEntity().getShooter().equals(player)) {
				if (event.getEntity().hasMetadata("ability." + EQUIPPED_ABILITIES[AbilityType.MEDIUM].getName())) {
					Ability ability = EQUIPPED_ABILITIES[AbilityType.MEDIUM];
					if (ability != null) ability.onArrowHit(event, player, 1);
				}
				if (event.getEntity().hasMetadata("ability." + EQUIPPED_ABILITIES[AbilityType.ULTIMATE].getName())) {
					Ability ability = EQUIPPED_ABILITIES[AbilityType.ULTIMATE];
					if (ability != null) ability.onArrowHit(event, player, 1);
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.getPlayer().equals(player)) {
			if (!player.isSneaking()) {
				if (event.getAction().equals(Action.LEFT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
					if (EQUIPPED_ABILITIES[AbilityType.LIGHT] != null)
					if (EQUIPPED_ABILITIES[AbilityType.LIGHT].getSkillType().isClassItem(event.getPlayer().getItemInHand())) {
						Ability ability = EQUIPPED_ABILITIES[AbilityType.LIGHT];
						if (canCast(player, ability)) {
							boolean success = ability.onClassItemInteract(event, player, 1);
							if (success) handleCast(player, ability);
						}
					}
				} else if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
					if (EQUIPPED_ABILITIES[AbilityType.MEDIUM] != null)
					if (EQUIPPED_ABILITIES[AbilityType.MEDIUM].getSkillType().isClassItem(event.getPlayer().getItemInHand())) {
						Ability ability = EQUIPPED_ABILITIES[AbilityType.MEDIUM];
						if (canCast(player, ability)) {
							boolean success = ability.onClassItemInteract(event, player, 1);
							if (success) handleCast(player, ability);
						}
					}
				}
			} else {
				if (event.getAction().equals(Action.LEFT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
					if (EQUIPPED_ABILITIES[AbilityType.HEAVY] != null)
					if (EQUIPPED_ABILITIES[AbilityType.HEAVY].getSkillType().isClassItem(event.getPlayer().getItemInHand())) {
						Ability ability = EQUIPPED_ABILITIES[AbilityType.HEAVY];
						if (canCast(player, ability)) {
							boolean success = ability.onClassItemInteract(event, player, 1);
							if (success) handleCast(player, ability);
						}
					}
				} else if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
					if (EQUIPPED_ABILITIES[AbilityType.ULTIMATE] != null)
					if (EQUIPPED_ABILITIES[AbilityType.ULTIMATE].getSkillType().isClassItem(event.getPlayer().getItemInHand())) {
						Ability ability = EQUIPPED_ABILITIES[AbilityType.ULTIMATE];
						if (canCast(player, ability)) {
							boolean success = ability.onClassItemInteract(event, player, 1);
							if (success) handleCast(player, ability);
						}
					}
				}
			}
		}
	}
	
	private boolean canCast(Player p, Ability ability) {
		if (ability == null) return false;
		if (PlayerUtil.isOnGlobalCooldown(p)) return false;
		else return true;
	}
	
	private void handleCast(Player p, Ability ability) {
		
	}
	
}
