package fr.astrant.farmland;

import java.util.List;

import org.bukkit.entity.Player;

public class BlockDropCommand {
	FarmLandDataBaseManager data;
	PlayerDataBaseMananger playerdata;
	ErrorManager error;
	
	public String[] args;
	public Player p;
	public String Farm;
	public String Block;
	public String Tool;
	public String Drop;
	public int Chance;
	
	public void BlockDropAdd() {
		data = new FarmLandDataBaseManager();
		playerdata = new PlayerDataBaseMananger();
		error = new ErrorManager();
		
		if(args.length == 8) {
			
			if(data.isFarm(Farm)) {
				
				if(data.isBlock(Farm, Block)) {
					try {
						data.addBlockDrop(Drop, Chance, Farm, Block, Tool);
						playerdata.msg(p, "");

					}catch(Exception ex) {
						error.ErrorSender(p, "Is.Int");
					}
					
				}else {
					error.ErrorSender(p, "Is.Block");
				}
				
			}else {
				error.ErrorSender(p, "Is.Farm");
			}
			
		}else {
			BlockDropCommandHelp();
		}
	}
	
	public void BlockDropRemove() {
		data = new FarmLandDataBaseManager();
		playerdata = new PlayerDataBaseMananger();
		error = new ErrorManager();
		
		if(args.length == 7) {
			
			if(data.isFarm(Farm)) {
				
				if(data.isBlock(Farm, Block)) {
					
					if(data.isBlockTool(Farm, Block, Tool)) {
						
						if(data.isBlockToolDrop(Farm, Block, Tool, Drop)) {
							data.removeBlockDrop(Farm, Block, Tool, Drop);
							playerdata.msg(p, "");
							
						}else {
							error.ErrorSender(p, "Is.BlockToolDrop");
						}
						
					}else {
						error.ErrorSender(p, "Is.BlockTool");
					}

				}else {
					error.ErrorSender(p, "Is.Block");
				}
				
			}else {
				error.ErrorSender(p, "Is.Farm");
			}
			
		}else {
			BlockDropCommandHelp();
		}
	}
	
	public void BlockDropsetChance() {
		data = new FarmLandDataBaseManager();
		playerdata = new PlayerDataBaseMananger();
		error = new ErrorManager();
		
		if(args.length == 8) {
			
			if(data.isFarm(Farm)) {
				
				if(data.isBlock(Farm, Block)) {
					
					if(data.isBlockTool(Farm, Block, Tool)) {
						
						if(data.isBlockToolDrop(Farm, Block, Tool, Drop)) {
							
							try {
								data.setBlockDropChance(Farm, Block, Tool, Drop, Chance);
								playerdata.msg(p, "");
								
							}catch(Exception ex) {
								error.ErrorSender(p, "Is.Int");

							}
							
						}else {
							error.ErrorSender(p, "Is.BlockToolDrop");
						}
						
					}else {
						error.ErrorSender(p, "Is.BlockTool");
					}

				}else {
					error.ErrorSender(p, "Is.Block");
				}
				
			}else {
				error.ErrorSender(p, "Is.Farm");
			}
			
		}else {
			BlockDropCommandHelp();
		}
	}
	
	public void BlockDropList() {
		error = new ErrorManager();
		data = new FarmLandDataBaseManager();

		if(args.length == 6) {
			
			if(data.isFarm(Farm)) {
				
				if(data.isBlock(Farm, Block)) {
					
					if(data.isBlockTool(Farm, Block, Tool)) {
						List<String> Drops = data.getBlockDrops(Farm, Block, Tool);
						
						for(int i = 0; i != Drops.size(); i++) {
							int Chance = data.getBlockDropChance(Farm, Block, Tool, Drops.get(i));
							p.sendMessage("§a- §e" + Drops.get(i) + " |Chance: " + Chance);
						}
						
					}else {
						error.ErrorSender(p, "Is.BlockTool");
					}

				}else {
					error.ErrorSender(p, "Is.Block");
				}
				
			}else {
				error.ErrorSender(p, "Is.Farm");
			}

		}else {
			BlockDropCommandHelp();
		}
		
	}
	
	public void BlockDropCommandHelp() {
		error = new ErrorManager();

		error.ErrorSender(p, "DontExist");
		p.sendMessage("§a_oO.BlockDropCommand.Oo_");
		p.sendMessage("§a- §e/farmland block drop add <Farm> <Block> <Tool> <Drop> <Chance>");
		p.sendMessage("§a- §e/farmland block drop remove <Farm> <Block> <Tool> <Drop>");
		p.sendMessage("§a- §e/farmland block drop chanceset <Farm> <Block> <Tool> <Drop> <Chance>");
		p.sendMessage("§a- §e/farmland block drop list <Farm> <Block> <Tool>");

	}
	
}
