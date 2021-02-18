package fr.astrant.farmland;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.citizensnpcs.npc.skin.SkinnableEntity;

public class FarmerCommand {
	ErrorManager error;
	FarmLandDataBaseManager data;
	PlayerDataBaseMananger playerdata;
	
    final NPCRegistry registry = CitizensAPI.getNPCRegistry();
	
	Player p;
	String[] args;
	String Farm;
	List<String> FarmerTypes = new LinkedList<String>(Arrays.asList("market", "info"));
	
	public boolean isFarmerTypes(List<String> Types) {
		if(FarmerTypes.containsAll(Types)) {
			return true;
		}
		return false;
	}
	
	public void FarmerCreate() {
		error = new ErrorManager();
		data = new FarmLandDataBaseManager();
		playerdata = new PlayerDataBaseMananger();
		
		if(args.length > 4 &&  args.length < 8) {
			
			if(data.isFarm(Farm)) {
				List<String> Types = new LinkedList<String>(Arrays.asList(args[4]));
				
				if(args.length  > 5) {
					if(Types.contains(args[5])) {
						error.ErrorSender(p, "FarmerTypeTwice");
						return;
						
					}
					
					Types.add(args[5]);
				}	
				
				if(isFarmerTypes(Types)) {
					Location loc = p.getLocation();
					String Name = args[3];
					net.citizensnpcs.api.npc.NPC farmer = registry.createNPC(EntityType.PLAYER, "§6"+ Farm);
					farmer.spawn(loc);
					
					String id = farmer.getUniqueId().toString();
					data.setFarmerName(Farm, id, Name);
					data.setFarmerTypes(Farm, id, Types);
					
					playerdata.msg(p, "");

				}else {
					error.ErrorSender(p, "Is.FarmerType");
				}

			}else {
				error.ErrorSender(p, "Is.Farm");
			}
			
		}else {
			FarmerCommandHelp();
		}
		
	}
	
	public void FarmersetName() {
		error = new ErrorManager();
		data = new FarmLandDataBaseManager();
		playerdata = new PlayerDataBaseMananger();
		
		if(args.length == 5) {
			
			if(data.isFarm(Farm)) {
				String id = args[3];

				if(data.isFarmFarmer(Farm, id)) {
					data.setFarmerName(id, id, args[4]);
					
					playerdata.msg(p, "");
					
				}else {
					error.ErrorSender(p, "Is.Farmer");
				}
				
			}else {
				error.ErrorSender(p, "Is.Farm");
			}
			
		}else {
			FarmerCommandHelp();
		}
		
	}
	
	public void FarmersetTypes() {
		error = new ErrorManager();
		data = new FarmLandDataBaseManager();
		playerdata = new PlayerDataBaseMananger();
		
		if(args.length > 4 && args.length < 8) {
			
			if(data.isFarm(Farm)) {
				String id = args[3];
				
				if(data.isFarmFarmer(Farm, id)) {
					List<String> Types = new LinkedList<String>(Arrays.asList(args[4]));
					if(args.length  > 5) {
						if(!Types.contains(args[5])) {
							Types.add(args[5]);
						}else {
							error.ErrorSender(p, "FarmerTypeTwice");
							return;
						}
					}
					
					if(FarmerTypes.containsAll(Types)) {
						data.setFarmerTypes(Farm, id, Types);
						playerdata.msg(p, "");
						
					}else {
						error.ErrorSender(p, "Is.FarmerType");
					}
					
				}else {
					error.ErrorSender(p, "Is.Farmer");
				}
				
			}else {
				error.ErrorSender(p, "Is.Farm");
			}
			
		}else {
			FarmerCommandHelp();
		}
		
	}
	
