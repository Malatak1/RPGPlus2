package net.swordsofvalor.rpgplus.abilities;

import java.util.ArrayList;
import java.util.List;

import net.swordsofvalor.rpgplus.datatypes.skills.SkillType;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public abstract class Ability {
	
	public static final List<Ability> ABILITY_LIST = new ArrayList<>();
	
	protected String name;
	protected String[] description;
	protected ItemStack icon;
	protected SkillType skillType;
	protected int abilityType;
	
	protected int cooldownTime = 0;
	protected int manaCost = 0;
	protected int staminaCost = 0;
	
	public Ability(String name, SkillType type, int abilityType, ItemStack icon, String... description) {
		this.name = name;
		this.skillType = type;
		this.icon = icon;
		this.abilityType = abilityType;
		this.description = description;
	}
	
	public static Ability getAbilityByName(String name) {
		for (Ability ability : ABILITY_LIST) {
			if (ability.getName().equalsIgnoreCase(name)) return ability;
		}
		return null;
	}
	
	public String getName() {
		return name;
	}
	
	public SkillType getSkillType() {
		return skillType;
	}
	
	public int getAbilityType() {
		return abilityType;
	}
	
	public String[] getInfo() {
		return description;
	}
	
	public ItemStack getIcon() {
		return icon;
	}
	
	public boolean onPlayerDamageEntity(EntityDamageByEntityEvent event, Player p, float power) {
		return false;
	}
	public boolean onPlayerBlockEntity(EntityDamageByEntityEvent event, Player p, float power) {
		return false;
	}
	public boolean onClassItemInteract(PlayerInteractEvent event, Player p, float power) {
		return false;
	}
	public boolean onArrowShoot(ProjectileLaunchEvent event, Player p, float power) {
		return false;
	}
	public void onArrowHit(ProjectileHitEvent event, Player p, float power) {}
	
	public int getCooldownTime() {
		return cooldownTime;
	}
	public int getManaCost() {
		return manaCost;
	}
	public int getStaminaCost() {
		return staminaCost;
	}
	
}
