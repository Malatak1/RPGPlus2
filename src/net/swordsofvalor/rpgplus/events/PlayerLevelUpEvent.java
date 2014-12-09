package net.swordsofvalor.rpgplus.events;

import net.swordsofvalor.rpgplus.skills.SkillManager;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerLevelUpEvent extends Event {
	
    private static final HandlerList handlers = new HandlerList();
    
    private Player p;
    private SkillManager skill;
    
    public PlayerLevelUpEvent(Player p, SkillManager manager) {
    	this.p = p;
    	this.skill = manager;
    }
    
    public Player getPlayer() {
    	return p;
    }
    
    public SkillManager getSkill() {
    	return skill;
    }
    
    public HandlerList getHandlers() {
        return handlers;
    }
    
    public static HandlerList getHandlerList() {
    	return handlers;
    }

}
