package fr.astrant.farmland;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class ErrorManager {
	ConfigManager config;
	
	public void ErrorSender(Player p, String ErrorKey) {
		config = new ConfigManager();
		
		String Error = config.getError(ErrorKey);
		Location loc = p.getLocation();
		
		p.sendMessage("§c§lHey!§r§7 " + Error);
		p.playSound(loc, Sound.ENTITY_VILLAGER_NO, 10, 10);
	}
	
}