	public void FarmerRemove() {
		error = new ErrorManager();
		data = new FarmLandDataBaseManager();
		playerdata = new PlayerDataBaseMananger();
		
		if(args.length == 4) {
			
			if(data.isFarm(Farm)) {
				String id = args[3];

				if(data.isFarmFarmer(Farm, id)) {
					data.removeFarmer(Farm, id);
					NPC npc = registry.getByUniqueId(UUID.fromString(id));
					registry.deregister(npc);
					playerdata.msg(p, "");
					
				}else {
					error.ErrorSender(p, "Is.Farmer");
				}

			}else {
				error.ErrorSender(p, "Is.Farm");
			}
			
		}else {
			FarmerCommandHelp();
		}
		
	}
	
	public void FarmersetPos() {
		error = new ErrorManager();
		data = new FarmLandDataBaseManager();
		playerdata = new PlayerDataBaseMananger();
		
		if(args.length == 4) {
			
			if(data.isFarm(Farm)) {
				String id = args[3];
				
				if(data.isFarmFarmer(Farm, id)) {
					Location loc = p.getLocation();
					NPC npc = registry.getByUniqueId(UUID.fromString(id));
					npc.despawn();
					npc.spawn(loc);
					playerdata.msg(p, "");
					
				}else {
					error.ErrorSender(p, "Is.Farmer");
				}

			}else {
				error.ErrorSender(p, "Is.Farm");
			}
			
		}else {
			FarmerCommandHelp();
		}
		
	}
	
	public void FarmersetSkin() {
		error = new ErrorManager();
		data = new FarmLandDataBaseManager();
		playerdata = new PlayerDataBaseMananger();
		
		if(args.length == 5) {
			
			if(data.isFarm(Farm)) {
				String id = args[3];
				
				if(data.isFarmFarmer(Farm, id)) {
					NPC npc = registry.getByUniqueId(UUID.fromString(id));
			        npc.data().setPersistent("player-skin-name", args[4]);
			        npc.data().setPersistent("player-skin-use-latest-skin", (Object)false);
			        final Entity npcEntity = npc.getEntity();
			        if (npcEntity instanceof SkinnableEntity) {
			            ((SkinnableEntity)npcEntity).getSkinTracker().notifySkinChange((boolean)npc.data().get("player-skin-use-latest-skin"));
			        }
			        
					playerdata.msg(p, "");
				}else {
					error.ErrorSender(p, "Is.Farmer");
				}

			}else {
				error.ErrorSender(p, "Is.Farm");
			}
			
		}else {
			FarmerCommandHelp();
		}
		
	}
	
	public void FarmerList() {
		error = new ErrorManager();
		data = new FarmLandDataBaseManager();

		if(args.length == 3) {

			if(data.isFarmFarmers(Farm)) {
				
				if(data.isFarmFarmers(Farm)) {
					p.sendMessage("§aFarmFarmersList:");
					List<String> Farmers = data.getFarmFarmers(Farm);
					
					for(int i = 0; i != Farmers.size(); i++) {
						p.sendMessage("§a- §e" + Farmers.get(i));
					}
					
				}else {
					error.ErrorSender(p, "Is.Farmers");
				}
				
			}else {
				error.ErrorSender(p, "Is.Farm");
			}
			
		}else {
			FarmerCommandHelp();
		}

	}
	
	public void FarmerCommandHelp() {
		error = new ErrorManager();

		error.ErrorSender(p, "DontExist");
		p.sendMessage("§a_oO.FarmerCommand.Oo_");
		p.sendMessage("§a- §e/farmland farmer create <Farm> <Name> <Type1 Type2 ...>");
		p.sendMessage("§a- §e/farmland farmer remove <Farm> <id>");
		p.sendMessage("§a- §e/farmland farmer setname <Farm> <id> <Name>");
		p.sendMessage("§a- §e/farmland farmer setTypes <Farm> <id> <Type1 Type2 ...>");
		p.sendMessage("§a- §e/farmland farmer setskin <Farm> <id> <PlayerName>");
		p.sendMessage("§a- §e/farmland farmer setPos <Farm> <id>");
		p.sendMessage("§a- §e/farmland farmer list <Farm>");
	}

}
