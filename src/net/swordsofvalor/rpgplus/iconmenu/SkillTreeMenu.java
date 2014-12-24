package net.swordsofvalor.rpgplus.iconmenu;

import net.swordsofvalor.rpgplus.abilities.AbilitiesManager;
import net.swordsofvalor.rpgplus.abilities.Ability;
import net.swordsofvalor.rpgplus.abilities.SkillTree;
import net.swordsofvalor.rpgplus.skills.SkillManager;
import net.swordsofvalor.rpgplus.util.player.PlayerUtil;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SkillTreeMenu extends IconMenu {

	public SkillTreeMenu(Player p, final SkillTree tree) {
		super(p, tree.getName() + " Skills", 45, new OptionClickEventHandler() {
			@Override
			public void onOptionClick(OptionClickEvent event) {
				if (event.getPosition() == 0) {
					new MainMenu(event.getPlayer()).open();
				} else if (event.getPosition() / 9 > 0 && event.getPosition() % 9 <= 3) {
					SkillManager sm = new SkillManager(event.getPlayer(), tree.getSkillType());
					AbilitiesManager am = PlayerUtil.getAbilities(event.getPlayer());
					Ability sel = Ability.getAbilityByName(event.getName());
					if (am.getAbilityLevel(sel) < 3 && sm.getSkillPoints() > 0) {
						am.setAbilityLevel(sel, am.getAbilityLevel(sel) + 1);
						sm.setSkillPoints(sm.getSkillPoints() - 1);
					}
					new SkillTreeMenu(event.getPlayer(), tree).open();
				} else if (event.getPosition() == 7 || event.getPosition() == 8) {
					new SkillTreeMenu(event.getPlayer(), adjacentTrees(tree)[event.getPosition() - 7]).open();
				}
				else {
					event.getPlayer().sendMessage(event.getName());
					new MainMenu(event.getPlayer()).open();
				}
			}
		});
		setOption(0, new ItemStack(Material.NETHER_STAR), "@tMain Menu", "@sReturn to Main Menu");
		Ability[][] abilities = tree.getAbilities();
		for (int row = 0; row < abilities.length; row++) {
			for (int col = 0; col < abilities[row].length; col++) {
				if (abilities[row][col] != null)
				setOption(((row + 1) *9) + col, PlayerUtil.getAbilities(p).getFormattedIcon(abilities[row][col]));
			}
		}
		SkillTree[] adjacentTrees = adjacentTrees(tree);
		for (int i = 0; i < adjacentTrees.length; i++) {
			setOption(7 + i, adjacentTrees[i].getFormattedIcon());
		}
	}
	
	private static SkillTree[] adjacentTrees(SkillTree tree) {
		SkillTree[] adj = new SkillTree[2];
		for (SkillTree st : SkillTree.values()) {
			if (st.getSkillType() == tree.getSkillType() && st != tree) {
				adj[adj[0] == null ? 0 : 1] = st;
			}
		}
		return adj;
	}
	
}
