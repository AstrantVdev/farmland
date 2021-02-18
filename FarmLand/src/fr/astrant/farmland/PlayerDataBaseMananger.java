package fr.astrant.farmland;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class PlayerDataBaseMananger {
	private YamlConfiguration config = Main.Playerconfig;
	
	public void msg(Player p, String Message) {
		p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10, 10);
		p.sendMessage("§a§l[FarmLand]: §c" + Message);
	}
	
	public void soundgood(Player p) {
		p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10, 10);
	}
	
	public void soundbad(Player p) {
		p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 10, 10);
	}
	
	public void setFarm(String Farm, String uuid) {
		config.set("Players." + uuid + ".Farm", Farm);
		save();
	}
	
	public String getFarm(String uuid) {
		return config.getString("Players." + uuid + ".Farm");
	}
	
	public boolean hasFarm(String uuid) {
		try {
			String Farm = getFarm(uuid);
			if(!Farm.equals(null)) {
				return true;
			}
			return false;
		}catch(Exception ex) {
			return false;
		}
		
	}
	
    public void Annouce(final String message, final Sound sound) {
        final Collection<? extends Player> players = (Collection<? extends Player>)Bukkit.getOnlinePlayers();
        final List<Player> Players = new ArrayList<Player>(players);
        for (int i = 0; i != Players.size(); ++i) {
            final Player p = Players.get(i);
            final Location playerloc = p.getLocation();
            p.playSound(playerloc, sound, 6.0f, 1.0f);
            p.sendMessage(message);
        }
    }
	
    private void save() {
        try {
        	config.save(Main.PlayerData);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void sendTitle(final Player player, final String title, final String subtitle, final int fadeInTime, final int showTime, final int fadeOutTime) {
        try {
            final Constructor<?> DeletetitleConstructor = this.getNMSClass("PacketPlayOutTitle").getConstructor(this.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], this.getNMSClass("IChatBaseComponent"), Integer.TYPE, Integer.TYPE, Integer.TYPE);
            final Object Deletepacket = DeletetitleConstructor.newInstance(this.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TITLE").get(null), null, 0, 0, 0);
            final Constructor<?> DeletetimingTitleConstructor = this.getNMSClass("PacketPlayOutTitle").getConstructor(this.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], this.getNMSClass("IChatBaseComponent"), Integer.TYPE, Integer.TYPE, Integer.TYPE);
            final Object DeletetimingPacket = DeletetimingTitleConstructor.newInstance(this.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("SUBTITLE").get(null), null, fadeInTime, showTime, fadeOutTime);
            this.sendPacket(player, Deletepacket);
            this.sendPacket(player, DeletetimingPacket);
            final Object chatTitle = this.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\": \"" + title + "\"}");
            final Constructor<?> titleConstructor = this.getNMSClass("PacketPlayOutTitle").getConstructor(this.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], this.getNMSClass("IChatBaseComponent"), Integer.TYPE, Integer.TYPE, Integer.TYPE);
            final Object packet = titleConstructor.newInstance(this.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TITLE").get(null), chatTitle, fadeInTime, showTime, fadeOutTime);
            final Object chatsTitle = this.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\": \"" + subtitle + "\"}");
            final Constructor<?> timingTitleConstructor = this.getNMSClass("PacketPlayOutTitle").getConstructor(this.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], this.getNMSClass("IChatBaseComponent"), Integer.TYPE, Integer.TYPE, Integer.TYPE);
            final Object timingPacket = timingTitleConstructor.newInstance(this.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("SUBTITLE").get(null), chatsTitle, fadeInTime, showTime, fadeOutTime);
            this.sendPacket(player, packet);
            this.sendPacket(player, timingPacket);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void sendPacket(final Player player, final Object packet) {
        try {
            final Object handle = player.getClass().getMethod("getHandle", (Class<?>[])new Class[0]).invoke(player, new Object[0]);
            final Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
            playerConnection.getClass().getMethod("sendPacket", this.getNMSClass("Packet")).invoke(playerConnection, packet);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public Class<?> getNMSClass(final String name) {
        try {
            return Class.forName("net.minecraft.server." + Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3] + "." + name);
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

}
