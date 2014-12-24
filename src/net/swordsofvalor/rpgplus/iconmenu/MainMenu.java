package net.swordsofvalor.rpgplus.iconmenu;

import net.swordsofvalor.rpgplus.abilities.SkillTree;
import net.swordsofvalor.rpgplus.database.DatabaseManager;
import net.swordsofvalor.rpgplus.datatypes.abilities.AbilityType;
import net.swordsofvalor.rpgplus.datatypes.player.PlayerData;
import net.swordsofvalor.rpgplus.datatypes.skills.SkillType;
import net.swordsofvalor.rpgplus.skills.SkillManager;
import net.swordsofvalor.rpgplus.util.text.TextOutput;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MainMenu extends IconMenu {

	public MainMenu(Player p) {
		super(p, "Main Menu", 18, new OptionClickEventHandler() {
			@Override
			public void onOptionClick(OptionClickEvent event) {
				switch (event.getPosition()) {
				case 0:
					new AbilitySelectMenu(event.getPlayer(), AbilityType.LIGHT).open();
					break;
				case 1:
					new SkillTreeMenu(event.getPlayer(), SkillTree.WARRIOR).open();
					break;
				case 2:
					new SkillTreeMenu(event.getPlayer(), SkillTree.ROGUE).open();
					break;
				case 3:
					new SkillTreeMenu(event.getPlayer(), SkillTree.ELEMENTALIST).open();
					break;
				default:
					event.getMenu().open();
				}
			}
		});
		PlayerData pd = DatabaseManager.getPlayerData(p);
		setOption(0, new ItemStack(Material.EMERALD), "@tAbility Selection", "@sInterface for selecting known abilities");
		for (int i = 0; i < SkillType.values().length; i++) {
			addSkillTree(i + 1, SkillType.values()[i], pd);
		}
	}
	
	private void addSkillTree(int pos, SkillType type, PlayerData pd) {
		SkillManager sm = new SkillManager(pd, type);
		if (sm.getSkillPoints() == 0) {
			setOption(pos, new ItemStack(type.getIcon(), pd.getInt("Skills." + type.name() + ".Level")),
					"@t" + type.getName(), TextOutput.menuText(pd, type),"","@eClick to open SkillTree");
		} else {
			setOption(pos, new ItemStack(type.getIcon(), pd.getInt("Skills." + type.name() + ".Level")),
					"@t" + type.getName(), TextOutput.menuText(pd, type), "" , "@e" + sm.getSkillPoints() +
					" SkillPoints remaining","@eClick to open SkillTree");
		}
	}
	
}
