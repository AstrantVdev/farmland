package fr.astrant.farmland;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.event.NPCLeftClickEvent;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPCRegistry;

public class FarmerClicManager implements Listener{
	BreakBlock breakblock;
	FarmLandDataBaseManager data;
	GuiManager gui;
	
    final NPCRegistry registry = CitizensAPI.getNPCRegistry();
    String Farm;
    Player p;
    String id;
    List<String> Types;
    
    @EventHandler
	public void RightClic (NPCRightClickEvent e) {
		breakblock = new BreakBlock();
		data = new FarmLandDataBaseManager();
		gui = new GuiManager();
		
		p = e.getClicker();
		id = e.getNPC().getUniqueId().toString();
		
		if(data.isFarmer(id)) {
			Farm = data.getFarmerFarm(id);
			Types = data.getFarmerTypes(Farm, id);

			if(Types.contains("market")) {
				gui.openGUI(p, new String[]{"market", Farm});

			}
			
		}
		
	}
    
    @EventHandler
	public void LeftClic (NPCLeftClickEvent e) {
		breakblock = new BreakBlock();
		data = new FarmLandDataBaseManager();
		gui = new GuiManager();

		p = e.getClicker();
		id = e.getNPC().getUniqueId().toString();
		
		if(data.isFarmer(id)) {
			Farm = data.getFarmerFarm(id);
			Types = data.getFarmerTypes(Farm, id);
			
			if(Types.contains("info")) {
				gui.openGUI(p, new String[]{"info", Farm});

			}
			
		}
		
	}

}
