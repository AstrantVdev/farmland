package fr.astrant.farmland;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;

public class BlockCommand {
	FarmLandDataBaseManager data;
	PlayerDataBaseMananger playerdata;
	ErrorManager error;
	Particle particle;
	
	public String[] args;
	public Player p;
	public String Block;
	public String Farm;
	
	public void BlockAdd() {
		data = new FarmLandDataBaseManager();
		playerdata = new PlayerDataBaseMananger();
		error = new ErrorManager();
		
		if(args.length == 8) {
			
			if(data.isFarm(Farm)) {
				
				if(!data.isBlock(Farm, Block)) {
					
					if(data.isTool(args[3])) {
						
						if(data.isDropName(args[4])) {
							List<Particle> Particles = Arrays.asList(Particle.values());
							
							if(Particles.contains(Particle.valueOf(args[7]))) {
								Material block = p.getInventory().getItemInMainHand().getType();
								
								if(!block.equals(Material.AIR)) {
									if(block.equals(Material.SWEET_BERRIES)) {
										block = Material.SWEET_BERRY_BUSH;
									}
									try {
										data.addBlock(Farm, block.toString(), args[3], args [4], Integer.valueOf(args[5]), Integer.valueOf(args[6]), args[7]);
										playerdata.msg(p, "");
										
									}catch(Exception ex) {
										error.ErrorSender(p, "Is.Int");

									}
									
								}else {
									error.ErrorSender(p, "NothinginHand");
								}
								
							}else {
								error.ErrorSender(p, "Is.Particle");
							}
							
						}else{
							error.ErrorSender(p, "Is.Drop");
						}
						
					}else{
						error.ErrorSender(p, "Is.Tool");
					}

				}else {
					error.ErrorSender(p, "Is.Block");
				}
				
			}else {
				error.ErrorSender(p, "Is.Farm");
			}
			
		}else {
			BlockCommandHelp();
		}
	}
	
	public void BlockRemove() {
		data = new FarmLandDataBaseManager();
		playerdata = new PlayerDataBaseMananger();
		error = new ErrorManager();
		
		if(args.length == 4) {
			if(data.isBlock(Farm, Block)) {
				data.removeBlock(Farm, Block);
				playerdata.msg(p, "");

			}else {
				error.ErrorSender(p, "Is.Block");
			}
			
		}else {
			BlockCommandHelp();
		}
	}
	
	public void BlockCooldownSet() {
		data = new FarmLandDataBaseManager();
		playerdata = new PlayerDataBaseMananger();
		error = new ErrorManager();
		
		if(args.length == 5) {
			
			if(data.isFarm(Farm)) {
				
				if(data.isBlock(Farm, Block)) {
					data.setBlockCooldown(Farm, Block, Integer.valueOf(args[4]));
					playerdata.msg(p, "");
					
				}else {
					error.ErrorSender(p, "Is.Block");
				}
				
			}else {
				error.ErrorSender(p, "Is.Farm");
			}
			
		}else {
			BlockCommandHelp();
		}
	}
	
	public void BlockParticleSet() {
		data = new FarmLandDataBaseManager();
		playerdata = new PlayerDataBaseMananger();
		error = new ErrorManager();
		
		if(args.length == 5) {
			
			if(data.isFarm(Farm)) {
				
				if(data.isBlock(Farm, Block)) {
					List<Particle> Particles = Arrays.asList(Particle.values());
					if(Particles.contains(Particle.valueOf(args[4]))) {
						data.setBlockParticle(Farm, Block, args[4]);
						playerdata.msg(p, "");
						
					}else {
						error.ErrorSender(p, "Is.Particle");
					}

				}else {
					error.ErrorSender(p, "Is.Block");
				}
				
			}else {
				error.ErrorSender(p, "Is.Farm");
			}
			
		}else {
			BlockCommandHelp();
		}
	}
	
	public void BlockList() {
		error = new ErrorManager();
		data = new FarmLandDataBaseManager();

		if(args.length == 3) {

			if(data.isBlocks(Farm)) {
				List<String> Blocks = data.getBlocks(Farm);

				for(int i = 0; i != Blocks.size(); i++) {
					p.sendMessage("§a- §e" + Blocks.get(i));
				}
				
			}else {
				error.ErrorSender(p, "Is.Blocks");

			}
			
		}else {
			BlockCommandHelp();
		}
		
	}
	
	public void BlockCommandHelp() {
		error = new ErrorManager();

		error.ErrorSender(p, "DontExist");
		p.sendMessage("§a_oO.DropCommand.Oo_");
		p.sendMessage("§a- §e/farmland block add <Farm> <Tool> <Drop> <Chance> <Cooldown> <Particle>");
		p.sendMessage("§a- §e/farmland block remove <Farm> <Block>");
		p.sendMessage("§a- §e/farmland block cooldownset <Farm> <Block> <Cooldown (second)>");
		p.sendMessage("§a- §e/farmland block drop ...");
		p.sendMessage("§a- §e/farmland block particleset <Farm> <Block> <Particle>");
		p.sendMessage("§a- §e/farmland block list <Farm>");

	}
	
}
