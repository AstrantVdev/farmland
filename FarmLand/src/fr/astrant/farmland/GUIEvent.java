package fr.astrant.farmland;

import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GUIEvent implements Listener{
	FarmLandDataBaseManager data;
	GuiManager gui;
	MarketManager market;
	PlayerDataBaseMananger playerdata;
	
	Player p;
	Inventory inv;
	String InvName;
	String Slot;
	String Farm;
	ItemStack clicked;
	ClickType Click;
	
	@EventHandler
	public void onClic(InventoryClickEvent e) {
		gui = new GuiManager();

		p = (Player) e.getWhoClicked();
		Click = e.getClick();
		String uuid = p.getUniqueId().toString();
		inv = p.getOpenInventory().getTopInventory();
		InvName = p.getOpenInventory().getTitle();
		Slot = String.valueOf(e.getSlot());
		clicked = e.getCurrentItem();
		HashMap<String, Inventory> GUI = GuiManager.GUI;
		List<String> GUITypes = gui.GUITypes;
		
		try {
			Farm = inv.getItem(4).getItemMeta().getDisplayName().split(" ")[1];
		}catch(Exception ex) { }
				
		if(GUI.containsValue(inv)) {
			e.setCancelled(true);

			for(int i = 0; i != GUITypes.size(); i++) {

				String Type = GUITypes.get(i);
				if(inv.equals(GUI.get(Type + "/" + uuid))) {

					switch(Type) {
					case "market":
						e.setCancelled(true);
						Market();
						break;
					case "info":
						e.setCancelled(true);
						Info();
						break;
					case "buy":
						e.setCancelled(true);
						Buy();
						break;
					case "sell":
						e.setCancelled(true);
						Sell();
						break;
					case "buyitem":
						e.setCancelled(true);
						BuyItem();
						break;
					case "sellitem":
						e.setCancelled(true);
						SellItem();
						break;
					case "blocksmenu":
						e.setCancelled(true);
						BlocksMenu();
						break;
					case "blockmenu":
						e.setCancelled(true);
						BlockMenu();
						break;
					case "toolsmenu":
						e.setCancelled(true);
						ToolsMenu();
						break;
					case "blocktooldropsmenu":
						e.setCancelled(true);
						BlockToolDropsMenu();
						break;
					case "dropmenu":
						e.setCancelled(true);
						DropMenu();
						break;
					case "dropsmenu":
						e.setCancelled(true);
						DropsMenu();
						break;
					default:
						break;
					}
					
				}
			}
		}

	}
	
	public void onInventoryClose(InventoryCloseEvent e) {
		gui = new GuiManager();
		HashMap<String, Inventory> GUI = GuiManager.GUI;
		Inventory inv = e.getInventory();
		List<String> GUITypes = gui.GUITypes;
		Player p = (Player) e.getPlayer();
		String uuid = p.getUniqueId().toString();
		
		if(GUI.containsValue(inv)) {
			
			for(int i = 0; i != GUITypes.size(); i++) {
				String Type = GUITypes.get(i);
				GUI.remove(Type + "/" + uuid);
				
			}
			
		}
		
	}
	
	public void Market(){
		data = new FarmLandDataBaseManager();
		gui = new GuiManager();
		playerdata = new PlayerDataBaseMananger();
		
		switch(Slot) {
		case "20":
			gui.openGUI(p, new String[]{"buy", Farm, "1"});
			break;
		case "24":
			gui.openGUI(p, new String[]{"sell", Farm, "1"});
			break;
		default:
			playerdata.soundbad(p);
			break;
		}
	}
	
	public void Info(){
		data = new FarmLandDataBaseManager();
		gui = new GuiManager();
		playerdata = new PlayerDataBaseMananger();

		switch(Slot) {
		case "0":
			gui.openGUI(p, new String[]{"market", Farm});
			break;
		case "39":
			gui.openGUI(p, new String[]{"blocksmenu", Farm, "1"});
			break;
		case "40":
			gui.openGUI(p, new String[]{"dropsmenu", Farm, "1"});
			break;
		default:
			playerdata.soundbad(p);
			break;
		}
	}
	
	public void BlockMenu(){
		data = new FarmLandDataBaseManager();
		gui = new GuiManager();
		playerdata = new PlayerDataBaseMananger();
		
		switch(Slot) {
		case "0":
			gui.openGUI(p, new String[]{"blocksmenu", Farm, "1"});
			break;
		case "23":
			String Block = inv.getItem(5).getItemMeta().getDisplayName().split(" ")[1];
			gui.openGUI(p, new String[]{"toolsmenu", Farm, Block, "1"});
			break;
		default:
			playerdata.soundbad(p);
			break;
		}
	}
	
	public void DropMenu(){
		data = new FarmLandDataBaseManager();
		gui = new GuiManager();
		playerdata = new PlayerDataBaseMananger();
		
		switch(Slot) {
		case "0":
			try {
				String Block = inv.getItem(5).getItemMeta().getDisplayName().split(" ")[1];
				String Tool = inv.getItem(6).getItemMeta().getDisplayName().split(" ")[1];
				gui.openGUI(p, new String[]{"blocktooldropsmenu", Farm, Block, Tool, "1"});

			}catch(Exception ex) {
				gui.openGUI(p, new String[]{"dropsmenu", Farm, "1"});

			}
			break;
		default:
			playerdata.soundbad(p);
			break;
		}
	}
	
	public void ToolsMenu(){
		data = new FarmLandDataBaseManager();
		gui = new GuiManager();
		playerdata = new PlayerDataBaseMananger();
		String Block = inv.getItem(5).getItemMeta().getDisplayName().split(" ")[1];

		switch(Slot) {
		case "0":
			gui.openGUI(p, new String[]{"blockmenu", Farm, Block});
			break;
		default:
			if(Integer.valueOf(Slot) > 8 && Integer.valueOf(Slot) < 53) {
				if(!clicked.getType().equals(org.bukkit.Material.AIR)) {
					String Tool = data.getToolName(clicked);
					gui.openGUI(p, new String[]{"blocktooldropsmenu", Farm, Block, Tool, "1"});
					return;
				}
			}
			playerdata.soundbad(p);
			break;
		}
	}
	
	public void BlocksMenu(){
		data = new FarmLandDataBaseManager();
		gui = new GuiManager();
		playerdata = new PlayerDataBaseMananger();
		
		switch(Slot) {
		case "0":
			gui.openGUI(p, new String[]{"info", Farm});
			break;
		default:
			if(Integer.valueOf(Slot) > 8 && Integer.valueOf(Slot) < 53) {
				if(!clicked.getType().equals(org.bukkit.Material.AIR)) {
					String Block = clicked.getType().toString();
					gui.openGUI(p, new String[]{"blockmenu", Farm, Block});
					return;
				}
			}
			playerdata.soundbad(p);
			break;
		}
	}
	
	public void BlockToolDropsMenu(){
		data = new FarmLandDataBaseManager();
		gui = new GuiManager();
		playerdata = new PlayerDataBaseMananger();
		
		String Block = inv.getItem(5).getItemMeta().getDisplayName().split(" ")[1];

		switch(Slot) {
		case "0":
			gui.openGUI(p, new String[]{"toolsmenu", Farm, Block, "1"});
			break;
		default:
			if(Integer.valueOf(Slot) > 8 && Integer.valueOf(Slot) < 53) {
				if(!clicked.getType().equals(org.bukkit.Material.AIR)) {
					String Drop = data.getDropName(clicked);
					String Tool = inv.getItem(6).getItemMeta().getDisplayName().split(" ")[1];
					gui.openGUI(p, new String[]{"dropmenu", Farm, Block, Tool, Drop});
					return;
				}
			}
			playerdata.soundbad(p);
			break;
		}
	}
	
	public void DropsMenu(){
		data = new FarmLandDataBaseManager();
		gui = new GuiManager();
		playerdata = new PlayerDataBaseMananger();
		
		switch(Slot) {
		case "0":
			gui.openGUI(p, new String[]{"info", Farm});
			break;
		default:
			if(Integer.valueOf(Slot) > 8 && Integer.valueOf(Slot) < 53) {
				if(!clicked.getType().equals(org.bukkit.Material.AIR)) {
					String Drop = data.getDropName(clicked);
					gui.openGUI(p, new String[]{"dropmenu", Farm, "null", "null", Drop});
					return;
				}
			}
			playerdata.soundbad(p);
			break;
		}
	}
	
	public void Sell(){
		data = new FarmLandDataBaseManager();
		gui = new GuiManager();
		playerdata = new PlayerDataBaseMananger();
		
		switch(Slot) {
		case "0":
			gui.openGUI(p, new String[]{"market", Farm});
			break;
		default:
			if(Integer.valueOf(Slot) > 8 && Integer.valueOf(Slot) < 53) {
				if(!clicked.getType().equals(org.bukkit.Material.AIR)) {
					String Drop = data.getDropName(clicked);
					gui.openGUI(p, new String[]{"sellitem", Farm, Drop});
					return;
				}
			}
			playerdata.soundbad(p);
			break;
		}
	}
	
	public void Buy(){
		data = new FarmLandDataBaseManager();
		gui = new GuiManager();
		playerdata = new PlayerDataBaseMananger();
		
		switch(Slot) {
		case "0":
			gui.openGUI(p, new String[]{"market", Farm});
			break;
		default:
			if(Integer.valueOf(Slot) > 8 && Integer.valueOf(Slot) < 53) {
				
				try {
					if(!clicked.getType().equals(org.bukkit.Material.AIR)) {
						String Drop = data.getDropName(clicked);
						gui.openGUI(p, new String[]{"buyitem", Farm, Drop});
						return;
					}
				}catch(Exception ex) {};

			}
			playerdata.soundbad(p);
			break;
		}
	}
	
	public void BuyItem(){
		data = new FarmLandDataBaseManager();
		gui = new GuiManager();
		playerdata = new PlayerDataBaseMananger();
		market = new MarketManager();

		switch(Slot) {
		case "0":
			gui.openGUI(p, new String[]{"buy", Farm, "1"});
			break;
		case "23":
			int Quantity = Integer.valueOf(inv.getItem(29).getItemMeta().getDisplayName().split(" ")[1]);
			ItemStack drop = inv.getItem(20);
			String Drop = data.getDropName(drop);
			market.FarmBuy(Farm, Drop, Quantity, p);
			break;
		case "29":
			ItemStack drop2 = inv.getItem(20);

			if(Click.equals(ClickType.LEFT)) {
				gui.setItemQuantity(inv, 1, p, "BUY", drop2);
			}else {
				gui.setItemQuantity(inv, -1, p, "BUY", drop2);
			}
			break;

		default:
			playerdata.soundbad(p);
			break;
		}
	}
	
	public void SellItem(){
		data = new FarmLandDataBaseManager();
		gui = new GuiManager();
		playerdata = new PlayerDataBaseMananger();
		market = new MarketManager();
		
		switch(Slot) {
		case "0":
			gui.openGUI(p, new String[]{"sell", Farm, "1"});
			break;
		case "23":
			int Quantity = Integer.valueOf(inv.getItem(29).getItemMeta().getDisplayName().split(" ")[1]);
			ItemStack drop = inv.getItem(20);
			String Drop = data.getDropName(drop);
			market.FarmSell(Farm, Drop, Quantity, p);
			break;
		case "29":
			ItemStack drop2 = inv.getItem(20);

			if(Click.equals(ClickType.LEFT)) {
				gui.setItemQuantity(inv, 1, p, "SELL", drop2);
			}else {
				gui.setItemQuantity(inv, -1, p, "SELL", drop2);
			}
			break;
		default:
			playerdata.soundbad(p);
			break;
		}
	}

}
