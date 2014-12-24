package net.swordsofvalor.rpgplus.datatypes.entity;

import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;

public class Collision {
	
	private Block b;
	private LivingEntity e;
	
	public Collision() {}
	
	public Collision(Block b) {
		this.b = b;
	}
	
	public Collision(LivingEntity e) {
		this.e = e;
	}
	
	public boolean hasBlock() {
		return b != null;
	}
	
	public Block getBlock() {
		return b;
	}
	
	public boolean hasEntity() {
		return e != null;
	}
	
	public LivingEntity getEntity() {
		return e;
	}
	
	public boolean isCollision() {
		return hasBlock() || hasEntity();
	}
	
}
