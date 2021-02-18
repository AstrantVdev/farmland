package fr.astrant.farmland;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class FarmlandTabCompleter implements TabCompleter{
	FarmLandDataBaseManager data;
	DropEventManager dropeventmanager;
	FarmerCommand farmercommand;
	FarmMarketCommand farmmarketcommand;
	
	public static Main plugin  = (Main) Main.plugin;
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
		data = new FarmLandDataBaseManager();
		dropeventmanager = new DropEventManager();
		farmercommand = new FarmerCommand();
		farmmarketcommand = new FarmMarketCommand();
		
		int size = args.length;
		
		if(cmd instanceof Player) {
			Player p = (Player) sender;
			if(p.hasPermission("!farmland.admin")) {
				return null;
			}
			
		}

		if(size == 1) {
			return Arrays.asList("farm", "farmer", "drop", "tool", "block", "reload");

		}else {
			switch(args[0]) {
			
			case "farm":
				if(size == 2) {
					return Arrays.asList("create", "remove", "teleport", "setspawn", "title", "plot", "money", "market", "list");
				}
				
				switch(args[1]) {
				case "remove":
					if(size == 3) {
						if(data.isFarms()) {
							return data.getFarms();
						}
					}
					break;
					
				case "title":
					if(size == 3) {
						if(data.isFarms()) {
							return data.getFarms();
						}
					}
					break;
					
				case "setspawn":
					if(size == 3) {
						if(data.isFarms()) {
							return data.getFarms();
						}
					}
					break;
					
				case "teleport":
					if(size == 3) {
						if(data.isFarms()) {
							return data.getFarms();
						}
					}
					break;
					
				case "plot":
					if(size == 3) {
						return Arrays.asList("add", "remove");
					}
					
					switch(args[2]) {
					case "add":
						if(size == 4) {
							if(data.isFarms()) {
								return data.getFarms();
							}
						}
						break;
					default:
						break;
					}

					break;
					
				case "money":
					if(size == 3) {
						return Arrays.asList("add", "withdraw", "set", "check");
					}
					
					switch(args[2]) {
					case "add":
						if(size == 4) {
							if(data.isFarms()) {
								return data.getFarms();
							}
						}
						if(size == 5) {
							return Arrays.asList("64", "128", "256", "512", "1024", "2048");

						}
						break;
					case "withdraw":
						if(size == 4) {
							if(data.isFarms()) {
								return data.getFarms();
							}
						}
						if(size == 5) {
							return Arrays.asList("64", "128", "256", "512", "1024", "2048");

						}
						break;
					case "set":
						if(size == 4) {
							if(data.isFarms()) {
								return data.getFarms();
							}
						}
						if(size == 5) {
							return Arrays.asList("64", "128", "256", "512", "1024", "2048");

						}
						break;
					case "check":
						if(size == 4) {
							if(data.isFarms()) {
								return data.getFarms();
							}
						}
						break;
					default:
						break;
					}
				
					break;
					
				case "market":
					if(size == 3) {
						return Arrays.asList("add", "remove", "setmoney", "setmoy", "list");
					}
					
					switch(args[2]) {
					case "add":
						if(size == 4) {
							if(data.isFarms()) {
								return data.getFarms();
							}
						}
						if(size == 5) {
							return farmmarketcommand.Types;
						}
						if(size == 6) {
							if(data.isDrops()) {
								return data.getDropsNames();
							}
						}
						if(size == 7) {
							return Arrays.asList("64", "128", "256", "512", "1024", "2048");
						}
						if(size == 8) {
							return Arrays.asList("64", "128", "256", "512", "1024", "2048");
						}
						break;
					case "remove":
						if(size == 4) {
							if(data.isFarms()) {
								return data.getFarms();
							}
						}
						if(size == 5) {
							return farmmarketcommand.Types;
						}
						if(size == 6) {
							if(args[5].equals("buy")) {
								if(data.isMarketBuys(args[3])) {
									data.getMarketBuys(args[3]);
								}
							}
							
							if(args[5].equals("sell")) {
								if(data.isMarketSells(args[3])) {
									data.getMarketSells(args[3]);
								}
							}

						}
						break;
					case "setmoney":
						if(size == 4) {
							if(data.isFarms()) {
								return data.getFarms();
							}
						}
						if(size == 5) {
							return farmmarketcommand.Types;
						}
						if(size == 6) {
							if(args[5].equals("buy")) {
								if(data.isMarketBuys(args[3])) {
									data.getMarketBuys(args[3]);
								}
							}
							
							if(args[6].equals("sell")) {
								if(data.isMarketSells(args[3])) {
									data.getMarketSells(args[3]);
								}
							}

						}
						if(size == 7) {
							return Arrays.asList("64", "128", "256", "512", "1024", "2048");
						}
						break;
					case "setmoy":
						if(size == 3) {
							if(data.isFarms()) {
								return data.getFarms();
							}
						}
						if(size == 4) {
							return farmmarketcommand.Types;
						}
						if(size == 5) {
							if(args[4].equals("buy")) {
								if(data.isMarketBuys(args[3])) {
									data.getMarketBuys(args[3]);
								}
							}
							
							if(args[4].equals("sell")) {
								if(data.isMarketSells(args[3])) {
									data.getMarketSells(args[3]);
								}
							}

						}
						if(size == 6) {
							return Arrays.asList("64", "128", "256", "512", "1024", "2048");
						}
						break;
					case "list":
						if(size == 3) {
							if(data.isFarms()) {
								return data.getFarms();
							}
						}
						if(size == 4) {
							return farmmarketcommand.Types;
						}
						break;
					default:
						break;
					}
					
					break;
					
				default:
					break;
					
					
						
				}
				break;
				
			case "farmer":
				if(size == 2) {
					return Arrays.asList("create", "remove", "setname", "settypes", "setskin", "setpos", "list");
				}
				switch(args[1]) {
				case "create":
					if(size == 3) {
						if(data.isFarms()) {
							return data.getFarms();
						}
						
					}
					if(size == 5) {
						return farmercommand.FarmerTypes;
						
					}
					if(size == 6) {
						return farmercommand.FarmerTypes;
						
					}
					break;
				case "remove":
					if(size == 3) {
						if(data.isFarms()) {
							return data.getFarms();
						}
						
					}
					if(size == 4) {
						String Farm = args[2];
						if(data.isFarmFarmers(Farm)) {
							return data.getFarmFarmers(Farm);
						}
						
					}
					break;
				case "setname":
					if(size == 3) {
						if(data.isFarms()) {
							return data.getFarms();
						}
						
					}
					if(size == 4) {
						String Farm = args[2];
						if(data.isFarmFarmers(Farm)) {
							return data.getFarmFarmers(Farm);
						}
						
					}
					break;
				case "settypes":
					if(size == 3) {
						if(data.isFarms()) {
							return data.getFarms();
						}
						
					}
					if(size == 4) {
						String Farm = args[2];
						if(data.isFarmFarmers(Farm)) {
							return data.getFarmFarmers(Farm);
						}
						
					}
					if(size == 5) {
						return farmercommand.FarmerTypes;
						
					}
					if(size == 6) {
						List<String> list = farmercommand.FarmerTypes;
						list.remove(args[5]);
						return list;
						
					}
					if(size == 7) {
						List<String> list = farmercommand.FarmerTypes;
						list.removeAll(Arrays.asList(args[5], args[6]));
						return list;						
					}
					break;
				case "setskin":
					if(size == 3) {
						if(data.isFarms()) {
							return data.getFarms();
						}
						
					}
					if(size == 4) {
						String Farm = args[2];
						if(data.isFarmFarmers(Farm)) {
							return data.getFarmFarmers(Farm);
						}
						
					}
					break;
				case "setpos":
					if(size == 3) {
						if(data.isFarms()) {
							return data.getFarms();
						}
						
					}
					if(size == 4) {
						String Farm = args[2];
						if(data.isFarmFarmers(Farm)) {
							return data.getFarmFarmers(Farm);
						}
						
					}
					break;
				case "list":
					if(size == 3) {
						if(data.isFarms()) {
							return data.getFarms();
						}
						
					}
					break;
				default:
					break;
						
				}
				break;
				
			case "drop":
				if(size == 2) {
					return Arrays.asList("add", "remove", "set", "event", "list");
				}
				
				switch(args[1]) {
				case "remove":
					if(size == 3) {
						if(data.isDrops()) {
							return data.getDropsNames();
						}
						
					}
					break;
				case "set":
					if(size == 3) {
						if(data.isDrops()) {
							return data.getDropsNames();
						}
					}
					break;
				case "event":
					if(size == 3) {
						return Arrays.asList("add", "remove", "set", "list");
					}
					
					switch(args[2]) {
					case "add":
						if(size == 4) {
							if(data.isDrops()) {
								return data.getDropsNames();
							}
						}
						if(size == 5) {
							return dropeventmanager.DropEvents;
						}
						if(size == 6) {
							if(args[4].equals("MONEY")) {
								return Arrays.asList("64", "128", "256", "512", "1024", "2048");
							}
							if(args[4].equals("PARTICLES")) {
								List<String> particle = Stream.of(Particle.values())
							            .map(Enum::name)
							            .collect(Collectors.toList());
								return particle;
							}
						}
						break;
					case "remove":
						if(size == 4) {
							if(data.isDrops()) {
								return data.getDropsNames();
							}
						}
						if(size == 5) {
							if(data.isDropName(args[3])) {
								if(data.isDropEvents(args[3])) {
									return Arrays.asList("64", "128", "256", "512", "1024", "2048");
								}
							}
						}
						break;
					case "set":
						if(size == 4) {
							if(data.isDrops()) {
								return data.getDropsNames();
							}
						}
						if(size == 5) {
							if(data.isDropName(args[3])) {
								if(data.isDropEvents(args[3])) {
									return Arrays.asList("64", "128", "256", "512", "1024", "2048");
								}
							}
						}
						if(size == 6) {
							return dropeventmanager.DropEvents;
						}
						if(size == 7) {
							if(args[5].equals("money")) {
								return Arrays.asList("10", "100", "1000");
							}
						}
						break;
					case "list":
						if(size == 4) {
							if(data.isDrops()) {
								return data.getDropsNames();
							}
						}
						break;
					}
					break;
				default:
					break;
						
				}
				break;
				
			case "tool":
				if(size == 2) {
					return Arrays.asList("add", "remove", "set", "list");
				}
				
					switch(args[1]) {
					case "remove":
						if(size == 3) {
							if(data.isTools()) {
								return data.getToolsNames();
							}
						}
						break;
					case "set":
						if(size == 3) {
							if(data.isTools()) {
								return data.getToolsNames();
							}
						}
						break;
					default:
						break;
							
					}
			break;
				
			case "block":
				if(size == 2) {
					return Arrays.asList("add", "remove", "cooldownset", "drop", "particleset", "list");
				}
				
				switch(args[1]) {
				case "add":
					if(size == 3) {
						if(data.isFarms()) {
							return data.getFarms();
						}
					}
					if(size == 4) {
						if(data.isTools()) {
							return data.getToolsNames();
						}
					}
					if(size == 5) {
						if(data.isDrops()) {
							return data.getDropsNames();
						}
					}
					if(size == 6) {
						return Arrays.asList("64", "128", "256", "512", "1024", "2048");
					}
					if(size == 7) {
						return Arrays.asList("64", "128", "256", "512", "1024", "2048");
					}
					if(size == 8) {
						List<String> particle = Stream.of(Particle.values())
					            .map(Enum::name)
					            .collect(Collectors.toList());
						return particle;
						
					}
					break;
				case "remove":
					if(size == 3) {
						if(data.isFarms()) {
							return data.getFarms();
						}
					}
					if(size == 4) {
						if(data.hasBlocks(args[2])){
							return data.getBlocks(args[2]);
						}
					}
					break;
				case "cooldownset":
					if(size == 3) {
						if(data.isFarms()) {
							return data.getFarms();
						}
					}
					if(size == 4) {
						return data.getBlocks(args[2]);
					}
					if(size == 5) {
						return Arrays.asList("64", "128", "256", "512", "1024", "2048");
					}
					break;
				case "drop":
					if(size == 3) {
						return Arrays.asList("add", "remove", "chanceset", "list");
					}
					switch(args[2]) {
					case "add":
						if(size == 4) {
							if(data.isFarms()) {
								return data.getFarms();
							}
						}
						if(size == 5) {
							if(data.isFarm(args[3])) {
								if(data.isBlocks(args[3])) {
									return data.getBlocks(args[3]);
								}	
							}				
						}
						if(size == 6) {
							if(data.isFarm(args[3])) {
								if(data.isBlock(args[3], args[4])) {
									if(data.isBlockTools(args[3], args[4])) {
										return data.getToolsNames();
									}	
								}
							}
						
						}
						if(size == 7) {
							if(data.isDrops()) {
								return data.getDropsNames();
							}							
						}
						if(size == 8) {
							return Arrays.asList("64", "128", "256", "512", "1024", "2048");
						}
						break;
						
					case "remove":
						if(size == 4) {
							if(data.isFarms()) {
								return data.getFarms();
							}
						}
						if(size == 5) {
							if(data.isFarm(args[3])) {
								if(data.isBlocks(args[3])) {
									return data.getBlocks(args[3]);
								}	
							}				
						}
						if(size == 6) {
							if(data.isFarm(args[3])) {
								if(data.isBlock(args[3], args[4])) {
									if(data.isBlockTools(args[3], args[4])) {
										return data.getToolsNames();
									}	
								}
							}
						
						}
						if(size == 7) {
							if(data.isDrops()) {
								return data.getDropsNames();
							}							
						}
						break;
						
					case "chanceset":
						if(size == 4) {
							if(data.isFarms()) {
								return data.getFarms();
							}
						}
						if(size == 5) {
							if(data.isFarm(args[3])) {
								if(data.isBlocks(args[3])) {
									return data.getBlocks(args[3]);
								}	
							}				
						}
						if(size == 6) {
							if(data.isFarm(args[3])) {
								if(data.isBlock(args[3], args[4])) {
									if(data.isBlockTools(args[3], args[4])) {
										return data.getToolsNames();
									}	
								}
							}
						
						}
						if(size == 7) {
							if(data.isDrops()) {
								return data.getDropsNames();
							}							
						}
						if(size == 8) {
							return Arrays.asList("64", "128", "256", "512", "1024", "2048");
						}
						break;
						
					case "list":
						if(size == 4) {
							if(data.isFarms()) {
								return data.getFarms();
							}
						}
						if(size == 5) {
							if(data.isFarm(args[3])) {
								if(data.isBlocks(args[3])) {
									return data.getBlocks(args[3]);
								}	
							}				
						}
						if(size == 6) {
							if(data.isFarm(args[3])) {
								if(data.isBlock(args[3], args[4])) {
									if(data.isBlockTools(args[3], args[4])) {
										return data.getToolsNames();
									}	
								}
							}
						
						}
					default:
						break;
					}
					break;
				case "particleset":
					if(size == 3) {
						if(data.isFarms()) {
							return data.getFarms();
						}
					}
					if(size == 4) {
						return data.getBlocks(args[2]);
					}
					if(size == 5) {
						List<Particle> particle = Arrays.asList(Particle.values());
						List<String> Particle = new ArrayList<>();
						for(int i = 0; i != particle.size(); i++) {
							Particle.add(particle.get(i).toString());
						}
						return Particle;						}
					break;
				case "list":
					if(size == 3) {
						if(data.isFarms()) {
							return data.getFarms();
						}
					}
					break;
				default:
					break;
						
				}
				break;
				
			default:	
				break;
			}
		}

		return null;
	}
	

}
