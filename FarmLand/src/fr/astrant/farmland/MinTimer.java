package fr.astrant.farmland;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class MinTimer {
	MinTimerDataBaseManager mintimerdata;
	BlockReplace blockreplace;
	DailyFarmProductionSave dailyfarmprodsave;
	ConfigManager config;
	DailyFarmDropLose dailyfarmdroplose;
	PlayerFarmTitle playerfarmtitle;
	
	public void MinTimerchrono(Main main) {
		mintimerdata = new MinTimerDataBaseManager();
        Bukkit.getScheduler().scheduleSyncRepeatingTask(main, (Runnable)new Runnable() {
            @Override
            public void run() {
            	
            	if(mintimerdata.isTimers()) {
    				List<Integer> Timers = mintimerdata.getTimers();
            		
            		for(int i = 0; i != Timers.size(); i++) {
            			int Minute = Integer.valueOf(Timers.get(i));
        				List<String> Values = mintimerdata.getTimerValues(Minute);

            			if(Minute == 0) {
            				mintimerdata.removeTimer(Minute);
            				BlocksReplace(Values);
            			}else {
            				mintimerdata.removeTimer(Minute);
            				mintimerdata.setTimerValues(Minute - 1, Values);
                			
            			}
            			
            		}
            		
            	}
            	
            }
            
        }, 0L, 20*60L);
        
	}
	
	private void BlocksReplace(List<String> Values) {
		blockreplace = new BlockReplace();

		for(int i = 0; i != Values.size(); i++) {
			String Value = Values.get(i);
			String[] Elements = Value.split("/");
			
			if(Elements[0].equals("BlockReplace")) {
				World world  = Bukkit.getWorld(Elements[1].replace("#", "."));
				int X = Integer.valueOf(Elements[2]);
				int Y = Integer.valueOf(Elements[3]);
				int Z = Integer.valueOf(Elements[4]);
				String Block = Elements[5];

				Location loc = world.getSpawnLocation(); 
				loc.setWorld(world);
				loc.setX(X);
				loc.setY(Y);
				loc.setZ(Z);
				blockreplace.Blockreplace(loc, Block);
	   
			}
			
		}
		
	}

}
