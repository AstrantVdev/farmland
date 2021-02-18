package fr.astrant.farmland;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

public class MinTimerDataBaseManager {
	private YamlConfiguration config = Main.MinTimerconfig;
    
    public void addTimerValue(int Minute, String Name) {
    	if(isTimerValues(Minute)) {
    		List<String> Values = getTimerValues(Minute);
    		Values.add(Name);
        	config.set("MinTimers." + Minute, Values);

    	}else {
        	config.set("MinTimers." + Minute, Name);
    	}
    	save();
    }
    
    public void setTimerValues(int Minute, List<String> Values) {
    	config.set("MinTimers." + Minute, Values);
    	save();
    }
    
    public List<Integer> getTimers(){
    	List<Integer> list = new LinkedList<Integer>();
    	List<String> timers = new LinkedList<String>(config.getConfigurationSection("MinTimers").getKeys(false));
    	
    	for(int i = 0; i != timers.size(); i++) {
    		list.add(Integer.valueOf(timers.get(i)));
    	}
    	
    	Collections.sort(list);
    	return list;
    }
    
    public void removeTimer(int Minute) {
    	config.set("MinTimers." + Minute, null);
    	save();
    }
    
    public List<String> getTimerValues (int Minute){
    	return config.getStringList("MinTimers." + Minute);
    }
    
    public boolean isTimers() {
    	try {
    		if(!getTimers().equals(null)) {
    			return true;
    		}
    		return false;
    	}catch(Exception ex) {
    		return false;
    	}
    }
    
    public boolean isTimerValues(int Minute) {
    	try {
    		if(!getTimerValues(Minute).equals(null)) {
    			return true;
    		}
    		return false;
    	}catch(Exception ex) {
    		return false;
    	}
    }
    
    public String getBlockReplaceKey(Location loc, String type) {
		int X = (int) loc.getX();
		int Y = (int) loc.getY();
		int Z = loc.getBlockZ();
		World world = loc.getWorld();
		
		return "BlockReplace/" + world.getName().replace(".", "#") + "/" + X + "/" + Y + "/" + Z + "/" + type;
    }

    private void save() {
        try {
        	config.save(Main.MinTimerData);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
