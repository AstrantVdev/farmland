package fr.astrant.farmland;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FarmlandCommand implements CommandExecutor {
	PlayerDataBaseMananger playerdata;
	FarmCommand farmcommand;
	DropCommand dropcommand;
	ToolCommand toolcommand;
	BlockCommand blockcommand;
	BlockDropCommand blockdropcommand;
	DropEventCommand dropeventcommand;
	FarmerCommand farmercommand;
	FarmMoneyCommand farmmoneycommand;
	FarmMarketCommand farmmarketcommand;
	FarmPlotCommand farmplotcommand;
	ErrorManager error;
	ConfigManager config;
	private Main main;
	  
    public FarmlandCommand(Main main) {
    	this.main = main;
	}
	
	private String[] args;
	private Player p;
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] Args) {
		error = new ErrorManager();
		config = new ConfigManager();
		playerdata = new PlayerDataBaseMananger();
		
		if(sender instanceof Player) {
			args = Args;
			p = (Player) sender;
			
			if(p.hasPermission("farmland.admin")) {
				if(args.length > 0) {
					
					switch(args[0]) {
					
					case "farm":
						farmCommandManager();
						break;
					case "farmer":
						farmerCommandManager();
						break;
					case "drop":
						dropCommandManager();
						break;
					case "tool":
						toolCommandManager();
						break;
					case "block":
						blockCommandManager();
						break;
					case "reload":
						playerdata.msg(p, "");
						main.Reload();
						break;
					default:
						break;
						
					}
					
				}else {
					error.ErrorSender(p,"DontExist");
					p.sendMessage("§a_oO.FarmLandCommand.Oo_");
					p.sendMessage("§a- §e/farmland farm ...");
					p.sendMessage("§a- §e/farmland farmer ...");
					p.sendMessage("§a- §e/farmland drop...");
					p.sendMessage("§a- §e/farmland tool...");
					p.sendMessage("§a- §e/farmland block...");
				}
			}
			
		}
		return false;
	}

	public void farmCommandManager(){
		farmcommand = new FarmCommand();
		farmcommand.args = args;
		farmcommand.p = p;
		if(args.length == 3) {farmcommand.Farm = args[2];}
		
		switch(args[1]) {
		case "create":
			farmcommand.FarmCreate();
			break;
		case "remove":
			farmcommand.FarmRemove();
			break;
		case "list":
			farmcommand.FarmList();
			break;
		case "money":
			farmmoneyCommandManager();
		case "title":
			farmcommand.FarmTitle();
			break;
		case "plot":
			farmplotCommandManager();
			break;
		case "market":
			farmmarketCommandManager();
			break;
		case "setspawn":
			farmcommand.FarmsetSpawn();
			break;
		case "teleport":
			farmcommand.FarmTeleport();
			break;
		default:
			farmcommand.FarmCommandHelp();
		}
	}
	
	public void farmmoneyCommandManager(){
		error = new ErrorManager();
		farmmoneycommand = new FarmMoneyCommand();
		farmmoneycommand.args = args;
		farmmoneycommand.p = p;
		if(args.length > 3) {farmmoneycommand.Farm = args[3];}
		try {
			if(args.length > 4) {farmmoneycommand.Money = Integer.valueOf(args[4]);}

		}catch (Exception ex) {
			error.ErrorSender(p,"Is.Int");
		}

		switch(args[2]) {
		case "add":
			farmmoneycommand.MoneyAdd();
			break;
		case "withdraw":
			farmmoneycommand.MoneyWithdraw();
			break;
		case "set":
			farmmoneycommand.MoneySet();
			break;
		case "check":
			farmmoneycommand.MoneyCheck();
			break;
		default:
			farmmoneycommand.FarmCommandHelp();
		}
	}
	
	public void farmplotCommandManager(){
		farmplotcommand = new FarmPlotCommand();
		farmplotcommand.args = args;
		farmplotcommand.p = p;
		if(args.length > 3) {farmplotcommand.Farm = args[3];}
		
		switch(args[2]) {
		case "add":
			farmplotcommand.FarmPlotAdd();
			break;
		case "remove":
			farmplotcommand.FarmPlotRemove();
			break;
		default:
			farmplotcommand.FarmCommandHelp();
		}
	}
	
	public void farmmarketCommandManager(){
		farmmarketcommand = new FarmMarketCommand();
		farmmarketcommand.args = args;
		farmmarketcommand.p = p;
		if(args.length > 3) {farmmarketcommand.Farm = args[3];}
		if(args.length > 4) {farmmarketcommand.Type = args[4];}
		if(args.length > 5) {farmmarketcommand.Drop = args[5];}

		switch(args[2]) {
		case "add":
			farmmarketcommand.FarmMarketAdd();
			break;
		case "remove":
			farmmarketcommand.FarmMarketRemove();
			break;
		case "setmoney":
			farmmarketcommand.FarmMarketsetMoney();;
			break;
		case "setmoy":
			farmmarketcommand.FarmMarketsetMoy();
			break;
		case "list":
			farmmarketcommand.FarmMarketList();
			break;
		default:
			farmmarketcommand.FarmMarketCommandHelp();
		}
	}
	
	public void farmerCommandManager() {
		farmercommand = new FarmerCommand();
		farmercommand.p = p;
		farmercommand.args = args;
		if(args.length > 2) {farmercommand.Farm = args[2];}

		switch(args[1]) {
		case "create":
			farmercommand.FarmerCreate();
			break;
		case "remove":
			farmercommand.FarmerRemove();
			break;
		case "setname":
			farmercommand.FarmersetName();
			break;
		case "settypes":
			farmercommand.FarmersetTypes();
			break;
		case "setskin":
			farmercommand.FarmersetSkin();
			break;
		case "setpos":
			farmercommand.FarmersetPos();
			break;
		case "list":
			farmercommand.FarmerList();
			break;
		default:
			break;
		}
	}
	
	public void dropCommandManager() {
		dropcommand = new DropCommand();
		dropcommand.args = args;
		dropcommand.p = p;
		dropcommand.Drop = p.getInventory().getItemInMainHand();
		if(args.length > 2) {dropcommand.Name = args[2];}
		
		switch(args[1]) {
		case "add":
			dropcommand.DropAdd();
			break;
		case "remove":
			dropcommand.DropRemove();
			break;
		case "set":
			dropcommand.DropSet();
			break;
		case "event":
			dropeventCommandManager();
			break;
		case "list":
			dropcommand.DropList();
			break;
		default:
			break;
		}
	}
	
	public void dropeventCommandManager() {
		dropeventcommand = new DropEventCommand();
		dropeventcommand.args = args;
		dropeventcommand.p = p;
		if(args.length > 3) {dropeventcommand.Drop = args[3];}

		switch(args[2]) {
		case "add":
			dropeventcommand.DropEventAdd();
			break;
		case "remove":
			dropeventcommand.DropEventRemove();
			break;
		case "set":
			dropeventcommand.DropEventSet();
			break;
		case "list":
			dropeventcommand.DropEventList();
			break;
		default:
			break;
		}
	}
	
	public void toolCommandManager() {
		toolcommand = new ToolCommand();
		toolcommand.args = args;
		toolcommand.p = p;
		toolcommand.Tool = p.getInventory().getItemInMainHand();
		if(args.length > 2) {toolcommand.Name = args[2];}

		switch(args[1]) {
		case "add":
			toolcommand.ToolAdd();
			break;
		case "set":
			toolcommand.ToolSet();
			break;
		case "remove":
			toolcommand.ToolRemove();
			break;
		case "list":
			toolcommand.ToolList();
			break;
		default:
			break;
		}
	}
	
	public void blockCommandManager() {
		blockcommand = new BlockCommand();
		blockcommand.args = args;
		blockcommand.p = p;
		if(args.length > 2) {blockcommand.Farm = args[2];}
		if(args.length > 3) {blockcommand.Block = args[3];}

		switch(args[1]) {
		case "add":
			blockcommand.BlockAdd();
			break;
		case "remove":
			blockcommand.BlockRemove();
			break;
		case "cooldownset":
			blockcommand.BlockCooldownSet();
			break;
		case "drop":
			blockdropCommandManager();
			break;
		case "particleset":
			blockcommand.BlockParticleSet();;
			break;
		case "list":
			blockcommand.BlockList();
			break;
		default:
			break;
		}
	}
	
	public void blockdropCommandManager() {
		blockdropcommand = new BlockDropCommand();
		blockdropcommand.args = args;
		blockdropcommand.p = p;
		if(args.length > 3) {blockdropcommand.Farm = args[3];}
		if(args.length > 4) {blockdropcommand.Block = args[4];}
		if(args.length > 5) {blockdropcommand.Tool = args[5];}
		if(args.length > 6) {blockdropcommand.Drop = args[6];}
		if(args.length > 7) {blockdropcommand.Chance = Integer.valueOf(args[7]);}

		switch(args[2]) {
		case "add":
			blockdropcommand.BlockDropAdd();
			break;
		case "remove":
			blockdropcommand.BlockDropRemove();
			break;
		case "setchance":
			blockdropcommand.BlockDropsetChance();
			break;
		case "list":
			blockdropcommand.BlockDropList();
			break;
		default:
			break;
		}
	}
	
}
