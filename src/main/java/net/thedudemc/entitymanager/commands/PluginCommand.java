package net.thedudemc.entitymanager.commands;

import net.thedudemc.entitymanager.commands.exception.CommandException;
import net.thedudemc.entitymanager.util.Log;
import net.thedudemc.entitymanager.util.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.List;

public abstract class PluginCommand implements CommandExecutor, TabCompleter {

    private boolean opOnly = false;
    private boolean playerCommand = false;

    public abstract String getName();

    public PluginCommand() {
    }

    public PluginCommand opOnly() {
        this.opOnly = true;
        return this;
    }

    public PluginCommand playerOnly() {
        this.playerCommand = true;
        return this;
    }

    public boolean canExecute(CommandSender sender) {
        if (this.playerCommand && !(sender instanceof Player))
            throw new CommandException("This command can only be run by a player.");
        if (this.opOnly && !sender.isOp())
            throw new CommandException("You do not have permission to run this command.");
        return true;
    }

    public abstract Message execute(CommandSender sender, String[] args);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (this.canExecute(sender)) {
            Message response = this.execute(sender, args);
            Log.message(sender, response, isPlayer(sender));
        }

        return true;
    }

    protected boolean isPlayer(CommandSender sender) {
        return sender instanceof Player;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }

}
