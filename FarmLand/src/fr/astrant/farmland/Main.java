package fr.astrant.farmland;
import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.economy.Economy;

public class Main extends JavaPlugin{
    static Plugin plugin;
    public static Economy economy;
    public static YamlConfiguration FarmLandconfig;
    public static File FarmLandData;
    public static YamlConfiguration Timerconfig;
    public static File TimerData;
    public static YamlConfiguration Playerconfig;
    public static File PlayerData;
    public static YamlConfiguration MinTimerconfig;
    public static File MinTimerData;
    public static FileConfiguration Config;
    MinTimer mintimer;
    Timer timer;
    ConfigManager config;
    GuiManager gui;
    private static Main instance;

    public Main() {
        instance = this;
    }

    public static Main getInstance() {
        return instance;
    }
    
    public void onEnable() {
    	mintimer = new MinTimer();
    	gui = new GuiManager();
        timer = new Timer();
        config = new ConfigManager();
        this.setupEconomy();
                 
        FarmLandData = new File(this.getDataFolder(), "FarmLandData.yml");
        FarmLandconfig = YamlConfiguration.loadConfiguration(Main.FarmLandData);
        TimerData  = new File(this.getDataFolder(), "TimerData.yml");
        Timerconfig = YamlConfiguration.loadConfiguration(Main.TimerData);
        PlayerData  = new File(this.getDataFolder(), "PlayerData.yml");
        Playerconfig = YamlConfiguration.loadConfiguration(Main.PlayerData);
        MinTimerData  = new File(this.getDataFolder(), "MinTimerData.yml");
        MinTimerconfig = YamlConfiguration.loadConfiguration(Main.MinTimerData);
        
        getCommand("farmland").setExecutor(new FarmlandCommand(this));
        getCommand("farmland").setTabCompleter(new FarmlandTabCompleter());
        
        this.getServer().getPluginManager().registerEvents(new FarmingListener(), this);
        this.getServer().getPluginManager().registerEvents(new GriefListener(), this);
        this.getServer().getPluginManager().registerEvents(new FarmerClicManager(), this);
        this.getServer().getPluginManager().registerEvents(new GUIEvent(), this);

        if (!Main.PlayerData.exists()) {
            try {
                Main.PlayerData.createNewFile();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        if (!Main.FarmLandData.exists()) {
            try {
                Main.FarmLandData.createNewFile();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        if (!Main.TimerData.exists()) {
            try {
                Main.TimerData.createNewFile();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        if (!Main.MinTimerData.exists()) {
            try {
                Main.MinTimerData.createNewFile();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        Config = this.getConfig();
        this.loadConfiguration();
        saveDefaultConfig();
        
        timer.setMainTimers();
        timer.Timerchrono(this);
        mintimer.MinTimerchrono(this);
        
        gui.InventoryAnimation(this);
        
        getLogger().info("\u001B[32m=============================");
        getLogger().info("\u001B[36mFarmLand!");
        getLogger().info("\u001B[36mEnablingOoo");
        getLogger().info("\u001B[32m=============================");
    }
    
    public void OnDisable() {
    	getLogger().info("\u001B[32m=============================");
    	getLogger().info("\u001B[36mFarmLand!");
    	getLogger().info("\u001B[36mDisablingOoo");
    	getLogger().info("\u001B[32m=============================");
    }
    
    public void loadConfiguration() {
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
    }
    
    public void Reload() {
    	config.save();
    	this.reloadConfig();
    	
        FarmLandconfig = YamlConfiguration.loadConfiguration(FarmLandData);
        Playerconfig = YamlConfiguration.loadConfiguration(PlayerData);
        Timerconfig = YamlConfiguration.loadConfiguration(TimerData);
        MinTimerconfig = YamlConfiguration.loadConfiguration(MinTimerData);
    }
    
    private boolean setupEconomy() {
        final RegisteredServiceProvider<Economy> economyProvider = (RegisteredServiceProvider<Economy>)this.getServer().getServicesManager().getRegistration((Class<Economy>)Economy.class);
        if (economyProvider != null) {
            Main.economy = economyProvider.getProvider();
        }
        return Main.economy != null;
    }
    
}
