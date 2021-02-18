package fr.astrant.farmland;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class GriefListener implements Listener{
	FarmLandDataBaseManager data;
	ErrorManager error;
	
	List<EntityType> protectedentities = Arrays.asList(EntityType.ARMOR_STAND, EntityType.ITEM_FRAME);
	List<Material> forbiddenobjects = Arrays.asList(Material.ARMOR_STAND, Material.FLINT_AND_STEEL);

	@EventHandler
	public void onPlayerPlace (BlockPlaceEvent e) {
		data = new FarmLandDataBaseManager();
		error = new ErrorManager();

		Block block = e.getBlock();
		Player p = e.getPlayer();
		org.bukkit.Location loc = block.getLocation();
		String PlotKey = data.getPlotKey(loc);
		if(data.isPlot(PlotKey)) {
	
			if(!p.hasPermission("farmland.admin")) {
				e.setCancelled(true);
				error.ErrorSender(p, "Grief");
			}
			
		}
		
	}
    
    @EventHandler
    public void OnEntityExplodeEvent(final EntityExplodeEvent e) {
        List<Block> destroyed = (List<Block>)e.blockList();
        Iterator<Block> it = destroyed.iterator();
        
        while (it.hasNext()) {
            final Block block = it.next();
            final Location loc = block.getLocation();
            final String PlotKey = data.getPlotKey(loc);
            if (data.isPlot(PlotKey)) {
                it.remove();
            }
        }
    }
    
    @EventHandler
    public void BlockSpreadEvent(final BlockGrowEvent e) {
		data = new FarmLandDataBaseManager();
		
		Block block = e.getBlock();
        final Location loc = block.getLocation();
        final String PlotKey = data.getPlotKey(loc);
        
        if (data.isPlot(PlotKey)) {
            e.setCancelled(true);
        }
        
    }
    
    @EventHandler
    public void PlayerInteractEntityEvent(PlayerInteractAtEntityEvent   e) {
		data = new FarmLandDataBaseManager();
		error = new ErrorManager();

		EntityType entity = e.getRightClicked().getType();
		Player p = e.getPlayer();
		Location loc = e.getRightClicked().getLocation();
		String PlotKey = data.getPlotKey(loc);
		
        if (data.isPlot(PlotKey)) {
        	
			if(!p.hasPermission("farmland.admin")) {
				
				if(protectedentities.contains(entity)) {
		            e.setCancelled(true);
					error.ErrorSender(p, "Grief");
				}

			}
			
        }
        
    }
    
    @EventHandler
    public void PlayerAttackEvent(EntityDamageByEntityEvent e) {
		data = new FarmLandDataBaseManager();
		error = new ErrorManager();

		if(e.getDamager() instanceof Player) {
			
			EntityType entity = e.getEntityType();
			Player p = (Player) e.getDamager();
			Location loc = e.getEntity().getLocation();
			String PlotKey = data.getPlotKey(loc);
			
	        if (data.isPlot(PlotKey)) {
	        	
				if(!p.hasPermission("farmland.admin")) {
					
					if(protectedentities.contains(entity)) {
			            e.setCancelled(true);
						error.ErrorSender(p, "Grief");
					}

				}
			
	        }
	        
		}

    }
    
    @EventHandler
    public void PlayerInterractEvent(PlayerInteractEvent  e) {
		data = new FarmLandDataBaseManager();
		error = new ErrorManager();

		Player p = e.getPlayer();
		Material object = p.getInventory().getItemInMainHand().getType();
		Action action = e.getAction();
		
		if(action.equals(Action.RIGHT_CLICK_BLOCK)) {
			Location loc = e.getClickedBlock().getLocation();
			String PlotKey = data.getPlotKey(loc);

	        if (data.isPlot(PlotKey)) {
	        	
				if(!p.hasPermission("farmland.admin")) {
					
					if(forbiddenobjects.contains(object)) {
			            e.setCancelled(true);
						error.ErrorSender(p, "Grief");
					}

				}
			
	        }
	        
		}

    }
    
    @EventHandler
    public void onBlockFromTo(BlockFromToEvent e) {
		data = new FarmLandDataBaseManager();
		
		Block block = e.getToBlock();
        final Location loc = block.getLocation();
        final String PlotKey = data.getPlotKey(loc);
        
        if (data.isPlot(PlotKey)) {
            e.setCancelled(true);
        }
      
    }
    
    @EventHandler
    public void onPlayerBucketEmpty(PlayerBucketEmptyEvent e) {
		data = new FarmLandDataBaseManager();
		error = new ErrorManager();

	    Material bucket = e.getBucket();
		Block block = e.getBlockClicked();
        Player p = e.getPlayer();
        Location loc = block.getLocation();
        String PlotKey = data.getPlotKey(loc);
        
        if (data.isPlot(PlotKey)) {
        	
			if(!p.hasPermission("farmland.admin")) {
				
	    	    if (bucket.toString().contains("LAVA") || bucket.toString().contains("WATER")) {
	                e.setCancelled(true);
					error.ErrorSender(p, "Grief");
	    	    }
	    	    
			}

    	    
        }
     
    }

}
