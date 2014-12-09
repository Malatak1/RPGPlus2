package net.swordsofvalor.rpgplus.util.text;

import net.swordsofvalor.rpgplus.datatypes.player.PlayerData;
import net.swordsofvalor.rpgplus.datatypes.skills.LevelIncrements;
import net.swordsofvalor.rpgplus.datatypes.skills.SkillType;
import net.swordsofvalor.rpgplus.skills.SkillManager;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public final class TextOutput {

	public static void outputSkills(CommandSender target, PlayerData data) {
		target.sendMessage(ChatColor.translateAlternateColorCodes('%', "%6SKILLS"));
		for (SkillType skill : SkillType.values()) {
			SkillManager sm = data.getSkill(skill);
			target.sendMessage(ChatColor.translateAlternateColorCodes('%',
					"%e "+ TextUtil.capitalize(skill.name()) + " %a" + sm.getSkillLevel() + " %3XP(%7" + (int) sm.getSkillExperience()
					+ "%3/%7" + LevelIncrements.getIncrementAt(sm.getSkillLevel()) + "%3)"));
		}
	}
	
}
