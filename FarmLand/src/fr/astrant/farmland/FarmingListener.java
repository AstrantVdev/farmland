package fr.astrant.farmland;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class FarmingListener implements Listener {
	BreakBlock breakblock;
	FarmLandDataBaseManager data;
	ErrorManager error;
	PlayerDataBaseMananger playerdata;

    @EventHandler
	public void Farming (BlockBreakEvent e) {
		breakblock = new BreakBlock();
		data = new FarmLandDataBaseManager();
		error = new ErrorManager();
		playerdata = new PlayerDataBaseMananger();
		
		Player p = e.getPlayer();
		@SuppressWarnings("deprecation")
		ItemStack tool = p.getItemInHand();
		Block block = e.getBlock();
		Location loc = block.getLocation();
		Chunk plot = loc.getChunk();
		World world = loc.getWorld();
		String type = block.getType().toString();
		
		String PlotKey = data.getPlotKey(plot);
		if(data.isPlot(PlotKey)) {
			String Farm = data.getPlotFarm(PlotKey);
			
			if(data.isBreackable(Farm, block)) {
				
				if(data.isTool(tool, Farm, type)){
					e.setCancelled(true);
					block.setType(Material.AIR);
					breakblock.Breakblock(world, Farm, type, loc, tool, p);
					
				}else {
					if(p.hasPermission("farmland.admin")) {
						playerdata.msg(p, "");
					}else {
						e.setCancelled(true);
						error.ErrorSender(p,"Grief");
					}
					
				}
				
			}else {
				if(!p.hasPermission("farmland.admin")) {
					e.setCancelled(true);
					error.ErrorSender(p,"Grief");
				}
				
			}
			
		}
		
	}
	
}
