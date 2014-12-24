package net.swordsofvalor.rpgplus.abilities;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import net.swordsofvalor.rpgplus.datatypes.skills.SkillType;
import net.swordsofvalor.rpgplus.iconmenu.IconMenu;
import net.swordsofvalor.rpgplus.util.text.TextUtil;

public enum SkillTree {
	
	WARRIOR(SkillType.STRENGTH, (byte)9),
	PALADIN(SkillType.STRENGTH, (byte)0),
	BERSERKER(SkillType.STRENGTH, (byte)14),
	ROGUE(SkillType.DEXTERITY, (byte)2),
	RANGER(SkillType.DEXTERITY, (byte)5),
	ASSASSIN(SkillType.DEXTERITY, (byte)15),
	ELEMENTALIST(SkillType.WISDOM, (byte)11),
	SHAMAN(SkillType.WISDOM, (byte)13),
	ARCHANIST(SkillType.WISDOM, (byte)10);
	
	private SkillType type;
	private Ability[][] abilities;
	private ItemStack icon;
	
	private SkillTree(SkillType type, byte icon) {
		this.type = type;
		this.abilities = new Ability[4][4];
		this.icon = new ItemStack(Material.STAINED_GLASS_PANE, 1, icon);
	}
	
	public static SkillTree fromString(String s) {
		for (SkillTree tree : values()) {
			if (s.equalsIgnoreCase(tree.name())) {
				return tree;
			}
		}
		return null;
	}
	
	public void addAbility(Ability ability) {
		for (int i = 0; i < 4; i++) {
			if (abilities[ability.getAbilityType()][i] == null) {
				abilities[ability.getAbilityType()][i] = ability;
				return;
			}
		}
	}
	
	public ItemStack getIcon() {
		return icon;
	}
	
	public ItemStack getFormattedIcon() {
		ItemStack icon = new ItemStack(this.icon);
		return IconMenu.setItemNameAndLore(icon, IconMenu.formatText("@t" + this.getName()),
				new String[]{IconMenu.formatText("@sClick to view abilities")});
	}
	
	public String getName() {
		return TextUtil.capitalize(name());
	}
	
	public SkillType getSkillType() {
		return type;
	}
	
	public Ability[][] getAbilities() {
		return abilities;
	}
	
	public List<Ability> getAbilityList() {
		List<Ability> abilities = new ArrayList<>();
		for (Ability[] row : this.abilities) {
			for (Ability ability : row) abilities.add(ability);
		}
		return abilities;
	}
	
	
}
