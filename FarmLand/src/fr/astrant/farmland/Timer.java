package fr.astrant.farmland;

import java.util.List;

import org.bukkit.Bukkit;

public class Timer {
	TimerDataBaseManager timerdata;
	BlockReplace blockreplace;
	DailyFarmProductionSave dailyfarmprodsave;
	ConfigManager config;
	DailyFarmDropLose dailyfarmdroplose;
	PlayerFarmTitle playerfarmtitle;
	
	public void Timerchrono(Main main) {
		timerdata = new TimerDataBaseManager();
        Bukkit.getScheduler().scheduleSyncRepeatingTask(main, (Runnable)new Runnable() {
            @Override
            public void run() {
				List<String> Timers = timerdata.getTimers();
        		
        		for(int i = 0; i != Timers.size(); i++) {
        			String Timer = Timers.get(i);
        			int Second = timerdata.getTimer(Timer);
        			
            		CheckTimer(Timer, Second);

        			if(Second == 0) {
        				timerdata.removeTimer(Timer);

        			}else {
        				timerdata.settimer(Timer, Second - 1);
            			
        			}
        			
        		}
            	
            }
            
        }, 0L, 20L);
        
	}
	
	public void CheckTimer(String Timer, int Second) {
		timerdata = new TimerDataBaseManager();
		
		String[] Elements = Timer.split("/");

		CheckDaily(Timer, Second, Elements);
		CheckFarmTitle(Timer, Second, Elements);
	}
	
	private void CheckFarmTitle(String Timer, int Second, String[] Elements) {
		timerdata = new TimerDataBaseManager();
		
		if(Elements[0].equals("FarmTitle")) {
			
            if(Second == 0) {
            	timerdata.addtimer("FarmTitle", 100);
            }
   
		}
		
	}
	
	private void CheckDaily(String Timer, int Second, String[] Elements) {
		timerdata = new TimerDataBaseManager();
		dailyfarmprodsave = new DailyFarmProductionSave();
		dailyfarmdroplose = new DailyFarmDropLose();
		
		if(Elements[0].equals("DailyFarmProductionSave")) {
			
            if(Second == 0) {
            	timerdata.addtimer("DailyFarmProductionSave", 86400);
            	
            	dailyfarmprodsave.Dailyfarmproductionsave();
            	
            	dailyfarmdroplose.Dailyfarmlose();
            }
   
		}
		
	}
	
	public void setMainTimers() {
		timerdata = new TimerDataBaseManager();
		
		if(!timerdata.TimerExist("FarmTitle")) {
	    	timerdata.addtimer("FarmTitle", 100);

		}
		
		if(!timerdata.TimerExist("DailyFarmProductionSave")) {
	    	timerdata.addtimer("DailyFarmProductionSave", 86400);

		}

	}
	
}
