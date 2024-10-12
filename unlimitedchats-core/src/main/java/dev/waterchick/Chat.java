package dev.waterchick;

import java.util.List;

public class Chat {

    private String command;
    private List<String> aliases;
    private String permission;

    private String message;

    public Chat(String command, List<String> aliases, String permission, String message){
        this.command = command;
        this.aliases = aliases;
        this.permission = permission;
        this.message = message;
    }

    public String getCommand() {
        return command;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public String getPermission() {
        return permission;
    }

    public String getMessage() {
        return message;
    }
}
