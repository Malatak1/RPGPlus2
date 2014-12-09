package net.swordsofvalor.rpgplus.commands;

import net.swordsofvalor.rpgplus.database.DatabaseManager;
import net.swordsofvalor.rpgplus.util.text.TextOutput;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SkillsCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("skills")) {
			if (sender instanceof Player) {
				TextOutput.outputSkills(sender, DatabaseManager.PLAYER_MAP.get(sender.getName()));
				return true;
			}
			return false;
		}
		return false;
	}
	
}
