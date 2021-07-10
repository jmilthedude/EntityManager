package net.thedudemc.entitymanager.commands;

import net.thedudemc.entitymanager.util.EntityUtils;
import net.thedudemc.entitymanager.util.Message;
import net.thedudemc.entitymanager.util.SearchType;
import net.thedudemc.entitymanager.util.StringUtilities;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class PlayersCommand extends PluginCommand {
    @Override
    public String getName() {
        return "players";
    }

    @Override
    public Message execute(CommandSender sender, String[] args) {
        Message response = Message.create();

        if (args.length < 1) {
            List<Entity> players = EntityUtils.getEntities(SearchType.TYPE, "player", 0, null);

            response.colorize(ChatColor.GRAY).append("Name - Entity Count - Item Count - Dimension - Coordinates").newLine();

            players.forEach(player -> {
                AtomicInteger itemCount = new AtomicInteger();
                AtomicInteger entityCount = new AtomicInteger();
                String name = ((Player) player).getDisplayName();
                ChatColor teamColor = StringUtilities.getNameColor((Player) player);
                String dimension = StringUtilities.getDimensionName(player.getLocation().getWorld().getEnvironment().name(), true);

                List<Entity> entities = EntityUtils.getEntities(SearchType.TYPE, "all", 160, (Player) player);
                entities.forEach(e -> {
                    if (e instanceof Item) {
                        itemCount.addAndGet(((Item) e).getItemStack().getAmount());
                    } else {
                        entityCount.getAndIncrement();
                    }
                });

                response.colorize(teamColor).append(name).resetColor()
                        .append(" - E: ")
                        .colorize(ChatColor.YELLOW).append(String.valueOf(entityCount.get()))
                        .resetColor().append(" - I: ")
                        .colorize(ChatColor.YELLOW).append(String.valueOf(itemCount.get()))
                        .resetColor().append(" - ")
                        .append(dimension)
                        .resetColor().append(" - ")
                        .colorize(ChatColor.GOLD).append(String.valueOf(player.getLocation().getBlockX()))
                        .resetColor().append(", ")
                        .colorize(ChatColor.GOLD).append(String.valueOf(player.getLocation().getBlockY()))
                        .resetColor().append(", ")
                        .colorize(ChatColor.GOLD).append(String.valueOf(player.getLocation().getBlockZ()))
                        .resetColor().newLine();
            });

            return response;

        } else {
            return response.colorize(ChatColor.GRAY).append("Invalid Command...");
        }
    }
}
