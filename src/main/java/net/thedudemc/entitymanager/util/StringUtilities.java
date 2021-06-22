package net.thedudemc.entitymanager.util;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

public class StringUtilities {

    public static String getEntityString(Entity e, int amount, boolean colored) {
        String name = e.getType().getKey().getKey();
        String dimension = StringUtilities.getDimensionName(e.getWorld().getEnvironment().name(), colored);
        String count = colored ? ChatColor.YELLOW + String.valueOf(amount) + ChatColor.RESET : String.valueOf(amount);
        String location = getLocationString(e.getLocation(), colored);

        return name + ": " + count + " | " + dimension + " - " + location + " |";
    }

    public static String getDimensionName(String dimension, boolean color) {
        switch (dimension) {
            case "NORMAL":
                return color ? ChatColor.DARK_GREEN + "Overworld" + ChatColor.RESET : "Overworld";
            case "NETHER":
                return color ? ChatColor.DARK_RED + "Nether" + ChatColor.RESET : "Nether";
            case "THE_END":
                return color ? ChatColor.DARK_PURPLE + "The End" + ChatColor.RESET : "The End";
            default:
                return "Other";
        }
    }

    public static String getLocationString(Location location, boolean colored) {
        if (colored) {
            return "x:" + ChatColor.GOLD + location.getBlockX() + ChatColor.RESET +
                    " y:" + ChatColor.GOLD + location.getBlockY() + ChatColor.RESET +
                    " z:" + ChatColor.GOLD + location.getBlockZ() + ChatColor.RESET;
        }
        return "x:" + location.getBlockX() +
                " y:" + location.getBlockY() +
                " z:" + location.getBlockZ();
    }

    public static ChatColor getNameColor(Player player) {
        Team team = player.getScoreboard().getEntryTeam(player.getDisplayName());
        return team != null ? team.getColor() : ChatColor.RESET;
    }
}
