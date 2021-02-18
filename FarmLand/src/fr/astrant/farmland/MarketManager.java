package fr.astrant.farmland;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.milkbowl.vault.economy.Economy;

public class MarketManager {
	FarmLandDataBaseManager data;
	ErrorManager error;
	PlayerDataBaseMananger playerdata;
	ConfigManager config;
	Economy eco;
	
	MarketManager() {
		eco = Main.economy;
	}
	
	public double getFarmBuyCost(String Farm, String Drop, int Quantity) {
		data = new FarmLandDataBaseManager();
		config = new ConfigManager();
		
		Double Price = data.getMarketBuyMoney(Farm, Drop);
		Double DropNumber = data.getTotalDropNumber(Drop);
		List<String> Farms = data.getFarms();
		int FarmSize = Farms.size();	
		Double Needed = DropNumber/FarmSize;
		Double Rarity = (double) (DropNumber/Needed);
		
		if(Rarity > config.getMaxRarity()) {
			Rarity = config.getMaxRarity();
		}
		
		if(data.isFarmDropProduction(Farm, Drop)){
			Double Have = data.getFarmDropProduction(Farm, Drop);
			Needed -= Have;
		}
		
		double Cost = Price/Rarity;
		
		return Cost*Quantity;
	}
	
	public double getFarmSellCost(String Farm, String Drop, int Quantity) {
		data = new FarmLandDataBaseManager();
		config = new ConfigManager();

		Double Price = data.getMarketSellMoney(Farm, Drop);
		Double DropNumber = data.getFarmDropProduction(Farm, Drop);
		Double Moy = data.getMarketSellMoy(Farm, Drop);
		Double Rarity = DropNumber/Moy;
		
		if(Rarity > config.getMaxRarity()) {
			Rarity = config.getMaxRarity();
		}
		
		double Cost = Price/Rarity;
		return Cost*Quantity;
	}
	
	public void FarmBuy(String Farm, String Drop, int Quantity, Player p) {
		data = new FarmLandDataBaseManager();
		playerdata = new PlayerDataBaseMananger();
		error = new ErrorManager();
		
		p.closeInventory();
		
		ItemStack Item = data.getDropwithName(Drop);
		int Amount = Item.getAmount();
		Inventory inv = p.getInventory();
		int InvSize = inv.getSize();
		int Total = Quantity*Amount;
		double Cost = getFarmBuyCost(Farm, Drop, Quantity);
		double Money = data.getMoney(Farm);
		
    	if(Money < Cost) {
			error.ErrorSender(p, "Farm.Money");
        	return;
    	}
    	
        for (int i = 0; i != InvSize; i++) {
        	
        	try {
        		ItemStack here = inv.getItem(i);
            	if(here.isSimilar(Item)) {	
            		Total -= here.getAmount();

            	}
        	}catch(Exception ex) {}
            
        }
        
        if(Total >= 1) {
			error.ErrorSender(p, "Buy.NotEnough");
        }else {
        	playerdata.msg(p, "");
        	
        	int invSize = inv.getSize();
        	
        	for( int i = 0; i != invSize; i++) {
            	ItemStack here = inv.getItem(i);
            	
            	try {
                	if(here.isSimilar(Item)) {
                		int tot = Quantity*Amount;
                		int N = here.getAmount();
                		if(tot - N < 0) {
                			here.setAmount(N - tot);
                			inv.setItem(i, here);

                			return;
                		}else {
                			inv.setItem(i, null);
                			tot -= here.getAmount();
                		}
                		
                	}
            	}catch(Exception ex) {}

        	}

        	eco.depositPlayer(p, Cost);
        }
		p.closeInventory();
		
	}
	
	public void FarmSell(String Farm, String Drop, int Quantity, Player p) {
		data = new FarmLandDataBaseManager();
		playerdata = new PlayerDataBaseMananger();
		error = new ErrorManager();
				
		if(Quantity < 1) {
			error.ErrorSender(p, "Sell.IsPositive");
			return;
		}
		
		Double Farmdrops = data.getFarmDropProduction(Farm, Drop);
		ItemStack Item = data.getDropwithName(Drop);
		Inventory inv = p.getInventory();
		double Cost = getFarmSellCost(Farm, Drop, Quantity);
		int Space = 0;
		
		Inventory Pinv = p.getInventory();
		
		for(int i = 0; i != 36; i++) {
			try {
				ItemStack item = Pinv.getItem(i);
				
				if(item.isSimilar(Item)) {
					Space += Item.getMaxStackSize() - item.getAmount();
				}
				
			}catch(NullPointerException ex) {
				Space += Item.getMaxStackSize();
			};
			
		}

		if(Space >= Quantity) {
			
	    	if(eco.getBalance(p) >= Cost) {
	        	
	        	if(Quantity <= Farmdrops) {
	            	playerdata.msg(p, "");
	            	data.setFarmDropProduction(Farm, Drop, Farmdrops-Quantity);
	            	data.addBank(Farm, Cost);
	            	
	            	for( int i = 0; i != Quantity; i++) {
	                	inv.addItem(Item);

	            	}
	            	
	        	}else {
	            	error.ErrorSender(p, "Sell.NotEnough");

	        	}
	        	
	    	}else {
	        	error.ErrorSender(p, "Player.Money");

	    	}
	    	
		}else {
        	error.ErrorSender(p, "Player.Space");

    	}

		p.closeInventory();
        
	}

}
