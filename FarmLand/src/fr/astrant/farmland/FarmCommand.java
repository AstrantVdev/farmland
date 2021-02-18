package fr.astrant.farmland;

import java.util.List;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;

public class FarmCommand {
	FarmLandDataBaseManager data;
	PlayerDataBaseMananger playerdata;
	ErrorManager error;
	Main main;
	ConfigManager config;
	Teleportation tp;
	
    final NPCRegistry registry = CitizensAPI.getNPCRegistry();
	public String[] args;
	public Player p;
	public String Farm;
	
	public FarmCommand(){
		this.main = Main.getInstance();
	}
	
	public void FarmCreate() {	
		data = new FarmLandDataBaseManager();
		playerdata = new PlayerDataBaseMananger();
		error = new ErrorManager();
		
		if(args.length == 3) {
			
			if(!data.isFarm(Farm)) {
				String PlotKey  = data.getPlotKey(p);

				if(!data.isPlot(PlotKey)) {
					data.addPlot(Farm, PlotKey);
					data.setMoney(Farm, 0.0);
					data.setSpawn(Farm, p.getLocation());
					data.setTitle(Farm, true);
					playerdata.Annouce("§a§l[FarmLand]:§r§e ===> " + Farm, Sound.ENTITY_ENDER_DRAGON_GROWL);
				}else {
					error.ErrorSender(p, "IsAlready.Plot");
				}
				
			}else {
				error.ErrorSender(p, "IsAlready.Farm");
			}

		}else {
			FarmCommandHelp();
		}

	}
	
	public void FarmsetSpawn() {	
		data = new FarmLandDataBaseManager();
		playerdata = new PlayerDataBaseMananger();
		error = new ErrorManager();
		
		if(args.length == 3) {
			
			if(data.isFarm(Farm)) {
				Location loc = p.getLocation();
				data.setSpawn(Farm, loc);
				playerdata.msg(p, "");
				
			}else {
				error.ErrorSender(p, "Is.Farm");
			}

		}else {
			FarmCommandHelp();
		}

	}
	
	public void FarmTeleport() {	
		data = new FarmLandDataBaseManager();
		playerdata = new PlayerDataBaseMananger();
		error = new ErrorManager();
		config = new ConfigManager();
		tp = new Teleportation();
		
		if(args.length == 3) {
			
			if(data.isFarm(Farm)) {
				String LocKey = data.getSpawn(Farm);
				Location loc = data.getLocation(LocKey);
				int countdown = config.getTeleportCooldown();
				
				tp.teleportion(countdown, p, loc);
				
			}else {
				error.ErrorSender(p, "Is.Farm");
			}

		}else {
			FarmCommandHelp();
		}

	}
	
	public void FarmTitle() {	
		data = new FarmLandDataBaseManager();
		playerdata = new PlayerDataBaseMananger();
		error = new ErrorManager();
		
		if(args.length == 3) {
			
			if(data.isFarm(Farm)) {
				
				if(data.getTitle(Farm)) {
					data.setTitle(Farm, false);
				}else {
					data.setTitle(Farm, true);
				}
				playerdata.msg(p, "");

			}else {
				error.ErrorSender(p, "Is.Farm");
			}

		}else {
			FarmCommandHelp();
		}

	}
	
	public void FarmRemove() {
		error = new ErrorManager();
		data = new FarmLandDataBaseManager();
		playerdata = new PlayerDataBaseMananger();
		
		if(args.length == 3) {
			
			if(data.isFarm(Farm)) {
				
				if(data.isFarmPlots(Farm)) {
					List<String> Plots  = data.getFarmPlots(Farm);
					
					for (int i = 0; i != Plots.size(); i++) {
						data.removePlot(Farm, Plots.get(i));
					}
		
				}
				
				if(data.isFarmFarmers(Farm)) {
					List<String> Farmers = data.getFarmFarmers(Farm);

					for (int i = 0; i != Farmers.size(); i++) {
						NPC npc = registry.getByUniqueId(UUID.fromString(Farmers.get(i)));
						registry.deregister(npc);
					}
					
				}

				data.removeFarm(Farm);
				
				playerdata.Annouce("§a§l[FarmLand]:§r§c ===> " + Farm, Sound.ENTITY_ENDER_DRAGON_GROWL);
			}else {
				error.ErrorSender(p, "Is.Farm");
			}

		}else {
			FarmCommandHelp();
		}

	}
	
	public void FarmList() {
		error = new ErrorManager();
		data = new FarmLandDataBaseManager();

		if(args.length == 2) {

			if(data.isFarms()) {
				List<String> Farms = data.getFarms();
				p.sendMessage("§aFarmsList:");

				for(int i = 0; i != Farms.size(); i++) {
					p.sendMessage("§a- §e" + Farms.get(i));
				}
				
			}else {
				error.ErrorSender(p, "Is.Farms");

			}
			
		}else {
			FarmCommandHelp();
		}

	}
	
	public void FarmCommandHelp() {
		error = new ErrorManager();

		error.ErrorSender(p, "DontExist");
		p.sendMessage("§a_oO.FarmCommand.Oo_");
		p.sendMessage("§a- §e/farmland farm create <Farm>");
		p.sendMessage("§a- §e/farmland farm remove <Farm>");
		p.sendMessage("§a- §e/farmland farm setspawn <Farm>");
		p.sendMessage("§a- §e/farmland farm teleport <Farm>");
		p.sendMessage("§a- §e/farmland farm title <Farm>");
		p.sendMessage("§a- §e/farmland farm plot ...");
		p.sendMessage("§a- §e/farmland farm money ...");
		p.sendMessage("§a- §e/farmland farm market ...");
		p.sendMessage("§a- §e/farmland farm list");

	}

}
