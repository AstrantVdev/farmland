package fr.astrant.farmland;

import org.bukkit.entity.Player;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPCRegistry;

public class FarmPlotCommand {
	FarmLandDataBaseManager data;
	PlayerDataBaseMananger playerdata;
	ErrorManager error;
	
    final NPCRegistry registry = CitizensAPI.getNPCRegistry();
	public String[] args;
	public Player p;
	public String Farm;
	
	public void FarmPlotAdd() {
		error = new ErrorManager();
		data = new FarmLandDataBaseManager();
		playerdata = new PlayerDataBaseMananger();
		
		if(args.length == 4) {
			String PlotKey  = data.getPlotKey(p);

			if(!data.isPlot(PlotKey)) {				
				data.addPlot(Farm, PlotKey);
				playerdata.msg(p, "");

			}else {
				error.ErrorSender(p, "IsAlready.Plot");
			}

		}else {
			FarmCommandHelp();
		}

	}
	
	public void FarmPlotRemove() {
		error = new ErrorManager();
		data = new FarmLandDataBaseManager();
		playerdata = new PlayerDataBaseMananger();
		
		if(args.length == 3) {
			String PlotKey  = data.getPlotKey(p);

			if(data.isPlot(PlotKey)) {
				String PlotFarm = data.getPlotFarm(PlotKey);
				
				data.removePlot(PlotFarm, PlotKey);
				playerdata.msg(p, "");

			}else {
				error.ErrorSender(p, "Is.Plot");
			}

		}else {
			FarmCommandHelp();
		}

	}
	
	public void FarmCommandHelp() {
		error = new ErrorManager();

		error.ErrorSender(p, "DontExist");
		p.sendMessage("§a_oO.FarmPlotCommand.Oo_");
		p.sendMessage("§a- §e/farmland farm plot add <Farm>");
		p.sendMessage("§a- §e/farmland farm plot remove");

	}
	
}
