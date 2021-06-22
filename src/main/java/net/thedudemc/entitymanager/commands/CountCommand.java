package net.thedudemc.entitymanager.commands;

import net.thedudemc.entitymanager.util.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.util.StringUtil;

import java.util.*;

public class CountCommand extends PluginCommand {
    @Override
    public String getName() {
        return "count";
    }

    @Override
    public Message execute(CommandSender sender, String[] args) {
        if (args.length > 1 && args.length < 5) {

            Message response = Message.create();

            SearchType searchType = SearchType.valueOf(args[0].toUpperCase(Locale.ENGLISH));
            HashMap<EntityType, Integer> sortedEntities;

            if (args.length > 2) {
                CountType type = CountType.fromString(args[1]);
                if (type == null) {
                    return response.colorize(ChatColor.RED).append("Invalid Entity Type")
                            .resetColor().append(": ")
                            .colorize(ChatColor.YELLOW).append(args[1]);
                }
            }

            if (args.length == 2) { // count <name,type> <criteria>

                List<Entity> entities = EntityUtils.getEntities(searchType, args[1], 0, null);
                HashMap<EntityType, Integer> counted = EntityUtils.getEntitiesWithCount(entities);
                sortedEntities = Util.sortMap(counted, true);

            } else if (args.length == 3) { // count <name,type> <criteria> <radius>
                if (!isPlayer(sender)) {
                    return response.colorize(ChatColor.RED).append("You must be a player to run this command with a radius.");
                }

                Player player = (Player) sender;
                int radius = Integer.parseInt(args[2]);
                List<Entity> entities = EntityUtils.getEntities(searchType, args[1], radius, player);
                HashMap<EntityType, Integer> counted = EntityUtils.getEntitiesWithCount(entities);
                sortedEntities = Util.sortMap(counted, true);

            } else { // count <name,type> <criteria> <radius> <player>

                Player player = Bukkit.getPlayer(args[3]);
                if (player == null) {
                    return response.colorize(ChatColor.RED).append("There was no player found by that name: ").resetColor().append(args[3]);
                }

                int radius = Integer.parseInt(args[2]);
                List<Entity> entities = EntityUtils.getEntities(searchType, args[1], radius, player);
                HashMap<EntityType, Integer> counted = EntityUtils.getEntitiesWithCount(entities);
                sortedEntities = Util.sortMap(counted, true);

            }

            if (sortedEntities.size() == 0) {
                return response.colorize(ChatColor.GOLD).append("No entities found of the specified type: ").resetColor().append(args[1]);
            }

            for (EntityType s : sortedEntities.keySet()) {
                response.append(s.getKey().getKey())
                        .append(": ")
                        .colorize(ChatColor.YELLOW).append(String.valueOf(sortedEntities.get(s)))
                        .resetColor().newLine();
            }
            return response;
        }
        return Message.create().colorize(ChatColor.GRAY).append("Invalid Command...");
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
