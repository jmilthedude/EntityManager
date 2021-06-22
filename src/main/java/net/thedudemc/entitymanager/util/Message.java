package net.thedudemc.entitymanager.util;

import org.bukkit.ChatColor;

public class Message {
    private String message;

    private Message() {
        this.message = "";
    }

    public static Message create() {
        return new Message()
                .colorize(ChatColor.AQUA)
                .append("***** Entity Manager *****")
                .resetColor()
                .newLine();
    }

    public Message append(String s) {
        this.message += s;
        return this;
    }

    public Message colorize(ChatColor color) {
        this.message += color;
        return this;
    }

    public Message resetColor() {
        this.message += ChatColor.RESET;
        return this;
    }

    public Message newLine() {
        this.append("^");
        return this;
    }

    public Message removeAllFormatting() {
        this.message = ChatColor.stripColor(this.message);
        return this;
    }


    public String build() {
        return this.message;
    }
}
