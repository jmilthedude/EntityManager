package net.thedudemc.entitymanager.commands;

import net.thedudemc.entitymanager.util.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.util.StringUtil;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class SafekillCommand extends PluginCommand {
    @Override
    public String getName() {
        return "safekill";
    }

    //              0           1         2        3
    // safekill <name/type> <criteria> <radius> <player>
    @Override
    public Message execute(CommandSender sender, String[] args) {
        if (args.length < 2 || args.length > 4)
            return Message.create().colorize(ChatColor.GRAY).append("Invalid Command...");

        Message response = Message.create();

        SearchType searchType = SearchType.valueOf(args[0].toUpperCase(Locale.ENGLISH));
        AtomicInteger killCount = new AtomicInteger();
        List<Entity> entities;

        if (args.length == 2) {

            entities = EntityUtils.getEntities(searchType, args[1], 0, null);

        } else if (args.length == 3) {
            if (!isPlayer(sender))
                return response.colorize(ChatColor.RED).append("You must be a player to run this command with a radius.");

            entities = EntityUtils.getEntities(searchType, args[1], Integer.parseInt(args[2]), (Player) sender);
        } else {
            Player player = Bukkit.getPlayer(args[3]);
            if (player == null)
                return response.colorize(ChatColor.RED).append("There was no player found by that name: ").resetColor().append(args[3]);

            entities = EntityUtils.getEntities(searchType, args[1], Integer.parseInt(args[2]), (Player) sender);
        }

        entities.forEach(e -> {
            if (canBeKilled(e)) {
                e.remove();
                killCount.getAndIncrement();
            }
        });

        return response.colorize(ChatColor.YELLOW)
                .append(String.valueOf(killCount.get())).resetColor()
                .append(" ")
                .append(args[1]).append(" type entities have been removed.");

    }

    private boolean canBeKilled(Entity entity) {
        if (entity instanceof Tameable) return !((Tameable) entity).isTamed();
        if (entity instanceof Villager) return false;
        return entity.getCustomName() == null;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length < 1 || args.length > 4) return null;

        if (args.length == 1) {
            return StringUtil.copyPartialMatches(args[0], new ArrayList<String>() {
                {
                    Arrays.asList(SearchType.values()).forEach(searchType -> add(searchType.name().toLowerCase()));
                }
            }, new ArrayList<>(SearchType.values().length));
        } else if (args.length == 2) {
            SearchType type = SearchType.valueOf(args[0].toUpperCase(Locale.ENGLISH));
            if (type == SearchType.TYPE) {
                return StringUtil.copyPartialMatches(args[1], new ArrayList<String>() {
                    {
                        Arrays.asList(CountType.values()).forEach(countType -> add(countType.name().toLowerCase()));
                    }
                }, new ArrayList<>(CountType.values().length));
            } else {
                return StringUtil.copyPartialMatches(args[1], new ArrayList<String>() {
                    {
                        Arrays.asList(EntityType.values()).forEach(entityType -> add(entityType.name().equalsIgnoreCase("dropped_item") ? "item" : entityType.name().toLowerCase()));
                    }
                }, new ArrayList<>(EntityType.values().length));
            }
        } else if (args.length == 3) {
            return StringUtil.copyPartialMatches(args[2], Collections.singletonList("<radius>"), new ArrayList<>(1));
        } else {
            List<String> names = EntityUtils.getPlayerNames();
            return StringUtil.copyPartialMatches(args[3], names, new ArrayList<>(names.size()));
        }
    }
}
