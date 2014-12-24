package net.swordsofvalor.rpgplus.iconmenu;

import java.util.List;

import net.swordsofvalor.rpgplus.abilities.Ability;
import net.swordsofvalor.rpgplus.datatypes.abilities.AbilityType;
import net.swordsofvalor.rpgplus.util.player.PlayerUtil;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class AbilitySelectMenu extends IconMenu {

	public AbilitySelectMenu(Player p, final int abilityType) {
		super(p, "Ability Selection", 36, new OptionClickEventHandler() {
			@Override
			public void onOptionClick(OptionClickEvent event) {
				if (event.getPosition() == 0) {
					new MainMenu(event.getPlayer()).open();
				} else if (event.getPosition() > 4 && event.getPosition() < 9) {
					new AbilitySelectMenu(event.getPlayer(), event.getPosition() - 5).open();
				} else if (event.getPosition() > 8) {
					Ability[] equipped = PlayerUtil.getAbilities(event.getPlayer()).EQUIPPED_ABILITIES;
					Ability a = Ability.getAbilityByName(event.getName());
					equipped[a.getAbilityType()] = a;
				} else {
					new AbilitySelectMenu(event.getPlayer(), abilityType).open();
				}
			}
		});
		setOption(0, new ItemStack(Material.NETHER_STAR), "@tMain Menu", "@sReturn to Main Menu");
		for (int type = AbilityType.LIGHT; type <= AbilityType.ULTIMATE; type++) {
			setOption(type + 5, new ItemStack(abilityType != type ? Material.PAPER : Material.EMPTY_MAP), "@t" + AbilityType.name(type), "@sSelect other abilities");
		}
		List<Ability> abilities = Ability.getAbilitiesOfType(abilityType);
		for (int i = 0; i < abilities.size(); i++) {
			setOption(i + 9, PlayerUtil.getAbilities(p).getFormattedIcon(abilities.get(i), "@eClick to select"));
		}
	}
	
	
	
}
