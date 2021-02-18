package fr.astrant.farmland;

import java.util.List;

public class DailyFarmDropLose {
	ConfigManager config;
	FarmLandDataBaseManager data;
	
	public void Dailyfarmlose() {
		config = new ConfigManager();
		data = new FarmLandDataBaseManager();
		
		try {
			int Percentage = config.getDailyFarmDropLosePercentage();
			List<String> Farms = data.getFarms();
			int FarmSize = Farms.size();
			
			if(!(Percentage > 0) || !(Percentage <=100)) {
				Percentage = 10;
			}
			
			for(int f = 0; f != FarmSize; f++) {
				String Farm = Farms.get(f);
				List<String> FarmDrops = data.getFarmDropsProduction(Farm);
				int FarmDropSize = FarmDrops.size();
				
				try {

					for ( int d = 0; d != FarmDropSize; d++) {
						String Drop = FarmDrops.get(d);
						Double Number = data.getFarmDropProduction(Farm, Drop);
						Double NewNumber = Double.valueOf(Math.round((Number * Percentage) / 100)) - 1;
						data.setFarmDropProduction(Farm, Drop, NewNumber);	
						
					}
					
				}catch(Exception ex) {}

				
			}
			
		}catch(Exception ex) {}
		
	}

}
