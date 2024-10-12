package dev.waterchick.manager;

public class MessageManager {

    private static MessageManager instance;

    public String prefix;
    public String name;

    public String no_permission;

    public String supply_message;
    public String reloaded;
    public String help;

    public String hover_text;
    public boolean hover_enabled;

    public String hover_command;

    public MessageManager(String name){
        this.name = name;
        this.prefix = "["+name+"]";
        instance = this;
    }

    public static MessageManager getInstance() {
        return instance;
    }
}
