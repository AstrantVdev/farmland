package fr.astrant.farmland;

import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.Player;

public class FarmMarketCommand {
	FarmLandDataBaseManager data;
	PlayerDataBaseMananger playerdata;
	ErrorManager error;
	
	public String[] args;
	public Player p;
	public String Farm;
	public String Type;	
	public String Drop;
	List<String> Types = Arrays.asList("sell", "buy");
	
	public void FarmMarketAdd() {	
		data = new FarmLandDataBaseManager();
		playerdata = new PlayerDataBaseMananger();
		error = new ErrorManager();
		
		if(args.length == 8) {
			
			if(data.isFarm(Farm)) {
				
				if(Types.contains(Type)) {
					
					if(data.isDropName(Drop)) {
						Double Money = 0.0;
						int Moy = 0;
						
						try {
							Money = Double.valueOf(args[6]);
							Moy = Integer.valueOf(args[7]);
						}catch(Exception ex) {
							error.ErrorSender(p,"Is.Int");
							return;
						}
						
						if(Type.equalsIgnoreCase("sell")) {
							data.setMarketSellMoney(Farm, Drop, Money);
							data.setMarketSellMoy(Farm, Drop, Moy);
							
							playerdata.msg(p, "");
						}
						
						if(Type.equalsIgnoreCase("buy")) {
							data.setMarketBuyMoney(Farm, Drop, Money);
							data.setMarketBuyMoy(Farm, Drop, Moy);
							
							playerdata.msg(p, "");
						}
						
					}else {
						error.ErrorSender(p,"Is.Drop");

					}
					
				}else {
					error.ErrorSender(p,"Is.MarketType");

				}
				
			}else {
				error.ErrorSender(p,"Is.Farm");
			}

		}else {
			FarmMarketCommandHelp();
		}

	}
	
	public void FarmMarketRemove() {	
		data = new FarmLandDataBaseManager();
		playerdata = new PlayerDataBaseMananger();
		error = new ErrorManager();
		
		if(args.length == 6) {
			
			if(!data.isFarm(Farm)) {
				
				if(Types.contains(Type)) {
					
					if(Type.equalsIgnoreCase("sell")) {
						
						if(data.isMarketSell(Farm, Drop)) {
							data.removeMarketSell(Farm, Drop);
							
							playerdata.msg(p, "");
						}else {
							error.ErrorSender(p,"Is.MarketSellDrop");
						}
						
					}
					
					if(Type.equalsIgnoreCase("buy")) {
						
						if(data.isMarketBuy(Farm, Drop)) {
							data.removeMarketBuy(Farm, Drop);
							
							playerdata.msg(p, "");
						}else {
							error.ErrorSender(p,"Is.MarketBuyDrop");
						}
						
					}					
					
				}else {
					error.ErrorSender(p,"Is.MarketType");
				}
				
			}else {
				error.ErrorSender(p,"Is.Farm");
			}

		}else {
			FarmMarketCommandHelp();
		}

	}
	
	public void FarmMarketsetMoney() {	
		data = new FarmLandDataBaseManager();
		playerdata = new PlayerDataBaseMananger();
		error = new ErrorManager();
		
		if(args.length == 7) {
			
			if(!data.isFarm(Farm)) {
				
				if(Types.contains(Type)) {
					Double Money = 0.0;
					
					try {
						Money = Double.valueOf(args[6]);
					}catch(Exception ex) {
						error.ErrorSender(p,"Is.Int");
						return;
					}
					
					if(Type.equalsIgnoreCase("sell")) {
						
						if(data.isMarketSell(Farm, Drop)) {
							data.setMarketSellMoney(Farm, Drop, Money);
							
							playerdata.msg(p, "");
						}else {
							error.ErrorSender(p,"Is.MarketSellDrop");
						}
						
					}
					
					if(Type.equalsIgnoreCase("buy")) {
						
						if(data.isMarketBuy(Farm, Drop)) {
							data.setMarketBuyMoney(Farm, Drop, Money);
							
							playerdata.msg(p, "");
						}else {
							error.ErrorSender(p,"Is.MarketBuyDrop");
						}
						
					}
					
				}else {
					error.ErrorSender(p,"Is.MarketType");
				}
				
			}else {
				error.ErrorSender(p,"Is.Farm");
			}

		}else {
			FarmMarketCommandHelp();
		}

	}
	
