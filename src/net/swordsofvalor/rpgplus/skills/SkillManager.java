package net.swordsofvalor.rpgplus.skills;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.swordsofvalor.rpgplus.database.DatabaseManager;
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
	
	public SkillManager(Player p, SkillType skill) {
		this(DatabaseManager.getPlayerData(p), skill);
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
	
	public int getSkillPoints() {
		return playerData.getInt("Skills." + skill.name() + ".SkillPoints");	
	}
	
	public void setSkillPoints(int skillPoints) {
		playerData.set("Skills." + skill.name() + ".SkillPoints", skillPoints);
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
			setSkillPoints(getSkillPoints() + 1);
			Bukkit.getServer().getPluginManager().callEvent(new PlayerLevelUpEvent(Bukkit.getPlayer(playerData.getPlayer()), this));
			handleXpGain(0);
		}
	}
	
}
