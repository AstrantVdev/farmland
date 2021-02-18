package fr.astrant.farmland;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ToolCommand {
	FarmLandDataBaseManager data;
	PlayerDataBaseMananger playerdata;
	ErrorManager error;
	
	public String[] args;
	public Player p;
	public String Name;
	public ItemStack Tool;
	
	public void ToolAdd() {
		data = new FarmLandDataBaseManager();
		playerdata = new PlayerDataBaseMananger();
		error = new ErrorManager();
		
		if(args.length == 3) {
			if(!data.isTool(Name)) {
				if(!data.isTool(Tool)) {
					if(!Tool.getType().equals(Material.AIR)) {
						data.addTool(Name, Tool);
						playerdata.msg(p, "");
						
					}else {
						error.ErrorSender(p, "NothinginHand");
					}
				}else {
					error.ErrorSender(p, "IsAlready.Too");
				}

			}else {
				error.ErrorSender(p, "IsAlready.ToolName");
			}
			
		}else {
			ToolCommandHelp();
		}
	}
	
	public void ToolRemove() {
		data = new FarmLandDataBaseManager();
		playerdata = new PlayerDataBaseMananger();
		error = new ErrorManager();
		
		if(args.length == 3) {
			if(data.isTool(Name)) {
				data.removeTool(Name);
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
									
									if(data.isBlockTool(Farm, Block, Name)) {
										data.removeBlockTool(Farm, Block, Name);
										
									}
									
								}

							}
							
						}
						
					}
					
				}
				
			}else {
				error.ErrorSender(p, "Is.Tool");
			}
			
		}else {
			ToolCommandHelp();
		}
	}
	
	public void ToolSet() {
		data = new FarmLandDataBaseManager();
		playerdata = new PlayerDataBaseMananger();
		error = new ErrorManager();
		
		if(args.length == 3) {
			if(data.isTool(Name)) {
				data.addTool(Name, Tool);
				playerdata.msg(p, "");

			}else {
				error.ErrorSender(p, "Is.Tool");
			}
			
		}else {
			ToolCommandHelp();
		}
	}
	
	public void ToolList() {
		error = new ErrorManager();
		data = new FarmLandDataBaseManager();

		if(args.length == 2) {

			if(data.isTools()) {
				List<String> Tools = data.getToolsNames();
				
				for(int i = 0; i != Tools.size(); i++) {
					p.sendMessage("§a- §e" + Tools.get(i));
				}
				
			}
			
		}else {
			 ToolCommandHelp();
		}

	}
	
	public void ToolCommandHelp() {
		error = new ErrorManager();

		error.ErrorSender(p, "DontExist");
		p.sendMessage("§a_oO.ToolCommand.Oo_");
		p.sendMessage("§a- §e/farmland tool add <Name>");
		p.sendMessage("§a- §e/farmland tool remove <Name>");
		p.sendMessage("§a- §e/farmland tool set <Name>");
		p.sendMessage("§a- §e/farmland tool list");

	}
	
}
