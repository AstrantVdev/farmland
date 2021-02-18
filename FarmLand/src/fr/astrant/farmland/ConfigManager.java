package fr.astrant.farmland;

import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {
	Main main;
	FileConfiguration config = Main.Config;
	
	public ConfigManager(){
		this.main = Main.getInstance();
	}
	
	public void save() {
		main.saveDefaultConfig();
	}
	
	public int getFarmProductionDaySave() {
		return config.getInt("FarmProductionDaySave");
	}
	
	public String getError(String ErrorKey) {
		return config.getString("Errors." + ErrorKey);
	}
	
	public Double getMaxRarity() {
		return config.getDouble("MaxRarity");
	}
	
	public int getDailyFarmDropLosePercentage() {
		return config.getInt("DailyFarmDropLosePercentage");
	}
	
	public int getTeleportCooldown() {
		return config.getInt("TeleportCooldow");
	}

}
