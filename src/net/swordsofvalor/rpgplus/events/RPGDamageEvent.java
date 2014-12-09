package net.swordsofvalor.rpgplus.events;

import net.swordsofvalor.rpgplus.datatypes.damage.DamageType;

import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class RPGDamageEvent extends Event {
	
	private static final HandlerList handlers = new HandlerList();
	
	private double damage;
	private Entity entity;
	private Entity damager;
	private DamageType type;
	
	public RPGDamageEvent(double damage, Entity entity, Entity damager, DamageType type) {
		this.damage = damage;
		this.entity = entity;
		this.damager = damager;
	}
	
	public RPGDamageEvent(EntityDamageByEntityEvent event, DamageType type) {
		this(event.getDamage(), event.getEntity(), event.getDamager(), type);
	}
	
	public RPGDamageEvent(EntityDamageEvent event, DamageType type) {
		this(event.getDamage(), event.getEntity(), null, type);
	}
	
	public double getDamage() {
		return damage;
	}
	
	public Entity getEntity() {
		return entity;
	}
	
	public Entity getDamager() {
		return damager;
	}
	
	public DamageType getDamageType() {
		return type;
	}
	
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}

}
