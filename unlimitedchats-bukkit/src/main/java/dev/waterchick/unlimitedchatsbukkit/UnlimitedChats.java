package dev.waterchick.unlimitedchatsbukkit;

import dev.waterchick.Chat;
import dev.waterchick.Core;
import dev.waterchick.Platform;
import dev.waterchick.manager.ChatManager;
import dev.waterchick.manager.MessageManager;
import dev.waterchick.unlimitedchatsbukkit.commands.ChatCommand;
import dev.waterchick.unlimitedchatsbukkit.commands.MainCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.lang.reflect.Field;

public final class UnlimitedChats extends JavaPlugin implements Platform {

    private static UnlimitedChats instance;
    private Core core;

    public static UnlimitedChats getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        this.core = new Core(this);
        registerCommands();

        if(canPAPIHook()){
            this.getLogger().info("Successfully hooked to PlaceholderAPI");
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void registerCommands(){
        try {
            final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");

            bukkitCommandMap.setAccessible(true);
            CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
            for(Chat chat : ChatManager.getInstance().getChats()) {
                commandMap.register(chat.getCommand(), new ChatCommand(chat));
            }
            commandMap.register("unlimitedchats", new MainCommand());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getPlatformName() {
        return "Bukkit";
    }

    @Override
    public String getServerVersion() {
        return Bukkit.getServer().getVersion();
    }

    @Override
    public File getFolder() {
        return this.getDataFolder();
    }

    public boolean canPAPIHook(){
        return (Bukkit.getServer().getPluginManager().getPlugin("PlaceholderAPI" ) != null);
    }
}
