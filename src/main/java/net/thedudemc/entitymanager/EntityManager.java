package net.thedudemc.entitymanager;

import net.thedudemc.entitymanager.init.PluginCommands;
import org.bukkit.plugin.java.JavaPlugin;

public class EntityManager extends JavaPlugin {

    private static EntityManager INSTANCE;

    public static EntityManager getInstance() {
        return INSTANCE;
    }

    @Override
    public void onEnable() {
        INSTANCE = this;

        PluginCommands.register();
    }

    @Override
    public void onDisable() {

    }

}
