package net.thedudemc.entitymanager.commands;

import net.thedudemc.entitymanager.util.Message;
import net.thedudemc.entitymanager.util.Util;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ChunksCommand extends PluginCommand {
    @Override
    public String getName() {
        return "chunks";
    }

    @Override
    public Message execute(CommandSender sender, String[] args) {
        Message response = Message.create();

        if (args.length == 1) {
            HashMap<Chunk, Integer> map = new HashMap<>();
            if ("entities".equalsIgnoreCase(args[0])) {
                response.colorize(ChatColor.GRAY).append("Top 10 loaded chunks by Entity count.").resetColor().newLine();
                Player player = (Player) sender;
                World world = player.getWorld();
                Chunk[] chunks = world.getLoadedChunks();
                for (Chunk chunk : chunks) {
                    map.put(chunk, chunk.getEntities().length);
                }
                HashMap<Chunk, Integer> sorted = Util.sortMap(map, true);
                int i = 0;
                for (Chunk chunk : sorted.keySet()) {
                    response.append(String.valueOf(i + 1))
                            .append(": ChunkCorner: ")
                            .colorize(ChatColor.GOLD).append("x")
                            .colorize(ChatColor.YELLOW).append(String.valueOf(chunk.getX() * 16)).resetColor()
                            .append(", ")
                            .colorize(ChatColor.GOLD).append("y")
                            .colorize(ChatColor.YELLOW).append(String.valueOf(chunk.getZ() * 16)).resetColor()
                            .append(" - Entities: ")
                            .colorize(ChatColor.YELLOW).append(String.valueOf(sorted.get(chunk))).resetColor()
                            .newLine();
                    if (i++ == 9) break;
                }
                return response;
            } else if ("tiles".equalsIgnoreCase(args[0])) {
                response.colorize(ChatColor.GRAY).append("Top 10 loaded chunks by Tile Entity count.").resetColor().newLine();
                Player player = (Player) sender;
                World world = player.getWorld();
                Chunk[] chunks = world.getLoadedChunks();
                for (Chunk chunk : chunks) {
                    map.put(chunk, chunk.getTileEntities().length);
                }
                HashMap<Chunk, Integer> sorted = Util.sortMap(map, true);
                int i = 0;
                for (Chunk chunk : sorted.keySet()) {
                    response.append(String.valueOf(i + 1))
                            .append(": ChunkCorner: ")
                            .colorize(ChatColor.GOLD).append("x")
                            .colorize(ChatColor.YELLOW).append(String.valueOf(chunk.getX() * 16)).resetColor()
                            .append(", ")
                            .colorize(ChatColor.GOLD).append("y")
                            .colorize(ChatColor.YELLOW).append(String.valueOf(chunk.getZ() * 16)).resetColor()
                            .append(" - Tile Entities: ")
                            .colorize(ChatColor.YELLOW).append(String.valueOf(sorted.get(chunk))).resetColor()
                            .newLine();
                    if (i++ == 9) break;
                }
                return response;
            }
        }

        return response.colorize(ChatColor.GRAY).append("Invalid Usage...");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return StringUtil.copyPartialMatches(args[0], Arrays.asList("entities", "tiles"), new ArrayList<>(2));
        }
        return super.onTabComplete(sender, command, alias, args);
    }
}
