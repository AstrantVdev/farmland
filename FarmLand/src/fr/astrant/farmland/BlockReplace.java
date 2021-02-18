package fr.astrant.farmland;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;

public class BlockReplace {
	FarmLandDataBaseManager farmlanddata;
	
	public List<Material> crops = Arrays.asList(Material.WHEAT, Material.CARROT, Material.BEETROOT, Material.POTATO, Material.SWEET_BERRY_BUSH);

	public void Blockreplace(Location loc, String Type) {
		farmlanddata = new FarmLandDataBaseManager();
		
		Chunk chunck  = loc.getChunk();
		String PlotKey = farmlanddata.getPlotKey(chunck);
		if(farmlanddata.isPlot(PlotKey)) {
			String Farm = farmlanddata.getPlotFarm(PlotKey);
			Particle particle = farmlanddata.getBlockParticle(Farm, Type);
			
			if(crops.contains(Material.valueOf(Type))) {
				Block block = loc.getBlock();
				
				block.setType(Material.valueOf(Type));
							
		        Ageable ageable = (Ageable) block.getBlockData();
		        ageable.setAge(ageable.getMaximumAge());
		        block.setBlockData(ageable);
				
			}else {
				loc.getBlock().setType(Material.valueOf(Type));
			}
			loc.getWorld().spawnParticle(particle, loc, 50, 1, 1, 1);
		}
		
	}

}
