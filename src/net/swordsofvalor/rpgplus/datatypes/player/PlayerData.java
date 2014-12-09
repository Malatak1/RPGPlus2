package net.swordsofvalor.rpgplus.datatypes.player;

import java.io.File;
import java.io.IOException;

import net.swordsofvalor.rpgplus.abilities.AbilitiesManager;
import net.swordsofvalor.rpgplus.datatypes.skills.SkillType;
import net.swordsofvalor.rpgplus.skills.SkillManager;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class PlayerData extends YamlConfiguration {
	
	public int GLOBAL_COOLDOWN;
	
	private AbilitiesManager abilities;
	
	public PlayerData(Player p, File f) {
		try {
			this.load(f);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
		this.set("Name", p.getName());
		if (!this.contains("Skills")) this.createSection("Skills");
		for (SkillType skill : SkillType.values()) {
			if (!this.contains("Skills." + skill.name())) {
				this.createSection("Skills." + skill.name());
				this.set("Skills." + skill.name() + ".Level", 1);
				this.set("Skills." + skill.name() + ".Experience", 0.0);
			}
		}
		abilities = new AbilitiesManager(p, this);
	}
	
	public AbilitiesManager getAbilities() {
		return abilities;
	}
	
	public SkillManager getSkill(SkillType skill) {
		return new SkillManager(this, skill);
	}
	
	public String getPlayer() {
		return this.getString("Name");
	}
	
}
