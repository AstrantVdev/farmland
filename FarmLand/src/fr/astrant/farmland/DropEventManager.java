package fr.astrant.farmland;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class DropEventManager {
	FarmLandDataBaseManager data;
	public List<String> DropEvents = Stream.of(DropEventsEnum.values())
            .map(Enum::name)
            .collect(Collectors.toList());
	
	public void Dropeventmanager(String Drop, Player p, Location loc) {
		data = new FarmLandDataBaseManager();
		double x = loc.getX();
		double y = loc.getY();
		double z = loc.getBlockZ();
		
		if(data.hasDropEvent(Drop)) {
			List<String> Events = data.getDropEvents(Drop);
			
			for(int e = 0; e != Events.size(); e++) {
				String event = Events.get(e); 
				String Value = data.getEventValue(Drop, e+1);
				List<String> split = Arrays.asList(Value.split(" "));

				Value.replace("&", "§");
				
				for (int i = 0; i != split.size(); i++) {
					String splited = split.get(i);
					if(splited.equals("<player>")) {
						split.set(i, p.getName());
						Value = split.toString();
					}
				}
				
				if(event.equals("BROADCAST")) {
					Bukkit.broadcastMessage(Value);
				}
				
				if(event.equals("MESSAGE")) {
					p.sendMessage(Value);
				}
				
				if(event.equals("PARTICLES")) {
					p.spawnParticle(Particle.valueOf(Value), x, y, z, 50, 1, 1, 1);

				}
				
				if(event.equals("COMMAND")) {
					
					if(split.get(0).equals("@player")) {
						split.set(0, null);

						p.chat("/" + Value);
					}else {
						ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
						Bukkit.dispatchCommand(console, Value);

					}
				}
				
				if(event.equals("MONEY")) {
					Main.economy.depositPlayer(p, Integer.valueOf(Value));
				}
				
			}
			
		}
		
	}
	
	public enum DropEventsEnum {
		BROADCAST,
		MESSAGE,
		MONEY,
		COMMAND,
		PARTICLES,

	}
	
}
