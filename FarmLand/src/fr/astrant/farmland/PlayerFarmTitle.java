package fr.astrant.farmland;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerFarmTitle {
	PlayerDataBaseMananger playerdata;
	FarmLandDataBaseManager farmlanddata;
	
	public void Playerfarmtitle() {
    	playerdata = new PlayerDataBaseMananger();
    	farmlanddata = new FarmLandDataBaseManager();
    	
    	List<Player> players = new LinkedList<Player>(Bukkit.getServer().getOnlinePlayers());
    	
    	for(int i = 0; i != players.size(); i++) {
        	Player p = players.get(i);
	    	String PlotKey = farmlanddata.getPlotKey(p);
	    	String uuid = p.getUniqueId().toString();
	    	
	    	if(farmlanddata.isPlot(PlotKey)) {
	    		if(playerdata.hasFarm(uuid)) {
	    			String PlayerFarm = playerdata.getFarm(uuid);
	    			String PlotFarm = farmlanddata.getPlotFarm(PlotKey);
	    			
	    			if(farmlanddata.getTitle(PlotFarm)) {
		    			if(!PlotFarm.equals(PlayerFarm)) {
		        			playerdata.sendTitle(p, "§6_oO." + PlotFarm + ".Oo_", "§6_______________________________________________", 1, 1, 1);
		        			playerdata.setFarm(PlotFarm, uuid);
		    			}
	    			}
	    			
	    		}else {
	    			String Farm = farmlanddata.getPlotFarm(PlotKey);
	    			
	    			if(farmlanddata.getTitle(Farm)) {
		    			playerdata.sendTitle(p, "§6_oO." + Farm + ".Oo_", "§6_______________________________________________", 1, 1, 1);
		    			playerdata.setFarm(Farm, uuid);
	    			}

	    		}
	    		
	    	}else {

	    		if(playerdata.hasFarm(uuid)) {
	    			String Farm = playerdata.getFarm(uuid);
	    			if(farmlanddata.getTitle(Farm)) {
		    			playerdata.sendTitle(p, "§7" + Farm, "§7Ooo", 1, 1, 1);
		    			playerdata.setFarm(null, uuid);
	    			}

	    		}
	    		
	    	}
	    	
    	}
    	
	}
	
}
