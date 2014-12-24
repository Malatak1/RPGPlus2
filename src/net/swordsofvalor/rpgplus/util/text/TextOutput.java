package net.swordsofvalor.rpgplus.util.text;

import java.util.ArrayList;
import java.util.List;

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
			target.sendMessage(skillText(data, skill));
		}
	}
	
	public static String skillText(PlayerData data, SkillType skill) {
		SkillManager sm = data.getSkill(skill);
		return ChatColor.translateAlternateColorCodes('%',
				"%e "+ TextUtil.capitalize(skill.name()) + " %a" + sm.getSkillLevel() + " %3XP(%7" + (int) sm.getSkillExperience()
				+ "%3/%7" + LevelIncrements.getIncrementAt(sm.getSkillLevel()) + "%3)");
	}
	
	public static String menuText(PlayerData data, SkillType skill) {
		SkillManager sm = data.getSkill(skill);
		return ChatColor.translateAlternateColorCodes('%',
				"%aLevel " + sm.getSkillLevel() + " %3XP(%7" + (int) sm.getSkillExperience()
				+ "%3/%7" + LevelIncrements.getIncrementAt(sm.getSkillLevel()) + "%3)");
	}
	
	public static String[] abilityInfo(String[] text) {
		List<String> info = new ArrayList<>();
		for (String s : text) {
			if (s.charAt(0) == '@') {
				String[] tmp = s.split(" ");
				String line = "";
				for (int i = 1; i < tmp.length; i++) {
					line += tmp[i];
					if (i+1 < tmp.length) {
						line += " ";
					}
				}
				switch (tmp[0].replace("@", "").toLowerCase()) {
				case "desc": for (String add : seperate(line, 48, "&a&o")) {
					info.add(""+ChatColor.stripColor(add));
				} info.add("");break;
				case "mana": info.add(ChatColor.stripColor("&2" + line + " Mana")); break;
				case "stamina": info.add(ChatColor.stripColor("&2" + line + " Stamina"));  break;
				case "cooldown": info.add(ChatColor.stripColor("&2" + line + " Cooldown"));  break;
				case "type": info.add(ChatColor.stripColor("&9" + line)); break;
				case "note": info.set(info.size()-1, ChatColor.stripColor("&e" + line));info.add(""); break;
				case "space": info.add(""); break;
				case "text": info.add(ChatColor.stripColor(line)); break;
				default: info.add(ChatColor.stripColor("&4Parse Error")); break;
				}
			} else {
				info.add(ChatColor.stripColor(s));
			}
		}
		return info.toArray(new String[info.size()]);
	}
	
	private static String[] seperate(String text, int l, String b) {
		List<String> lines = new ArrayList<>();
		int lineNumber = 0;
		int crm = l;
		for (String s : text.split(" ")) {
			if (lines.size() == lineNumber) {
				lines.add(b);
			}
			if (s.length()+1 >= crm) {
				lines.set(lineNumber, lines.get(lineNumber).trim());
				lineNumber++;
				lines.add(b);
				lines.set(lineNumber, lines.get(lineNumber) + s + " ");
				crm = l - (s.length() + 1);
			} else {
				crm -= (s.length() + 1);
				lines.set(lineNumber, lines.get(lineNumber) + s + " ");
			}
		}
		return lines.toArray(new String[lines.size()]);
	}
	
}
