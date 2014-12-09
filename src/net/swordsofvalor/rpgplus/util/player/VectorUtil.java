package net.swordsofvalor.rpgplus.util.player;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public final class VectorUtil {
	
	public static Location getEyeLocationWithOffset(Player p) {
		return getEyeLocationWithOffset(p, 2);
	}
	
	public static Location getEyeLocationWithOffset(Player p, float offset) {
		return p.getEyeLocation().toVector().add(p.getLocation().getDirection().multiply(offset)).toLocation(p.getWorld(), p.getLocation().getYaw(), p.getLocation().getPitch());
	}
	
}
