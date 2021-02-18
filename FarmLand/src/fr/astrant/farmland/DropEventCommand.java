package fr.astrant.farmland;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bukkit.entity.Player;

public class DropEventCommand {
	FarmLandDataBaseManager data;
	PlayerDataBaseMananger playerdata;
	ErrorManager error;
	DropEventManager dropeventmanager;
	
	public String[] args;
	public Player p;
	public String Drop;
	
	public void DropEventAdd() {
		data = new FarmLandDataBaseManager();
		playerdata = new PlayerDataBaseMananger();
		error = new ErrorManager();
		dropeventmanager = new DropEventManager();
		
		if(args.length > 5) {
			
			if(data.isDropName(Drop)) {
				
				if(dropeventmanager.DropEvents.contains(args[4])) {
					String Value = "";
					List<String> Values = new LinkedList<String>(Arrays.asList(args));

					for(int i = 0; i != 5; i++){
						Values.remove(0);
					}
					
					for(int i = 0; i != Values.size(); i++){
						Value = Value  + Values.get(i) + " ";
					}
					
					switch(args[4]) {
					case "MONEY":
						try {
							int money = Integer.valueOf(args[5]);
							data.addDropEvent(Drop, args[4], String.valueOf(money));
							playerdata.msg(p, "");
						}catch(Exception ex) {
							error.ErrorSender(p, "Is.Int");
						}
						break;
					case "PARTICLES":
						List<String> particle = Stream.of(Particle.values())
			            .map(Enum::name)
			            .collect(Collectors.toList());
						
						if(particle.contains(args[5])) {
							data.addDropEvent(Drop, args[4], args[5]);
							playerdata.msg(p, "");
						}else {
							error.ErrorSender(p, "Is.Particle");
						}
						break;
						default:
							data.addDropEvent(Drop, args[4], args[5]);
							playerdata.msg(p, "");
							break;
					}

				}else {
					error.ErrorSender(p, "Is.DropEvent");
				}

			}else {
				error.ErrorSender(p, "Is.Drop");
			}
			
		}else {
			DropEventCommandHelp();
		}
		
	}
	
	public void DropEventRemove() {
		data = new FarmLandDataBaseManager();
		playerdata = new PlayerDataBaseMananger();
		error = new ErrorManager();
		
		if(args.length == 5) {
			
			if(data.isDropName(Drop)) {
				
				if(data.getDropEvents(Drop).size() >= Integer.valueOf(args [4])) {
					data.removeDropEvent(Drop, Integer.valueOf(args[4]));
				
				}else {
					error.ErrorSender(p, "Is.Drop");
				}
			}else {
				error.ErrorSender(p, "Is.Drop");
			}
			
		}else {
			DropEventCommandHelp();
		}
		
	}
	
	public void DropEventSet() {
		data = new FarmLandDataBaseManager();
		playerdata = new PlayerDataBaseMananger();
		error = new ErrorManager();
		dropeventmanager = new DropEventManager();
		
		if(args.length == 7) {
			
			if(data.isDropName(Drop)) {
				
				if(data.getDropEvents(Drop).size() >= Integer.valueOf(args [4])) {
					
					if(dropeventmanager.DropEvents.contains(args[5])) {
						
						switch(args[5]) {
						case "MONEY":
							try {
								int money = Integer.valueOf(args[6]);
								data.addDropEvent(Drop, args[5], String.valueOf(money));
								playerdata.msg(p, "");
							}catch(Exception ex) {
								error.ErrorSender(p, "Is.Int");
							}
							break;
						case "PARTICLES":
							List<String> particle = Stream.of(Particle.values())
				            .map(Enum::name)
				            .collect(Collectors.toList());
							
							if(particle.contains(args[6])) {
								data.addDropEvent(Drop, args[5], args[6]);
								playerdata.msg(p, "");
							}else {
								error.ErrorSender(p, "Is.Particle");
							}
							break;
							default:
								data.addDropEvent(Drop, args[5], args[6]);
								playerdata.msg(p, "");
								break;
						}

					}else {
						error.ErrorSender(p, "Is.Drop");
					}

				}else {
					error.ErrorSender(p, "Is.Drop");
				}

			}else {
				error.ErrorSender(p, "Is.Drop");
			}
			
		}else {
			DropEventCommandHelp();
		}
		
	}
	
	public void DropEventList() {
		error = new ErrorManager();
		data = new FarmLandDataBaseManager();

		if(args.length == 4) {

			if(data.isDropName(Drop)) {
				
				if(data.isDropEvents(Drop)) {
					List<String> DropEvents = data.getDropEvents(Drop);
					
					for(int i = 1; i != DropEvents.size()+1; i++) {
						String Value = data.getEventValue(Drop, i);
						p.sendMessage("§a- §e" + i + ": " + DropEvents.get(i) + " " + Value);
					}
					
				}else {
					error.ErrorSender(p, "Is.DropEvents");
				}
				
			}else {
				error.ErrorSender(p, "Is.Drop");
			}
			
		}else {
			DropEventCommandHelp();
		}
		
	}
	
	public void DropEventCommandHelp() {
		error = new ErrorManager();

		error.ErrorSender(p, "DontExist");
		p.sendMessage("§a_oO.DropEventCommand.Oo_");
		p.sendMessage("§a- §e/farmland drop event add <Drop> <Event> <Value>");
		p.sendMessage("§a- §e/farmland drop event remove <Drop> <Index>");
		p.sendMessage("§a- §e/farmland drop event set <Drop> <Index> <Event> <Value>");
		p.sendMessage("§a- §e/farmland drop event list <Drop>");

	}
	
}
