package fr.astrant.farmland;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BreakBlock {
	FarmLandDataBaseManager data;
	DropChance dropchance;
	DropEventManager dropeventmanager;
	MinTimerDataBaseManager mintimerdata;
	
	public void Breakblock(World world, String Farm, String type, Location loc, ItemStack tool, Player p) {
		data = new FarmLandDataBaseManager();
		dropchance = new DropChance();
		dropeventmanager = new DropEventManager();
		mintimerdata = new MinTimerDataBaseManager();
		
		String ToolName = data.getToolName(tool);
		String DropName = dropchance.DropChoose(Farm, type, ToolName);
		ItemStack Drop = data.getDropwithName(DropName);
		
		world.dropItem(loc, Drop);
		
		PotionEffect potion = new PotionEffect(PotionEffectType.SLOW_DIGGING, 10*20, 254);
		p.addPotionEffect(potion);
		
		if(data.hasDropEvent(DropName)) {
			dropeventmanager.Dropeventmanager(DropName, p, loc);
		}
		
		int Minute = data.getBlockCooldown(Farm, type);
		data.addDropNumber(DropName, 1);
		data.addFarmDropProduction(Farm, DropName, 1);
		
		mintimerdata.addTimerValue(Minute, mintimerdata.getBlockReplaceKey(loc, type));
		p.removePotionEffect(PotionEffectType.SLOW_DIGGING);
		}

}
