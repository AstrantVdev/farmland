package fr.astrant.farmland;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GuiManager extends BaseInventory{
    public static HashMap<String, Inventory> GUI;
    public List<String> GUITypes = Arrays.asList("market", "info", "buy", "sell", "buyitem", "sellitem", "blocksmenu", "blockmenu", "toolsmenu", "blocktooldropsmenu", "dropsmenu", "dropmenu");
    public static ItemStack RETURN = customItem(Material.ARROW, "§e<<<");
    
    static {
    	GuiManager.GUI = new HashMap<String, Inventory>();
    }
	
	public void openGUI(Player p, String[]args) {
		String Type = args[0];
		Farm = args[1];
		String uuid = p.getUniqueId().toString();
		this.p = p;
				
		switch(Type) {
		case "market":
			openMarket();
			break;
		case "info":
			openInfo();
			break;
		case "buy":
			openBuyMenu(Integer.valueOf(args[2]));
			break;
		case "sell":
			openSellMenu(Farm, Integer.valueOf(args[2]));
			break;
		case "buyitem":
			openBuyItemMenu(Farm, args[2]);
			break;
		case "sellitem":
			openSellItemMenu(Farm, args[2]);
			break;
		case "blocksmenu":
			openBlocksMenu(Integer.valueOf(args[2]));
			break;
		case "blockmenu":
			openBlockMenu(args[2]);
			break;
		case "toolsmenu":
			openToolsMenu(args[2], Integer.valueOf(args[3]));
			break;
		case "blocktooldropsmenu":
			openBlockToolDropsMenu(args[2], args[3], Integer.valueOf(args[4]));
			break;
		case "dropmenu":
			openDropMenu(args[2], args[3], args[4]);
			break;
		case "dropsmenu":
			openDropsMenu(Integer.valueOf(args[2]));
			break;
		default:
			break;
		}
		GUI.put(Type + "/" + uuid, inv);
		
	}
	
	public void openMarket(){
		playerdata = new PlayerDataBaseMananger();
		
		set("§a§lMarket " + Farm, 54);
		inv.setItem(20, customItem(Material.SUNFLOWER, "Buy"));
		inv.setItem(24, customItem(Material.SUNFLOWER, "Sell"));
		p.openInventory(inv);
		playerdata.soundgood(p);

	}
	
	public void openInfo(){
		data = new FarmLandDataBaseManager();
		playerdata = new PlayerDataBaseMananger();

		int Plots = 0;
		int Farmers = 0;
		int Blocks = 0;
		
		if(data.isFarmPlots(Farm)) {
			Plots = data.getFarmPlots(Farm).size();
		}
		if(data.isFarmFarmers(Farm)) {
			Farmers = data.getFarmFarmers(Farm).size();
		}
		if(data.isBlocks(Farm)) {
			Farmers = data.getBlocks(Farm).size();
		}
		
		set("§a§lInfos " + Farm, 54);
		inv.setItem(20, customItem(Material.GOLD_BLOCK, "§e§lMoney: §r§e" + data.getMoney(Farm)));
		inv.setItem(20, customItem(Material.GOLD_BLOCK, "§e§lMoney: §r§e" + data.getMoney(Farm)));
		inv.setItem(24, customItem(Material.GRASS_BLOCK, "§a§lPlots: §r§a" + Plots));
		inv.setItem(37, customItem(Material.MAP, "§7Farmers: " + Farmers));
		inv.setItem(38, customItem(Material.MAP, "§7Blocks: " + Blocks));
		inv.setItem(39, customItem(Material.BEACON, "§7Ooo"));
		inv.setItem(40, customItem(Material.SNOWBALL, "§7Ooo"));
		inv.setItem(41, customItem(Material.MAP, "§7BestBloc: Warped_Steam"));
		inv.setItem(42, customItem(Material.MAP, "§7BestServer: Iziria"));
		inv.setItem(43, customItem(Material.MAP, "§7Creator: Astrant_V"));

		p.openInventory(inv);
		playerdata.soundgood(p);

	}
	
	public void openBlocksMenu(int page){
		data = new FarmLandDataBaseManager();
		playerdata = new PlayerDataBaseMananger();

		set("§a§lBlocksMenu" + Farm, 54);
		if(data.isBlocks(Farm)) {
			List<String> Blocks = data.getBlocks(Farm);
			int BlocksSize = Blocks.size();
			int set = 44*(page-1);

			for(int i = 0; i != 45; i++) {
				if(BlocksSize == set) {
					return;
				}
				try {
					String Block = Blocks.get(set + i);
					inv.setItem(9 + i, customItem(Material.valueOf(Block), Block));
				}catch(Exception e) {}
			}
			
			if(BlocksSize-(set + 44) > 0){
				inv.setItem(53, customItem(Material.PAPER, "§e>>> " + (page+1)));
			}		

		}
		
		inv.setItem(0, RETURN);
		p.openInventory(inv);
		playerdata.soundgood(p);

	}
	
	@SuppressWarnings("deprecation")
	public void openBlockMenu(String Block){
		data = new FarmLandDataBaseManager();
		playerdata = new PlayerDataBaseMananger();

		int Countdown = data.getBlockCooldown(Farm, Block);
		Particle Particle = data.getBlockParticle(Farm, Block);
		set("§a§lBlockMenu" + Farm, 54);
		
		inv.setItem(0, RETURN);
		inv.setItem(5, customItem(Material.BEACON, "§7| " + Block));
		inv.setItem(23, customItem(Material.WOODEN_HOE, "§a§l| Ooo"));
		inv.setItem(29, customItem(Material.NETHER_STAR, "§7| " + Particle.toString()));
		inv.setItem(30, customItem(Material.LEGACY_WATCH, "§7| " + Countdown));		
		p.openInventory(inv);
		playerdata.soundgood(p);

	}
	
	public void openToolsMenu(String Block, int page){
		data = new FarmLandDataBaseManager();
		playerdata = new PlayerDataBaseMananger();

		set("§a§lToolsMenu" + Farm, 54);
		inv.setItem(5, customItem(Material.BEACON, "§7| " + Block));
		
		if(data.isBlockTools(Farm, Block)) {
			List<String> Tools = data.getBlockTools(Farm, Block);
			int ToolsSize = Tools.size();
			int set = 44*(page-1);
			
			for(int i = 0; i != 45; i++) {
				if(ToolsSize == set) {
					return;
				}
				try {
					String Tool = Tools.get(set + i);
					inv.setItem(9 + i, data.getToolwithName(Tool));
				}catch(Exception e) {}
			}
			
			if(ToolsSize-(set + 44) > 0){
				inv.setItem(53, customItem(Material.PAPER, "§e>>> " + (page+1)));
			}		

		}
		
		inv.setItem(0, RETURN);
		p.openInventory(inv);
		playerdata.soundgood(p);

	}
	
	public void openBlockToolDropsMenu(String Block, String Tool, int page){
		data = new FarmLandDataBaseManager();
		playerdata = new PlayerDataBaseMananger();

		set("§a§lopenBlockToolDropsMenu" + Farm, 54);
		inv.setItem(5, customItem(Material.BEACON, "§7| " + Block));
		inv.setItem(6, customItem(Material.WOODEN_HOE, "§7| " + Tool));
		
		if(data.hasBlockToolsDrops(Farm, Block, Tool)) {
			List<String> Drops = data.getBlockToolsDrops(Farm, Block, Tool);
			int DropsSize = Drops.size();
			int set = 44*(page-1);
			
			for(int i = 0; i != 45; i++) {
				if(DropsSize == set) {
					return;
				}
				try {
					String Drop = Drops.get(set + i);
					inv.setItem(9 + i, data.getDropwithName(Drop));
				}catch(Exception e) {}
			}
			
			if(DropsSize-(set + 44) > 0){
				inv.setItem(53, customItem(Material.PAPER, "§e>>> " + (page+1)));
			}		

		}
		
		inv.setItem(0, RETURN);
		p.openInventory(inv);
		playerdata.soundgood(p);
	}
	
	public void openDropMenu(String Block, String Tool, String Drop){
		data = new FarmLandDataBaseManager();
		playerdata = new PlayerDataBaseMananger();

		set("§a§lDropsMenu" + Farm, 54);
		
		inv.setItem(0, RETURN);
		inv.setItem(20, data.getDropwithName(Drop));
		
		if(data.isDropEvents(Drop)) {
			List<String> Events = data.getDropEvents(Drop);
			int EventsSize = Events.size();
			
			for(int i = 1; i != EventsSize+1; i++) {
				String Event = Events.get(i-1);
				String EventValue = data.getEventValue(Drop, i);
				
				switch(Event) {
				case "BROADCAST":
					inv.setItem(22 + i, customItem(Material.FIREWORK_ROCKET, "§a| " + EventValue));	
					break;
				case "COMMAND":
					inv.setItem(22 + i, customItem(Material.COMMAND_BLOCK, "§a| " + EventValue));	
					break;
				case "MESSAGE":
					inv.setItem(22 + i, customItem(Material.PAPER, "§a| " + EventValue));	
					break;
				case "MONEY":
					inv.setItem(22 + i, customItem(Material.GOLD_INGOT, "§a| " + EventValue));	
					break;
				case "PARTICLES":
					inv.setItem(22 + i, customItem(Material.NETHER_STAR, "§a| " + EventValue));	
					break;
				}

			}

		}
		
		if(!Tool.equals("null")) {
			int Chance = data.getBlockDropChance(Farm, Block, Tool, Drop);
			inv.setItem(29, customItem(Material.CAKE, "§7| " + Chance));
			inv.setItem(5, customItem(Material.BEACON, "§7| " + Block));
			inv.setItem(6, customItem(Material.WOODEN_HOE, "§7| " + Tool));
		}
		
		p.openInventory(inv);
		playerdata.soundgood(p);
	}
	
	public void openDropsMenu(int page){
		data = new FarmLandDataBaseManager();
		playerdata = new PlayerDataBaseMananger();

		set("§a§lopenBlockToolDropsMenu" + Farm, 54);
		
		if(data.isDrops()) {
			List<String> Drops = data.getDropsNames();
			int DropsSize = Drops.size();
			int set = 44*(page-1);
			
			for(int i = 0; i != 45; i++) {
				if(DropsSize == set) {
					return;
				}
				try {
					String Drop = Drops.get(set + i);
					inv.setItem(9 + i, data.getDropwithName(Drop));
				}catch(Exception e) {}
			}
			
			if(DropsSize-(set + 44) > 0){
				inv.setItem(53, customItem(Material.PAPER, "§e>>> " + (page+1)));
			}		

		}
		
		inv.setItem(0, RETURN);
		p.openInventory(inv);
		playerdata.soundgood(p);
	}
	
	public void openBuyMenu(int page){
		data = new FarmLandDataBaseManager();
		playerdata = new PlayerDataBaseMananger();

		set("§a§lBuyMenu" + Farm, 54);
		
		if(data.isFarmDropsProduction(Farm)) {
			if(data.isMarketBuys(Farm)){
				
				List<String> Drops = data.getFarmDropsProduction(Farm);
				List<String> Buys = data.getMarketBuys(Farm);
				int BuysSize = Buys.size();
				int set = 44*(page-1);
				
				for(int i = 0; i != 45; i++) {
					if(BuysSize == set) {
						return;
					}
					try {
						String Buy = Buys.get(set + i);
						if(Drops.contains(Buy))
						inv.setItem(9 + i, data.getDropwithName(Buy));
					}catch(Exception e) {}
				}
				
				if(BuysSize-(set + 44) > 0){
					inv.setItem(53, customItem(Material.PAPER, "§e>>> " + (page+1)));
				}

			}
		}
		
		inv.setItem(0, RETURN);
		p.openInventory(inv);
		playerdata.soundgood(p);

	}
	
	public void openSellMenu(String Farm, int page){
		data = new FarmLandDataBaseManager();
		playerdata = new PlayerDataBaseMananger();

		set("§a§lSellMenu" + Farm, 54);
		
		if(data.isFarmDropsProduction(Farm)) {
			if(data.isMarketSells(Farm)){

				
				List<String> Drops = data.getFarmDropsProduction(Farm);
				List<String> Sells = data.getMarketSells(Farm);
				int SellsSize = Sells.size();
				int set = 44*(page-1);
				
				for(int i = 0; i != 45; i++) {
					if(SellsSize == set) {
						return;
					}
					try {
						String Sell = Sells.get(set + i);
						if(Drops.contains(Sell))
						inv.setItem(9 + i, data.getDropwithName(Sell));
					}catch(Exception e) {}
				}
				
				if(SellsSize-(set + 44) > 0){
					inv.setItem(53, customItem(Material.PAPER, "§e>>> " + (page+1)));
				}

			}
		}
		
		inv.setItem(0, RETURN);
		p.openInventory(inv);
		playerdata.soundgood(p);

	}
	
	public void openBuyItemMenu(String Farm, String Drop){
		data = new FarmLandDataBaseManager();
		market = new MarketManager();
		playerdata = new PlayerDataBaseMananger();

		ItemStack drop = data.getDropwithName(Drop);
		double Money = market.getFarmSellCost(Farm, Drop, 1);
		
		set("§a§lBuyItemMenu" + Farm, 54);

		inv.setItem(0, RETURN);
		inv.setItem(20, drop);
		inv.setItem(23, customItem(Material.GREEN_SHULKER_BOX, "§a§l>>>: §r§e " + Money));
		inv.setItem(29, customItem(Material.IRON_BARS, "§7| 1"));
		
		p.openInventory(inv);
		playerdata.soundgood(p);

	}
	
	public void openSellItemMenu(String Farm, String Drop){
		data = new FarmLandDataBaseManager();
		market = new MarketManager();
		playerdata = new PlayerDataBaseMananger();

		ItemStack drop = data.getDropwithName(Drop);
		double Money = market.getFarmSellCost(Farm, Drop, 1);
		
		set("§a§lSellItemMenu" + Farm, 54);

		inv.setItem(0, RETURN);
		inv.setItem(20, drop);
		inv.setItem(23, customItem(Material.GREEN_SHULKER_BOX, "§a§l>>>: §r§e " + Money));
		inv.setItem(29, customItem(Material.IRON_BARS, "§7| 1"));
		
		p.openInventory(inv);
		playerdata.soundgood(p);

	}
	
	public void setItemQuantity(Inventory inv, int Quantity, Player p, String Type, ItemStack Item) {
		data = new FarmLandDataBaseManager();
		market = new MarketManager();
		playerdata = new PlayerDataBaseMananger();
		error = new ErrorManager();
		
		Farm = inv.getItem(4).getItemMeta().getDisplayName().split(" ")[1];
		ItemStack drop = inv.getItem(20);
		String Drop = data.getDropName(drop);
		int set = Integer.valueOf(inv.getItem(29).getItemMeta().getDisplayName().split(" ")[1]) + Quantity;
		double Money = market.getFarmSellCost(Farm, Drop, set);

		if(set < 0) {
			error.ErrorSender(p, "");
			return;
		}
		
		if(Type.equalsIgnoreCase("BUY")) {
			Inventory Pinv = p.getInventory();
			int InvSize = Pinv.getSize();
			int Total = 1;
			
	        for (int i = 0; i != InvSize; i++) {
	        	
	        	try {
	        		ItemStack here = Pinv.getItem(i);
	            	if(here.isSimilar(Item)) {	
	            		
	            		Total += here.getAmount();

	            	}
	        	}catch(Exception ex) {}
	            
	        }
			if(set >= Total) {
				error.ErrorSender(p, "");
				return;
			}
		}
		
		if(Type.equalsIgnoreCase("SELL")) {
			Double FarmDropProduction = data.getFarmDropProduction(Farm, Drop);

			if(set >= FarmDropProduction) {
				error.ErrorSender(p, "");
				return;
			}
		}
		
		inv.setItem(23, customItem(Material.GREEN_SHULKER_BOX, "§a§l>>>:§r§e " + Money));
		inv.setItem(29, customItem(Material.IRON_BARS, "§7| " + set));
		
		playerdata.soundgood(p);
	}
		
	public void InventoryAnimation(Main main) {
		
        Bukkit.getScheduler().scheduleSyncRepeatingTask(main, (Runnable)new Runnable() {
            @Override
            public void run() {
            	
            	try {
            		List<String> guis = new ArrayList<>(GUI.keySet());
            		
                	for(int i = 0; i != guis.size(); i++) {
                		String key = guis.get(i);
                		String Type = key.split("/")[0];
                		
                		switch(Type) {
                		case "market":
                			MarketAnimation(GUI.get(key));
                			break;
                		case "info":
                			break;
                		case "buy":
                			break;
                		case "sell":
                			break;
                		case "buyitem":
                			break;
                		case "sellitem":
                			break;
                		default:
                			break;
                		}
                		
                	}
                	
            	}catch(Exception ex) {};
            	
            }
            
        }, 0L, 20L);
        
	}
	
	public void MarketAnimation(Inventory inv) {
		data = new FarmLandDataBaseManager();
		
		String Farm = inv.getItem(4).getItemMeta().getDisplayName().split(" ")[1];
		
		if(data.isMarketBuys(Farm)) {
			List<String> Buys = data.getMarketBuys(Farm);
			Random rand = new Random(); 
			int random = rand.nextInt(Buys.size());
			inv.setItem(20, data.getDropwithName(Buys.get(random)));
		}
		if(data.isMarketSells(Farm)) {
			List<String> Sells = data.getMarketSells(Farm);
			Random rand = new Random(); 
			int random = rand.nextInt(Sells.size());
			inv.setItem(20, data.getDropwithName(Sells.get(random)));
		}
		
	}
	
}

abstract class BaseInventory {
	ErrorManager error;
    MarketManager market;
    PlayerDataBaseMananger playerdata;
	FarmLandDataBaseManager data;
	ConfigManager config;
	String Farm;
	Inventory inv;
	Player p;

	void set(String Name, int size) {
		inv = Bukkit.createInventory(p, size, Name);

		for(int i = 0; i != size; i++) {
			inv.setItem(i, new ItemStack(Material.LIME_STAINED_GLASS_PANE));
		}
		inv.setItem(4, customItem(Material.STONE_HOE, "§a| " + Farm));
		
	}
	
	static ItemStack customItem(Material Material, String Name) {
		ItemStack Item = new ItemStack(Material);
		ItemMeta meta = Item.getItemMeta();
		meta.setDisplayName(Name);
		Item.setItemMeta(meta);
		return Item;
	}

}
