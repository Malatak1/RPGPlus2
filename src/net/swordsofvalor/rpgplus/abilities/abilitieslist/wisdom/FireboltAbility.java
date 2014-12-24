package net.swordsofvalor.rpgplus.abilities.abilitieslist.wisdom;

import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Material;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import net.swordsofvalor.rpgplus.abilities.Ability;
import net.swordsofvalor.rpgplus.abilities.SkillTree;
import net.swordsofvalor.rpgplus.datatypes.abilities.AbilityType;
import net.swordsofvalor.rpgplus.datatypes.entity.Collision;
import net.swordsofvalor.rpgplus.datatypes.entity.ItemProjectile;
import net.swordsofvalor.rpgplus.util.player.PlayerUtil;
import net.swordsofvalor.rpgplus.util.player.VectorUtil;
import net.swordsofvalor.rpgplus.util.visuals.FireworkEffectPlayer;

public class FireboltAbility extends Ability {

	public FireboltAbility() {
		super("Firebolt", SkillTree.ELEMENTALIST, AbilityType.LIGHT, new ItemStack(Material.BLAZE_ROD),
				"@desc Shoots a bolt of fire in a target direction. Upon collision, the firebolt"
				+ " will deal damage and set target entity ablaze. Velocity and overall range scale"
				+ " with power.",
				"@mana 2",
				"@type Projectile");
		this.manaCost = 2;
	}
	
	@Override
	public boolean onClassItemInteract(PlayerInteractEvent event, final Player p, final float power) {
		PlayerUtil.setGlobalCooldown(p, 5);
		new ItemProjectile(new ItemStack(Material.FIRE)) {
			@Override
			public void onCollision(Item thrown, Collision c, int ticksLived) {
				this.removed = true;
				FireworkEffectPlayer.playFirework(thrown.getWorld(), thrown.getLocation(),
						FireworkEffect.builder().withColor(Color.RED).with(Type.BURST).build());
				if (c.isCollision()) {
					if (c.hasEntity()) {
						c.getEntity().setFireTicks(100);
						if (c.getEntity() instanceof Damageable) {
							((Damageable)c.getEntity()).damage(power);
						}
					}
				}
			}
			@Override
			public void onUpdate(Item thrown, int ticksLived) {
				thrown.getWorld().playEffect(thrown.getLocation(), Effect.MOBSPAWNER_FLAMES, 2);
				if (ticksLived > 20) onCollision(thrown, new Collision(), ticksLived);
			}
		}.shoot(p, VectorUtil.getEyeLocationWithOffset(p), p.getLocation().getDirection().normalize().multiply(2));
		return true;
	}
	
}