	public void FarmMarketsetMoy() {	
		data = new FarmLandDataBaseManager();
		playerdata = new PlayerDataBaseMananger();
		error = new ErrorManager();
		
		if(args.length == 7) {
			
			if(!data.isFarm(Farm)) {
				
				if(Types.contains(Type)) {
					int Moy = 0;
					
					try {
						Moy = Integer.valueOf(args[6]);
					}catch(Exception ex) {
						error.ErrorSender(p,"Is.Int");
						return;
					}
					
					if(Type.equalsIgnoreCase("sell")) {
						
						if(data.isMarketSell(Farm, Drop)) {
							data.setMarketSellMoy(Farm, Drop, Moy);
							
							playerdata.msg(p, "");
						}else {
							error.ErrorSender(p,"Is.MarketSellDrop");
						}
						
					}
					
					if(Type.equalsIgnoreCase("buy")) {
						
						if(data.isMarketBuy(Farm, Drop)) {
							data.setMarketBuyMoy(Farm, Drop, Moy);
							
							playerdata.msg(p, "");
						}else {
							error.ErrorSender(p,"Is.MarketBuyDrop");
						}
						
					}
					
				}else {
					error.ErrorSender(p,"Is.MarketType");
				}
				
			}else {
				error.ErrorSender(p,"Is.Farm");
			}

		}else {
			FarmMarketCommandHelp();
		}

	}
	
	public void FarmMarketList() {	
		data = new FarmLandDataBaseManager();
		playerdata = new PlayerDataBaseMananger();
		error = new ErrorManager();
		
		if(args.length == 5) {
			
			if(!data.isFarm(Farm)) {
				
				if(Types.contains(Type)) {
					List<String> list = Arrays.asList();
					
					if(Type.equalsIgnoreCase("sell")) {
						
						if(data.isMarketSell(Farm, Drop)) {
							list.addAll(data.getMarketSells(Farm));
							
							p.sendMessage("§a_oO.FarmMarketSellsList.Oo_");
							
							for(int i = 0; i != list.size(); i++) {
								p.sendMessage(("§a- §e" + list.get(i) + " |Money: " + data.getMarketSellMoy(Farm, Drop) + " |Moy :" + data.getMarketSellMoy(Farm, Drop)));
							}

						}else {
							error.ErrorSender(p,"Is.MarketSellDrop");
							return;
						}
						
					}
					
					if(Type.equalsIgnoreCase("buy")) {
						
						if(data.isMarketBuy(Farm, Drop)) {
							list.addAll(data.getMarketBuys(Farm));
							
							p.sendMessage("§a_oO.FarmMarketBuysList.Oo_");
							
							for(int i = 0; i != list.size(); i++) {
								p.sendMessage(("§a- §e" + list.get(i) + " |Money: " + data.getMarketBuyMoy(Farm, Drop) + " |Moy :" + data.getMarketBuyMoy(Farm, Drop)));
							}
							
						}else {
							error.ErrorSender(p,"Is.MarketBuyDrop");
							return;
						}
						
					}
					
				}else {
					error.ErrorSender(p,"Is.MarketType");
				}
				
			}else {
				error.ErrorSender(p,"Is.Farm");
			}

		}else {
			FarmMarketCommandHelp();
		}

	}
	
	public void FarmMarketCommandHelp() {
		error = new ErrorManager();

		error.ErrorSender(p,"DontExist");
		p.sendMessage("§a_oO.FarmMarketCommand.Oo_");
		p.sendMessage("§a- §e/farmland farm market add <Farm> <Type> <Drop> <Money> <Moy>");
		p.sendMessage("§a- §e/farmland farm market remove <Farm> <Type> <Drop>");
		p.sendMessage("§a- §e/farmland farm market setmoney <Farm> <Type> <Drop> <Money>");
		p.sendMessage("§a- §e/farmland farm market setmoy <Farm> <Type> <Drop> <Moy>");
		p.sendMessage("§a- §e/farmland farm market list <Farm> <Type>");

	}
}
