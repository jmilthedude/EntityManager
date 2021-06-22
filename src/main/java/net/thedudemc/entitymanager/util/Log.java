package net.thedudemc.entitymanager.util;

import org.bukkit.command.CommandSender;

public class Log {

    public static void printInfo(Object... objects) {
        System.out.println("******* Entity Manager - INFO *******");
        for (Object o : objects) System.out.println(o);
    }


    public static void printError(Object... objects) {
        System.out.println("******* Entity Manager - Error *******");
        for (Object o : objects) System.out.println(o);
    }

    public static void message(CommandSender player, Message message, boolean console) {
        String chatOutput = message.build();
        String consoleOutput = message.removeAllFormatting().build();
        for (String s : chatOutput.split("\\^")) {
            player.sendMessage(s);
        }
        if (console) {
            for (String s : consoleOutput.split("\\^")) {
                System.out.println(s);
            }
        }

    }

}
