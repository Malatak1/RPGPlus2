package net.swordsofvalor.rpgplus.datatypes.skills;

import net.swordsofvalor.rpgplus.util.text.TextUtil;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum SkillType {
	
	STRENGTH(80, Color.RED, Material.IRON_SWORD),
	DEXTERITY(80, Color.GREEN, Material.BOW),
	WISDOM(80, Color.BLUE, Material.BOOK),
	CONSTITUTION(80, Color.YELLOW, Material.APPLE);
	
	private int maxLevel;
	private Color fireworkColor;
	private Material icon;
	
	private SkillType(int maxLevel, Color fireworkColor, Material icon) {
		this.maxLevel = maxLevel;
		this.fireworkColor = fireworkColor;
		this.icon = icon;
	}
	
	public int getMaxLevel() {
		return maxLevel;
	}
	
	public Color getLevelUpColor() {
		return fireworkColor;
	}
	
	public String getName() {
		return TextUtil.capitalize(name());
	}
	
	public Material getIcon() {
		return icon;
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
