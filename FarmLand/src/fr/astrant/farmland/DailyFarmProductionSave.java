package fr.astrant.farmland;

import java.util.List;

public class DailyFarmProductionSave {
	FarmLandDataBaseManager data;
	ConfigManager config;
	
	public void Dailyfarmproductionsave() {
		data = new FarmLandDataBaseManager();
		config = new ConfigManager();
		
		try {
			List<String> Drops = data.getDropsNames();
			int DropSize = Drops.size();
			
			for(int f = 0; f != DropSize; f++) {
				String Drop = Drops.get(f);
				
				if(data.isDropNumber(Drop)) {
					int DaySave = config.getFarmProductionDaySave();
					for(int i = DaySave-1; i != 0; i--) {
						
						try {
							
							if(i == 0) {
								data.setDropNumber(Drop, 0, 1);
								
							}else {
								data.setDropNumber(Drop, data.getDropNumber(Drop, i-1), i);

							}
							
						}catch(Exception e) {}

					}
					
				}
				
			}
			
		}catch(Exception ex) {}
		
	}

}
