package net.swordsofvalor.rpgplus.abilities.abilitieslist.wisdom;

import java.util.Arrays;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitRunnable;

import net.swordsofvalor.rpgplus.abilities.Ability;
import net.swordsofvalor.rpgplus.datatypes.abilities.AbilityType;
import net.swordsofvalor.rpgplus.datatypes.skills.SkillType;
import net.swordsofvalor.rpgplus.util.player.PlayerUtil;
import net.swordsofvalor.rpgplus.util.player.VectorUtil;
import net.swordsofvalor.rpgplus.util.synchronization.Scheduling;

public class FireballAbility extends Ability {
	
	public FireballAbility() {
		super("Fireball", SkillType.WISDOM, AbilityType.MEDIUM, new ItemStack(Material.FIREBALL),
				"(desc)",
				"(more)");
		this.manaCost = 10;
		this.cooldownTime = 6;
	}
	
	@Override
	public boolean onClassItemInteract(PlayerInteractEvent event, final Player p, final float power) {
		PlayerUtil.setGlobalCooldown(p, 12);
		p.getWorld().playEffect(p.getEyeLocation(), Effect.BLAZE_SHOOT, 5);
		for (int i = 0; i < 12; i++) {
			Scheduling.runTaskLater(new Runnable() {
				@Override
				public void run() {
					Location loc = VectorUtil.getEyeLocationWithOffset(p);
					p.getWorld().playEffect(loc, Effect.SMOKE, 2);
				}
			}, i); 
		}
		
		Scheduling.runTaskLater(new Runnable() {
			@Override
			public void run() {
				Location loc = VectorUtil.getEyeLocationWithOffset(p);
				final Fireball fireball = p.getWorld().spawn(loc, Fireball.class);
					fireball.setYield((power + 3) / 3);
					fireball.setShooter((ProjectileSource) p);
				Scheduling.runTaskRepeating(new BukkitRunnable() {
					private int c = 0;
					@Override
					public void run() {
						fireball.getWorld().playEffect(fireball.getLocation(), Effect.MOBSPAWNER_FLAMES, 5);
						fireball.getWorld().playEffect(fireball.getLocation(), Effect.SMOKE, 5);
						if (c > 300) this.cancel();
						c++;
					}
				}, 2, 5);
				for (Effect e : Arrays.asList(Effect.SMOKE, Effect.MOBSPAWNER_FLAMES, Effect.BLAZE_SHOOT)) {
					p.getWorld().playEffect(loc, e, 5);
				}
			}
		}, 10); 
		return true;
	}
	
}
