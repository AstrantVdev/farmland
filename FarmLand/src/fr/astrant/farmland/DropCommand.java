package fr.astrant.farmland;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DropCommand {
	FarmLandDataBaseManager data;
	PlayerDataBaseMananger playerdata;
	ErrorManager error;
	
	public String[] args;
	public Player p;
	public ItemStack Drop;
	public String Name;
	
	public void DropAdd() {
		data = new FarmLandDataBaseManager();
		playerdata = new PlayerDataBaseMananger();
		error = new ErrorManager();
		
		if(args.length == 3) {
			if(!data.isDropName(Name)) {
				if(!data.isDrop(Drop)) {
					if(!Drop.getType().equals(Material.AIR)) {
						data.addDrop(Name, Drop);
						playerdata.msg(p, "");
						
					}else {
						error.ErrorSender(p, "NothinginHand");
					}
				}else {
					error.ErrorSender(p, "IsAlready.Drop");
				}

			}else {
				error.ErrorSender(p, "IsAlready.DropName");
			}
			
		}else {
			DropCommandHelp();
		}
	}
	
	public void DropRemove() {
		data = new FarmLandDataBaseManager();
		playerdata = new PlayerDataBaseMananger();
		error = new ErrorManager();
		
		if(args.length == 3) {
			if(data.isDropName(Name)) {
				data.removeDrop(Name);
				playerdata.msg(p, "");
				
				if(data.isFarms()) {
					List<String> Farms = data.getFarms();
					
					for (int f = 0; f != Farms.size(); f++) {
						String Farm = Farms.get(f);
						
						if(data.isBlocks(Farm)) {
							List<String> Blocks = data.getBlocks(Farm);
							
							for(int b =0; b != Blocks.size(); b++) {
								String Block = Blocks.get(b);
								
								if(data.isBlockTools(Farm, Block)) {
									List<String> Tools = data.getBlockTools(Farm, Block);
									
									for(int t = 0; t != Tools.size(); t++) {
										String Tool = Tools.get(t);
										
										if(data.isBlockToolDrop(Farm, Block, Tool, Name)) {
											data.removeBlockDrop(Farm, Block, Tool, Name);
										}
										
									}
									
								}

							}
							
						}
						
					}
					
				}

			}else {
				error.ErrorSender(p, "Is.Drop");
			}
			
		}else {
			DropCommandHelp();
		}
	}
	
	public void DropSet() {
		if(args.length == 3) {
			if(data.isDropName(Name)) {
				data.addDrop(Name, Drop);
				playerdata.msg(p, "");

			}else {
				error.ErrorSender(p, "Is.Drop");
			}
			
		}else {
			DropCommandHelp();
		}
	}
	
	public void DropList() {
		error = new ErrorManager();
		data = new FarmLandDataBaseManager();

		if(args.length == 2) {

			if(data.isDrops()) {
				List<String> Drops = data.getDropsNames();
				
				for(int i = 0; i != Drops.size(); i++) {
					p.sendMessage("§a- §e" + Drops.get(i));
				}
			}else {
				error.ErrorSender(p, "Is.Drops");
			}

		}else {
			DropCommandHelp();
		}
		
	}
	
	public void DropCommandHelp() {
		error = new ErrorManager();

		error.ErrorSender(p, "DontExist");
		p.sendMessage("§a_oO.DropCommand.Oo_");
		p.sendMessage("§a- §e/farmland drop add <Name>");
		p.sendMessage("§a- §e/farmland drop remove <Name>");
		p.sendMessage("§a- §e/farmland drop set <Name>");
		p.sendMessage("§a- §e/farmland drop event <Name>");
		p.sendMessage("§a- §e/farmland drop list");

	}
	
}
