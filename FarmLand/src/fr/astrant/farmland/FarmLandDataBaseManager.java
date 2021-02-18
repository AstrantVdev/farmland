package fr.astrant.farmland;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class FarmLandDataBaseManager {
	private YamlConfiguration config = Main.FarmLandconfig;

	public void addPlot(String Farm, String PlotKey) {
		if(isFarmPlots(Farm)) {
			List<String> Plots = getFarmPlots(Farm);
			Plots.add(PlotKey);
			config.set("Farms." + Farm + ".Plots", Plots);

		}else{
			config.set("Farms." + Farm + ".Plots", PlotKey);
		}
		config.set("Plots." + PlotKey, Farm);
		save();
	}
	
	public void removeFarm(String Farm) {
		config.set("Farms." + Farm, null);	
		save();
		
		if(!isFarms()) {
			config.set("Farms", null);	
			save();
		}
	}
	
	public void removePlot(String Farm, String PlotKey) {
		if(isFarmPlots(Farm)) {
			config.set("Farms." + Farm + ".Plots", getFarmPlots(Farm).remove(PlotKey));

		}else{
			config.set("Farms." + Farm + ".Plots", null);

		}
		config.set("Plots." + PlotKey, null);
		save();
		
		if(!isPlots()) {
			config.set("Plots", null);	
			save();
		}
	}
	
	public String getPlotFarm(String PlotKey) {
		return config.getString("Plots." + PlotKey);
	}
	
	public int getDropNumber(String Drop, int Day) {
		return config.getInt("Drops." + Drop + ".Number." + Day);
	}
	
	public List<String> getDropNumbers(String Drop) {
		return config.getStringList("Drops." + Drop + ".Number");
	}
	
	public Double getTotalDropNumber(String Drop) {
		List<String> DropNumbers = getDropNumbers(Drop);
		Double Total = 0.0;
		
		for (int i = 0; i != DropNumbers.size(); i++) {
			int DropNumber = getDropNumber(Drop, Integer.valueOf(DropNumbers.get(i)));
			Total += DropNumber;
			
		}
		
		return Total;
	}
	
	public void setDropNumber(String Drop, int Number, int Day) {
		config.set("Drops." + Drop + ".Number." + Day + "." + Drop, Number);
		save();
	}
	
	public void addDropNumber(String Drop, int Number) {
		config.set("Drops." + Drop + ".Number.1", getDropNumber(Drop, 1) + Number);
		save();
	}
	
	public void withdrawDropNumber(String Drop, int Number) {
		config.set("Drops." + Drop + ".Number.1", getDropNumber(Drop, 1) - Number);
		save();
	}
	
	public boolean isDropNumber(String Drop) {
		try {
			if(getDropNumber(Drop, 1) != 0) {
				return true;
			}
			return false;
		}catch(Exception e) {
			return false;
		}
	}
	
	public Double getFarmDropProduction(String Farm, String Drop) {
		return config.getDouble("Farms." + Farm + ".DropsProduction." + Drop);
	}
	
	public boolean isFarmDropProduction(String Farm, String Drop) {
		try {
			if(getFarmDropProduction(Farm, Drop) != 0) {
				return true;
			}
			return false;
		}catch(Exception e) {
			return false;
		}
	}
	
	public List<String> getFarmDropsProduction(String Farm) {
		return new ArrayList<>(config.getConfigurationSection("Farms." + Farm + ".DropsProduction").getKeys(false));
	}
	
	public boolean isFarmDropsProduction(String Farm) {
		try{
			if(!getFarmDropsProduction(Farm).equals(null)) {
				return true;
			}
			return false;
		}catch(Exception ex) {
			return false;
		}
	}
	
	public void setFarmDropProduction(String Farm, String Drop, Double newNumber) {
		if(newNumber == 0.0) {
			config.set("Farms." + Farm + ".DropsProduction." + Drop, null);
		}else {
			config.set("Farms." + Farm + ".DropsProduction." + Drop, newNumber);
		}
		save();
	}
	
	public void addFarmDropProduction(String Farm, String Drop, int Number) {
		config.set("Farms." + Farm + ".DropsProduction." + Drop, getFarmDropProduction(Farm, Drop) + Number);
		save();
	}
	
	public void withdrawFarmDropProduction(String Farm, String Drop, int Number) {
		config.set("Farms." + Farm + ".DropsProduction." + Drop, getFarmDropProduction(Farm, Drop) - Number);
		save();
	}
	
	public List<String> getPlots() {
		return new ArrayList<String>(config.getConfigurationSection("Plots").getKeys(false));
	}
	
	public boolean isPlot(String PlotKey) {
		if(isPlots()) {
			if (getPlots().contains(PlotKey)) {
				return true;
			}
			return false;
		}
		return false;
	}
	
	public boolean isFarm(String Farm) {
		if(isFarms()) {
			if (getFarms().contains(Farm)) {
				return true;
			}else {
				return false;
			}
		}else {
			return false;
		}

	}
	
	public void setFarmerName(String Farm, String id, String Name) {
		config.set("Farms." + Farm + ".Farmers." + id + ".Name", Name);
		save();
	}
	
	public void setFarmerTypes(String Farm, String id, List<String> Types) {
		config.set("Farms." + Farm + ".Farmers." + id + ".Type", Types);
		save();
	}
	
	public boolean isFarmer(String id) {
		if(!isFarms()) {
			return false;
		}
		try {
			if(!getFarmers().equals(null)) {
				return true;
			}
			return false;
		}catch(Exception ex){
			return false;
		}
	}
	
	public boolean isFarmFarmers(String Farm) {
		try {
			if(!getFarmFarmers(Farm).equals(null)) {
				return true;
			}
			return false;
		}catch(Exception ex){
			return false;
		}
	}
	
	public boolean isFarmFarmer(String Farm, String Farmer) {
		if(!isFarmFarmers(Farm)) {
			return false;
		}
		if(getFarmFarmers(Farm).contains(Farmer)) {
			return true;
		}
		return false;
	}
	
	public String getFarmerFarm (String id) {
		List<String> Farms = getFarms();
		
		for(int f = 0; f != Farms.size(); f++) {
			String Farm = Farms.get(f);
			if(hasFarmers(Farm)) {
				if(getFarmFarmers(Farm).contains(id)) {
					return Farm;

				}
			}

		}
		return null;
	}
	
	public List<String> getFarmers() {
		List<String> Farms = getFarms();
		List<String> Farmers = new LinkedList<String>();
		
		for(int f = 0; f != Farms.size(); f++) {
			String Farm = Farms.get(f);
			
			if(isFarmFarmers(Farm)) {
				List<String> farmers = getFarmFarmers(Farm);				
				Farmers.addAll(farmers);
			}

		}
		return Farmers;

	} 
	
	public void addFarmerType(String Farm, String id, String Type) {
		if(isFarmerTypes(Farm, id)) {
			config.set("Farms." + Farm + ".Farmers." + id + ".Type", getFarmerTypes(Farm, id).add(Type));

		}else {
			config.set("Farms." + Farm + ".Farmers." + id + ".Type", Type);

		}
		save();
	}
	
	public boolean isFarmerTypes(String Farm, String id) {
		if(!isFarmer(id)) {
			return false;	
		}
		try {
			if(!getFarmerTypes(Farm, id).equals(null)) {
				return true;
			}
			return false;
		}catch(Exception ex) {
			return false;
		}
	}	
	
	public List<String> getFarmerTypes(String Farm, String id) {
		return config.getStringList("Farms." + Farm + ".Farmers." + id + ".Type");
	}
	
	public void removeFarmer(String Farm, String id) {
		config.set("Farms." + Farm + ".Farmers." + id, null);
		save();
	}
	
	public void addFarmerSell(String Farm, String Farmer) {
		config.set("Farms." + Farm + ".Farmers", getFarmFarmers(Farm).add(Farmer));
		save();
	}
	
	public void addFarmerBuy(String Farm, String Farmer, String Drop) {
		config.set("Farms." + Farm + ".Farmers", getFarmFarmers(Farm).add(Farmer));
		save();
	}
	
	public List<String> getFarmFarmers(String Farm){
		return new ArrayList<>(config.getConfigurationSection("Farms." + Farm + ".Farmers").getKeys(false));
	}
	
	public boolean hasFarmers (String Farm) {
		try {
			if(!getFarmFarmers(Farm).equals(null)) {
				return true;
			}
			return false;
		}catch(Exception ex) {
			return false;
		}
		
	}
	
	public boolean isBreackable(String Farm, Block block) {
		String Type = block.getType().toString();
		if(!isBlocks(Farm)) {
			return false;
		}
		if (config.getConfigurationSection("Farms." + Farm + ".Blocks").contains(Type)) {
			return true;
		}else {
			return false;
		}
	}
	
	public void addBlock(String Farm, String Block, String Tool, String Drop, int Chance, int Cooldown, String Particle) {
		config.set("Farms." + Farm + ".Blocks." + Block + ".Tools." + Tool + "." + Drop, Chance);
		config.set("Farms." + Farm + ".Blocks." + Block + ".Particle", Particle);
		config.set("Farms." + Farm + ".Blocks." + Block + ".Cooldown", Cooldown);
		save();
	}
	
	public String getLocKey(Location loc) {
		String World = loc.getWorld().getName();
		int X = loc.getBlockX();
		double Y = loc.getY();
		double Z = loc.getZ();
		return (World + "/" + X + "/" + Y + "/" + Z);
	}
	
	public Location getLocation (String LocKey) {
		String[] split = LocKey.split("/");
		World world = Bukkit.getWorld(split[0]);
		int X = Integer.valueOf(split[1]);
		double Y = Double.valueOf(split[2]);
		double Z = Double.valueOf(split[3]);
		return new Location(world, X, Y, Z);
	}
	
	public void setSpawn(String Farm, Location loc) {
		String LocKey = getLocKey(loc);
		config.set("Farms." + Farm + ".Spawn", LocKey);
		save();
	}
	
	public String getSpawn(String Farm) {
		return config.getString("Farms." + Farm + ".Spawn");
	}
	
	public boolean getTitle(String Farm) {
		return config.getBoolean("Farms." + Farm + ".Title");
	}
	
	public void setTitle(String Farm, Boolean type) {
		config.set("Farms." + Farm + ".Title", type);
		save();
	}
	
	public void removeBlockDrop(String Farm, String Block, String Tool, String Drop) {
		config.set("Farms." + Farm + ".Blocks." + Block + ".Tools." + Tool + "." + Drop, null);
		save();
		if(!hasBlockToolsDrops(Farm, Block, Tool)) {
			config.set("Farms." + Farm + ".Blocks." + Block + ".Tools." + Tool, null);
			save();
		}
		if(!hasBlockTools(Farm, Block)) {
			config.set("Farms." + Farm + ".Blocks." + Block, null);
			save();
		}
	}
	
	public void setBlockDropChance(String Farm, String Block, String Tool, String Drop, int Chance) {
		config.set("Farms." + Farm + ".Blocks." + Block + "." + Tool + "." + Drop, Chance);
		save();
	}
	
	public int getBlockDropChance(String Farm, String Block, String Tool, String Drop) {
		return config.getInt("Farms." + Farm + ".Blocks." + Block + ".Tools." + Tool + "." + Drop);
	}
	
	public void setBlockParticle(String Farm, String Block, String particle) {
		config.set("Farms." + Farm + ".Blocks." + Block + ".Particle", particle);
		save();
	}
	
	public void setBlockCooldown(String Farm, String Block, int Cooldown) {
		config.set("Farms." + Farm + ".Blocks." + Block + ".Cooldown", Cooldown);
		save();
	}
	
	public void removeBlock(String Farm, String Block) {
		config.set("Farms." + Farm + ".Blocks." + Block, null);
		save();
	}
	
	public boolean isBlock(String Farm, String Block) {
		if(!isBlocks(Farm)) {
			return false;
		}
		
		if(getBlocks(Farm).contains(Block)){
			return true;
		}else {
			return false;
		}
	}
	
	public boolean isBlocks (String Farm) {
		try {
			if(!getBlocks(Farm).equals(null)) {
				return true;
			}
			return false;
		}catch(Exception e) {
			return false;
		}
	}
	
	public boolean isBlockTool(String Farm, String Block, String Tool) {
		if(getBlockTools(Farm, Block).contains(Tool)){
			return true;
		}else {
			return false;
		}
	}
	
	public boolean isBlockToolDrop(String Farm, String Block, String Tool, String Drop) {
		if(getBlocks(Farm).contains(Block)){
			return true;
		}else {
			return false;
		}
	}
		
	public List<String> getBlocks(String Farm) {
		return new ArrayList<>(config.getConfigurationSection(("Farms." + Farm + ".Blocks")).getKeys(false));
	}
	
	public boolean hasBlocks(String Farm) {
		try {
			if(!getBlocks(Farm).equals(null)) {
				return true;
			}
			return false;
		}catch(Exception ex) {
			return false;
		}
	}
	
	public List<String> getBlockDrops(String Farm, String Block, String Tool) {
		return new ArrayList<>(config.getConfigurationSection(("Farms." + Farm + ".Blocks." + Block + ".Tools." + Tool)).getKeys(false));
	}
	
	public void setBlockParticle(String Farm, String block, int Cooldown) {
		config.set("Farms." + Farm + ".Blocks." + block + ".Cooldown", Cooldown);
		save();
	}
	
	public Particle getBlockParticle(String Farm, String block) {
		return (Particle.valueOf(config.getString("Farms." + Farm + ".Blocks." + block + ".Particle")));
	}
	
	public int getBlockCooldown(String Farm, String block) {
		return (config.getInt("Farms." + Farm + ".Blocks." + block + ".Cooldown"));
	}
	
	public List<String> getFarms() {
		return new ArrayList<String>(config.getConfigurationSection("Farms").getKeys(false));
	}
	
	public boolean isFarms() {
		try {
			List<String> list = getFarms();
			
			if(!list.equals(null)) {
				return true;
			}
			return false;
		}catch(Exception e) {
			return false;
		}
		
	}
	
	public boolean isTool(ItemStack tool, String Farm, String Block) {
		if(isBlockTools(Farm, Block)) {
			for(int i = 0; i != getBlockTools(Farm, Block).size(); i++) {
				if(getToolwithName(getBlockTools(Farm, Block).get(i)).isSimilar(tool)) {
					return true;
				}
			}
		}

		return false;

	}
	
	public boolean isBlockTools(String Farm, String Block) {
		try {
			if(!getBlockTools(Farm, Block).equals(null)) {
				return true;
			}
			return false;
		}catch(Exception ex) {
			return false;
		}
	}
	
	public String getToolName(ItemStack tool) {
		List<String> ToolsNames = getToolsNames();
		for(int i = 0; i != ToolsNames.size(); i++) {
			String Name = ToolsNames.get(i);
			
			if(config.getItemStack("Tools." + Name).equals(tool)) {
				return ToolsNames.get(i);
			}
			
		}
		return null;
	}
	
	public List<String> getToolsNames(){
		return new ArrayList<>(config.getConfigurationSection("Tools").getKeys(false));
	}
	
	public boolean isTools() {
		try {
			List<String> list = getToolsNames();
			
			if(!list.equals(null)) {
				return true;
			}
			return false;
		}catch(Exception e) {
			return false;
		}
		
	}
	
	public boolean isDrops() {
		try {
			List<String> list = getDropsNames();
			
			if(!list.equals(null)) {
				return true;
			}
			return false;
		}catch(Exception e) {
			return false;
		}
		
	}
	
	public boolean isDropEvents(String Drop) {
		try {
			List<String> list = getDropEvents(Drop);
			
			if(!list.equals(null)) {
				return true;
			}
			return false;
		}catch(Exception e) {
			return false;
		}
		
	}
	
	public String getDropKey(String Farm, String blocktype, String toolname) {
		return config.getString("Farms" + Farm + "." + blocktype + "." + toolname);
	}
	
	public boolean hasDropEvent(String drop) {
		try {
			if(!getDropEvents(drop).equals(null)) {
				return true;
			}
		}catch(Exception e) {
			return false;

		}
		return false;
	}
	
	public List<String> getDropEvents(String Drop){
		List<String> DropEventIndex = new ArrayList<>(config.getConfigurationSection("Drops."  + Drop + ".Events").getKeys(false));
		List<String> DropEvents = new ArrayList<>();

		for(int i = 1; i != DropEventIndex.size() + 1; i++) {
			String Event = getEventType(Drop, i);
			DropEvents.add(Event);
		}
		
		return DropEvents;
	}
	
	public String getEventValue(String Drop, int Index) {
		return config.getString("Drops."  + Drop + ".Events." + Index + "." + getEventType(Drop, Index));
	}
	
	public String getEventType(String Drop, int Index) {
		return new ArrayList<String>(config.getConfigurationSection("Drops."  + Drop + ".Events." + Index).getKeys(false)).get(0);
	}
		
	public void addDropEvent(String Drop, String Event, String Value) {
		if(isDropEvents(Drop)) {
			config.set("Drops." + Drop + ".Events." + (getDropEvents(Drop).size() + 1) + "." + Event, Value);
		}else {
			config.set("Drops." + Drop + ".Events." + 1 + "." + Event, Value);
		}
		save();
	}
	
	public void setDropEvent(String Drop, int Index, String Event, String Value) {
		config.set("Drops." + Drop + ".Events." + Index + "." + Event, Value);
		save();
	}
	
	public void removeDropEvent(String drop, int Index) {
		config.set("Drops." + drop + ".Events." + Index, null);
		save();
	}
	
	public ItemStack getDropwithName(String Name) {
		return config.getItemStack("Drops." + Name + ".Type");
	}
	
	public ItemStack getToolwithName(String name) {
		return config.getItemStack("Tools." + name);
	}
	
	public List<String> getBlockTools(String Farm, String Block) {
		return new ArrayList<>(config.getConfigurationSection("Farms." + Farm + ".Blocks." + Block + ".Tools").getKeys(false));
	}
	
	public List<String> getBlockToolsDrops(String Farm, String Block, String Tool) {
		return new ArrayList<>(config.getConfigurationSection("Farms." + Farm + ".Blocks." + Block + ".Tools." + Tool).getKeys(false));
	}
	
	public boolean hasBlockTools(String Farm, String Block) {
		try {
			if(!getBlockTools(Farm, Block).equals(null)) {
				return true;
			}else {
				return false;
			}
			
		}catch(Exception ex) {
			return false;
		}
	}
	
	public boolean hasBlockToolsDrops(String Farm, String Block, String Tool) {
		try {
			if(!getBlockToolsDrops(Farm, Block, Tool).equals(null)) {
				return true;
			}else {
				return false;
			}
			
		}catch(Exception ex) {
			return false;
		}
	}
	
	public List<ItemStack> getTools(){
		List<String> list = getToolsNames();
		List<ItemStack> tools = new LinkedList<>();
		
		for(int i = 0; i != list.size(); i++) {
			tools.add(config.getItemStack("Tools." + list.get(i)));
		}
		return tools;
	}
		
	public void addTool(String Name, ItemStack Tool) {
		config.set("Tools." + Name, Tool);
		save();
	}
	
	public void removeTool(String Name) {
		config.set("Tools." + Name, null);
		save();
	}
	
	public void removeDrop(String Name) {
		config.set("Drops." + Name, null);
		save();
	}
	
	public void addDrop(String Name, ItemStack Drop) {
		config.set("Drops." + Name + ".Type", Drop);
		save();
	}
	
	public boolean isDropName(String Drop) {
		if(isDrops()) {
			if (getDropsNames().contains(Drop)) {
				return true;
			}else {
				return false;
			}
		}
		return false;
	}
	
	public boolean isDrop(ItemStack Drop) {
		if(isDrops()) {
			if (getDrops().contains(Drop)) {
				return true;
			}else {
				return false;
			}
		}
		return false;
	}
	
	public List<String> getDropsNames() {
		return new ArrayList<String>(config.getConfigurationSection("Drops").getKeys(false));
	}
	
	public String getDropName(ItemStack Drop) {
		List<String> DropsNames = getDropsNames();
		
		for(int i = 0; i != DropsNames.size(); i++) {
			String Name = DropsNames.get(i);
			if(getDropwithName(Name).equals(Drop)) {
				return Name;
			}
		}
		return null;
	}
		
	public List<ItemStack> getDrops(){
		List<String> list = getDropsNames();
		List<ItemStack> drops = new LinkedList<>();
		
		for(int i = 0; i != list.size(); i++) {
			drops.add(config.getItemStack("Drops." + list.get(i)));
		}
		return drops;
	}
	
	public boolean isTool(String Tool) {
		if(isTools()) {
			if (getToolsNames().contains(Tool)) {
				return true;
			}else {
				return false;
			}
		}else {
			return false;
		}

	}
	
	public boolean isTool(ItemStack Tool) {
		if(isTools()) {
			if (getTools().contains(Tool)) {
				return true;
			}else {
				return false;
			}
		}else {
			return false;
		}

	}
	
	public void addBlockDrop(String Drop, int Chance, String Farm, String Block, String Tool) {
		config.set("Farms." + Farm + ".Blocks." + Block + ".Tools." + Tool + "." + Drop, Chance);
		save();
	}
	
	public void removeBlockTool(String Farm, String Block, String Tool) {
		config.set("Farms." + Farm + ".Blocks." + Block + ".Tools." + Tool, null);
		save();
	}
	
	public void addBank(String Farm, Double Money) {
		config.set("Farms." + Farm + ".Bank", getMoney(Farm) + Money);
		save();
	}
	
	public void setMarketBuyMoney(String Farm, String Drop, Double Money) {
		config.set("Farms." + Farm + ".Market.Buy." + Drop + ".Money", Money);
		save();
	}
	
	public void setMarketSellMoney(String Farm, String Drop, Double Money) {
		config.set("Farms." + Farm + ".Market.Sell." + Drop + ".Money", Money);
		save();
	}
	
	public void setMarketBuyMoy(String Farm, String Drop, int Moy) {
		config.set("Farms." + Farm + ".Market.Buy." + Drop + ".Moy", Moy);
		save();
	}
	
	public void setMarketSellMoy(String Farm, String Drop, int Moy) {
		config.set("Farms." + Farm + ".Market.Sell." + Drop + ".Moy", Moy);
		save();
	}
	
	public void removeMarketSell(String Farm, String Drop) {
		config.set("Farms." + Farm + ".Market.Sell" + Drop, null);
		save();
	}
	
	public void removeMarketBuy(String Farm, String Drop) {
		config.set("Farms." + Farm + ".Market.Buy" + Drop, null);
		save();
	}
	
	public Double getMarketBuyMoney(String Farm, String Drop) {
		return config.getDouble("Farms." + Farm + ".Market.Buy." + Drop +"Money");
	}
	
	public Double getMarketSellMoney(String Farm, String Drop) {
		return config.getDouble("Farms." + Farm + ".Market.Sell." + Drop +".Money");
	}
	
	public int getMarketBuyMoy(String Farm, String Drop) {
		return config.getInt("Farms." + Farm + ".Market.Buy." + Drop + ".Moy");
	}
	
	public Double getMarketSellMoy(String Farm, String Drop) {
		return config.getDouble("Farms." + Farm + ".Market.Sell." + Drop + ".Moy");
	}
	
	public List<String> getMarketBuys(String Farm) {
		return new ArrayList<>(config.getConfigurationSection("Farms." + Farm + ".Market.Buy").getKeys(false));
	}
	
	public List<String> getMarketSells(String Farm) {
		return new ArrayList<>(config.getConfigurationSection("Farms." + Farm + ".Market.Sell").getKeys(false));
	}
	
	public boolean isMarketSells(String Farm) {
		try {
			if(!getMarketSells(Farm).equals(null)) {
				return true;
			}
			return false;

		}catch(Exception ex) {
			return false;
		}
	}
	
	public boolean isMarketBuys(String Farm) {
		try {
			if(!getMarketBuys(Farm).equals(null)) {
				return true;
			}
			return false;

		}catch(Exception ex) {
			return false;
		}
	}
	
	public boolean isMarketSell(String Farm, String Drop) {
		if(!isMarketSells(Farm)) {
			return false;
		}
		
		if(getMarketBuys(Farm).contains(Drop)) {
			return true;
		}
		return false;
	}
	
	public boolean isMarketBuy(String Farm, String Drop) {
		if(!isMarketBuys(Farm)) {
			return false;
		}
		
		if(getMarketBuys(Farm).contains(Drop)) {
			return true;
		}
		return false;
	}
	
	public void setMoney(String Farm, Double Money) {
		config.set("Farms." + Farm + ".Bank", Money);
		save();
	}
	
	public void withdrawMoney(String Farm, Double Money) {
		config.set("Farms." + Farm + ".Bank", getMoney(Farm) - Money);
		save();
	}
	
	public Double getMoney(String Farm) {
		return config.getDouble("Farms." + Farm + ".Bank");
	}
	
	public List<String> getFarmPlots(String Farm){
		return config.getStringList("Farms." + Farm + ".Plots");	
	}
	
	public boolean isFarmPlots(String Farm) {
		if(isFarms()) {
			if(isFarm(Farm)) {
				 try {
					 if(!getFarmPlots(Farm).equals(null)) {
						 return true;
					 }
					 return false;
				 }catch(Exception ex) {
					 return false;
				 }
			}

		}
		return false;
	}
	
	public boolean isPlots() {
		 try {
			 if(!getPlots().equals(null)) {
				 return true;
			 }
			 return false;
		 }catch(Exception ex) {
			 return false;
		 }
	}
	
	public String getPlotKey(Chunk plot) {
		String World = plot.getWorld().getName();
		int X = plot.getX();
		int Z = plot.getZ();
		String PlotKey = World + "/" + X + "/" + Z;
		return PlotKey.replace(".", "#");
	}
	
	public String getPlotKey(Location loc) {
		String World = loc.getWorld().getName();
		int X = loc.getChunk().getX();
		int Z = loc.getChunk().getZ();
		String PlotKey = World + "/" + X + "/" + Z;
		return PlotKey.replace(".", "#");	}
	
	public String getPlotKey(Player p) {
		Location loc = p.getLocation();
		String World = loc.getWorld().getName();
		int X = loc.getChunk().getX();
		int Z = loc.getChunk().getZ();
		String PlotKey = World + "/" + X + "/" + Z;
		return PlotKey.replace(".", "#");	}
	
	public World getPlotWorld(String plot) {
		String[] list = plot.split("/");
		return Bukkit.getWorld(list[0]);
	}
	
	public int getPlotX(String plot) {
		String[] list = plot.split("/");
		return Integer.valueOf(list[1]);
	}
	
	public int getPlotZ(String plot) {
		String[] list = plot.split("/");
		return Integer.valueOf(list[2]);
	}
	
    private void save() {
        try {
        	config.save(Main.FarmLandData);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
