package net.swordsofvalor.rpgplus.datatypes.skills;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum SkillType {
	
	STRENGTH(80, Color.RED),
	DEXTERITY(80, Color.GREEN),
	WISDOM(80, Color.BLUE),
	CONSTITUTION(80, Color.YELLOW);
	
	private int maxLevel;
	private Color fireworkColor;
	
	private SkillType(int maxLevel, Color fireworkColor) {
		this.maxLevel = maxLevel;
		this.fireworkColor = fireworkColor;
	}
	
	public int getMaxLevel() {
		return maxLevel;
	}
	
	public Color getLevelUpColor() {
		return fireworkColor;
	}
	
	public boolean isClassItem(ItemStack item) {
		switch (this) {
		case STRENGTH:
			switch (item.getType().name().split("_")[item.getType().name().split("_").length - 1]) {
			case "AXE":
			case "SWORD":
			case "SPADE":
			case "PICKAXE":
				return true;
			default:
			}
			break;
		case DEXTERITY:
			switch (item.getType()) {
			case BOW:
			case SHEARS:
				return true;
			default:
			}
			break;
		case WISDOM:
			if (item.getType() == Material.STICK) return true;
		default:
		}
		return false;
	}
	
}
