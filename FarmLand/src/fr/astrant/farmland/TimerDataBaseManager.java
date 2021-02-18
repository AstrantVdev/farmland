package fr.astrant.farmland;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;

public class TimerDataBaseManager {
	private YamlConfiguration config = Main.Timerconfig;
    
    public void addtimer(String Name, int Second) {
    	config.set("Timers." + Name, Second);
    	save();
    }
    
    public void settimer(String Name, int Second) {
    	config.set("Timers." + Name, Second);
    	save();
    }
    
    public void removeTimer(String Name) {
    	Main.Timerconfig.set("Timers." + Name, null);
    	save();
    }
    
    public int getTimer(String Name) {
    	return config.getInt("Timers." + Name);
    }
    
    public List<String> getTimers() {
		return new ArrayList<>(config.getConfigurationSection("Timers").getKeys(false));
    }
        
    public boolean TimerExist (String Timer) {
    	try {
        	String Second = config.getString("Timers." + Timer);

    		if(!Second.equals(null)) {
    			return true;
    			
    		}
    		return false;
    	}catch(Exception ex) {
    		return false;
    		
    	}
    }
    
    private void save() {
        try {
        	config.save(Main.TimerData);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
