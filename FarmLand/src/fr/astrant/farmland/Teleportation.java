package fr.astrant.farmland;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Teleportation {
	Main main;
	PlayerDataBaseMananger playerdata;
	
	Teleportation() {
		main = Main.getInstance();
	}
	
    public void teleportion(int i, Player p, Location loc) {
    	playerdata = new PlayerDataBaseMananger();
    	
        new BukkitRunnable() {
			public void run() {
                int time = i;
                
                if (time != 0) {
                	playerdata.msg(p, "|" + i);
                    time --;
                    teleportion(time, p, loc);
                }else if (time == 0) {
                	p.teleport(loc);
                }
                
            }
        }.runTaskLater(main, 20L);
    }

}
