package net.thedudemc.entitymanager.commands.exception;

public class CommandException extends RuntimeException {

    private final String message;

    public CommandException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
