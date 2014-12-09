package net.swordsofvalor.rpgplus.skills;

import org.bukkit.Bukkit;

import net.swordsofvalor.rpgplus.datatypes.player.PlayerData;
import net.swordsofvalor.rpgplus.datatypes.skills.LevelIncrements;
import net.swordsofvalor.rpgplus.datatypes.skills.SkillType;
import net.swordsofvalor.rpgplus.events.PlayerLevelUpEvent;

public class SkillManager {
	
	protected PlayerData playerData;
	protected SkillType skill;
	
	public SkillManager(PlayerData playerData, SkillType skill) {
		this.playerData = playerData;
		this.skill = skill;
	}
	
	public PlayerData getPlayerData() {
		return playerData;
	}
	
	public SkillType getSkillType() {
		return skill;
	}
	
	public int getSkillLevel() {
		return playerData.getInt("Skills." + skill.name() + ".Level");
	}
	
	public double getSkillExperience() {
		return playerData.getDouble("Skills." + skill.name() + ".Experience");
	}
	
	public void setSkillLevel(int level) {
		playerData.set("Skills." + skill.name() + ".Level", level);
	}
	
	public void setSkillExperience(double experience) {
		playerData.set("Skills." + skill.name() + ".Experience", experience);
	}
	
	public void handleXpGain(double xp) {
		if (getSkillLevel() >= skill.getMaxLevel()) {
			setSkillExperience(0);
			return;
		}
		setSkillExperience(getSkillExperience() + xp);
		if (getSkillExperience() >= LevelIncrements.getIncrementAt(getSkillLevel())) {
			setSkillExperience(getSkillExperience() - LevelIncrements.getIncrementAt(getSkillLevel()));
			setSkillLevel(getSkillLevel() + 1);
			Bukkit.getServer().getPluginManager().callEvent(new PlayerLevelUpEvent(Bukkit.getPlayer(playerData.getPlayer()), this));
			handleXpGain(0);
		}
	}
	
}
