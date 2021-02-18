package fr.astrant.farmland;

import org.bukkit.entity.Player;

public class FarmMoneyCommand {
	FarmLandDataBaseManager data;
	PlayerDataBaseMananger playerdata;
	ErrorManager error;
	
	public String[] args;
	public Player p;
	public String Farm;
	public int Money;
	
	public void MoneyAdd() {
		error = new ErrorManager();
		data = new FarmLandDataBaseManager();
		playerdata = new PlayerDataBaseMananger();
		
		if(args.length == 4) {
			String PlotKey  = data.getPlotKey(p);
			
			if(data.isFarm(Farm)) {
				
				try {
					data.addBank(PlotKey, Double.valueOf(args[3]));
					playerdata.msg(p, "");

				}catch(Exception e) {
					error.ErrorSender(p, "Is.Int");
				}
				
			}else {
				error.ErrorSender(p, "Is.Farm");
			}

		}else {
			FarmCommandHelp();
		}

	}
	
	public void MoneyWithdraw() {
		error = new ErrorManager();
		data = new FarmLandDataBaseManager();
		playerdata = new PlayerDataBaseMananger();
		
		if(args.length == 4) {
			String PlotKey  = data.getPlotKey(p);
			
			if(data.isFarm(Farm)) {
				
				try {
					data.withdrawMoney(PlotKey, Double.valueOf(args[3]));
					playerdata.msg(p, "");

				}catch(Exception e) {
					error.ErrorSender(p, "Is.Int");
				}
				
			}else {
				error.ErrorSender(p, "Is.Farm");
			}

		}else {
			FarmCommandHelp();
		}

	}
	
	public void MoneySet() {
		error = new ErrorManager();
		data = new FarmLandDataBaseManager();
		playerdata = new PlayerDataBaseMananger();
		
		if(args.length == 4) {
			String PlotKey  = data.getPlotKey(p);
			
			if(data.isFarm(Farm)) {
				
				try {
					data.setMoney(PlotKey, Double.valueOf(args[3]));
					playerdata.msg(p, "");

				}catch(Exception e) {
					error.ErrorSender(p, "Is.Int");
				}
				
			}else {
				error.ErrorSender(p, "Is.Farm");
			}

		}else {
			FarmCommandHelp();
		}

	}
	
	public void MoneyCheck() {
		error = new ErrorManager();
		data = new FarmLandDataBaseManager();
		playerdata = new PlayerDataBaseMananger();
		
		if(args.length == 3) {

			if(data.isFarm(Farm)) {
				Double Money = data.getMoney(Farm);
				p.sendMessage("§a_oO." + Farm + ".Oo_");
				p.sendMessage("§aMoney: " + Money);
				
			}else {
				error.ErrorSender(p, "Is.Farm");
			}

		}else {
			FarmCommandHelp();
		}

	}
	
	public void FarmCommandHelp() {
		error = new ErrorManager();

		error.ErrorSender(p, "DontExist");
		p.sendMessage("§a_oO.FarmMoneyCommand.Oo_");
		p.sendMessage("§a- §e/farmland farm money add <Farm> <Money>");
		p.sendMessage("§a- §e/farmland farm money withdraw <Farm> <Money>");
		p.sendMessage("§a- §e/farmland farm money set <Farm> <Money>");
		p.sendMessage("§a- §e/farmland farm money check <Farm>");

	}
	
}
