package net.thedudemc.entitymanager.util;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class EntityUtils {

    public static List<Entity> getEntities(SearchType type, String term, int radius, @Nullable Player player) {
        if (type == SearchType.TYPE) return getEntitiesByType(term, radius, player);
        else return getEntitiesByName(term, radius, player);
    }

    private static List<Entity> getEntitiesByName(String name, int radius, @Nullable Player player) {
        List<Entity> entities = new ArrayList<>();
        EntityType type = EntityType.fromName(name);
        boolean local = radius > 0;
        if (type != null) {
            if (local)
                entities.addAll(player.getWorld().getNearbyEntities(player.getLocation(), radius, radius, radius, entity -> entity.getType() == type));
            else {
                getAllLoadedEntities().forEach(entity -> {
                    if (entity.getType() == type) entities.add(entity);
                });
            }
        }
        return entities;
    }

    private static List<Entity> getEntitiesByType(String type, int radius, @Nullable Player player) {
        CountType countType = CountType.fromString(type);
        boolean local = radius > 0;
        if (countType != null) {
            if (local) return new ArrayList<>(filterEntitiesByType(
                    player.getWorld().getNearbyEntities(
                            player.getLocation(),
                            radius,
                            radius,
                            radius
                    ),
                    countType));
            else return new ArrayList<>(filterEntitiesByType(getAllLoadedEntities(), countType));
        }
        return new ArrayList<>();
    }

    public static HashMap<EntityType, Integer> getEntitiesWithCount(List<Entity> entities) {
        List<EntityType> types = getEntityTypeList(entities);
        HashMap<EntityType, Integer> counted = new HashMap<>();
        HashSet<EntityType> uniqueEntites = new HashSet<>(types);

        for (EntityType e : uniqueEntites) {
            counted.put(e, Collections.frequency(types, e));
        }
        return counted;
    }

    private static List<EntityType> getEntityTypeList(List<Entity> entities) {
        List<EntityType> types = new ArrayList<>();
        for (Entity entity : entities) {
            types.add(entity.getType());
        }
        return types;
    }

    public static List<Entity> filterEntitiesByType(Collection<Entity> entities, CountType type) {
        List<Entity> filtered = new ArrayList<>();
        switch (type) {
            case ALL:
                for (Entity e : entities) {
                    if (!(e instanceof Player)) filtered.add(e);
                }
                break;
            case AMBIENT:
                for (Entity e : entities) {
                    if (e instanceof Ambient) filtered.add(e);
                }
                break;
            case FLYING:
                for (Entity e : entities) {
                    if (e instanceof Flying) filtered.add(e);
                }
                break;
            case HOSTILE:
                for (Entity e : entities) {
                    if (e instanceof Monster) filtered.add(e);
                }
                break;
            case ITEM:
                for (Entity e : entities) {
                    if (e instanceof Item) {
                        Item item = (Item) e;
                        ItemStack stack = item.getItemStack();
                        int count = stack.getAmount();
                        for (int i = 0; i < count; i++) {
                            filtered.add(item);
                        }
                    }
                }
                break;
            case OTHER:
                for (Entity e : entities) {
                    if (!isCounted(e) && (!(e instanceof Player))) filtered.add(e);
                }
                break;
            case PASSIVE:
                for (Entity e : entities) {
                    if (e instanceof Animals) filtered.add(e);
                }
                break;
            case VILLAGER:
                for (Entity e : entities) {
                    if (e instanceof Villager) filtered.add(e);
                }
                break;
            case WATER:
                for (Entity e : entities) {
                    if (e instanceof WaterMob) filtered.add(e);
                }
                break;

            case PLAYER:
                for (Entity e : entities) {
                    if (e instanceof Player) filtered.add(e);
                }
                break;
            default:
                break;

        }

        return filtered;
    }

    private static List<Entity> getAllLoadedEntities() {
        List<Entity> entities = new ArrayList<>();
        List<World> worlds = Bukkit.getWorlds();
        for (World world : worlds) {
            entities.addAll(world.getEntities());
        }

        return entities;
    }


    public static List<String> getPlayerNames() {
        List<String> names = new ArrayList<>();
        Bukkit.getOnlinePlayers().forEach(player -> names.add(player.getName()));
        return names;
    }

    private static boolean isCounted(Entity entity) {
        return entity instanceof Monster ||
                entity instanceof Animals ||
                entity instanceof WaterMob ||
                entity instanceof Flying ||
                entity instanceof Ambient ||
                entity instanceof Item ||
                entity instanceof Villager ||
                entity instanceof Player;
    }

}
