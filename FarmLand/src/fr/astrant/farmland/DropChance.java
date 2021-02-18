package fr.astrant.farmland;

import java.util.LinkedList;
import java.util.List;

public class DropChance {
	FarmLandDataBaseManager data;
	
	public String DropChoose(String Farm, String Block, String Tool) {
		data = new FarmLandDataBaseManager();
		
		List<String> Drops = data.getBlockDrops(Farm, Block, Tool);
		List<String> DropsChance = new LinkedList<String>();
        for(int i = 0; i != Drops.size(); i++) {
        	DropsChance.add(String.valueOf(data.getBlockDropChance(Farm, Block, Tool, Drops.get(i))));
        }
        
        int Max = 0;
        int newMax = 0;
        for (int i = 0; i != Drops.size(); i++) {
            Max += Integer.valueOf(DropsChance.get(i));
        }
        
        final Double random = Math.random() * (Max + 1.0);
        for (int i = 0; i != Drops.size(); i++) {
            newMax += Integer.valueOf(DropsChance.get(i));
            if (random < newMax) {
                return Drops.get(i);
            }
        }
        return Drops.get(0);	
        
	}

}
