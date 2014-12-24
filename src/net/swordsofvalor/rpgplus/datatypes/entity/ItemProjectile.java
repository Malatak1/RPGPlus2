package net.swordsofvalor.rpgplus.datatypes.entity;

import net.swordsofvalor.rpgplus.RPGPlus;
import net.swordsofvalor.rpgplus.util.player.PlayerCheck;

import org.bukkit.Location;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public abstract class ItemProjectile {
	
	private static long n = 0;
	
	protected ItemStack item;
	protected boolean removed;
	
	public ItemProjectile(ItemStack item) {
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(nextName());
		item.setItemMeta(meta);
		this.item = item;
	}
	
	public abstract void onCollision(Item thrown, Collision c, int ticksLived);
	public void onUpdate(Item thrown, int ticksLived) {}
	
	public void shoot(final LivingEntity shooter, Location loc, Vector velocity) {
		final Item thrown = loc.getWorld().dropItem(loc, item);
		thrown.setPickupDelay(Integer.MAX_VALUE);
		thrown.setVelocity(velocity);
		new BukkitRunnable() {
			private int ticksLived = 0;
			@Override
			public void run() {
				if (removed) {
					this.cancel();
					thrown.remove();
					return;
				}
				for (Entity e : thrown.getNearbyEntities(1, 1, 1)) {
					if (e instanceof Damageable && !shooter.equals(e)) {
						if (shooter instanceof Player ? PlayerCheck.canDamage((Player)shooter, (Damageable)e) : true) {
							onCollision(thrown, new Collision((LivingEntity) e), ticksLived);
							if (removed) return;
						}
					}
				}
				if (thrown.getLocation().add(thrown.getVelocity()).getBlock().getType().isSolid() || thrown.isOnGround()) {
					onCollision(thrown, new Collision(thrown.getLocation().add(thrown.getVelocity()).getBlock()), ticksLived);
					if (removed) return;
				}
				onUpdate(thrown, ticksLived);
			}
		}.runTaskTimer(RPGPlus.getInstance(), 1, 1);
	}
	
	
	
	private static String nextName() {
		n++; return "" + n;
	}
	
}
