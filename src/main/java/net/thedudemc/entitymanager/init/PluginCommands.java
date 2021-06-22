package net.thedudemc.entitymanager.init;

import net.thedudemc.entitymanager.EntityManager;
import net.thedudemc.entitymanager.commands.*;

public class PluginCommands {

    public static CountCommand COUNT;
    public static SafekillCommand SAFEKILL;
    public static PlayersCommand PLAYERS;
    public static ChunksCommand CHUNKS;

    public static void register() {
        COUNT = (CountCommand) register(new CountCommand().opOnly());
        SAFEKILL = (SafekillCommand) register(new SafekillCommand().opOnly());
        PLAYERS = (PlayersCommand) register(new PlayersCommand().opOnly());
        CHUNKS = (ChunksCommand) register(new ChunksCommand().opOnly().playerOnly());
    }

    private static PluginCommand register(PluginCommand command) {
        EntityManager.getInstance().getCommand(command.getName()).setExecutor(command);
        EntityManager.getInstance().getCommand(command.getName()).setTabCompleter(command);
        return command;
    }

}
