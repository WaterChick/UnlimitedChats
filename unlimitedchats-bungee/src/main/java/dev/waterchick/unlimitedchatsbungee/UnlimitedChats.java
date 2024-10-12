package dev.waterchick.unlimitedchatsbungee;

import dev.waterchick.Chat;
import dev.waterchick.Core;
import dev.waterchick.Platform;
import dev.waterchick.manager.ChatManager;
import dev.waterchick.unlimitedchatsbungee.commands.ChatCommand;
import dev.waterchick.unlimitedchatsbungee.commands.MainCommand;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.File;

public final class UnlimitedChats extends Plugin implements Platform {


    private static UnlimitedChats instance;

    private Core core;

    public static UnlimitedChats getInstance() {
        return instance;
    }

    @Override
    public void onEnable(){

        instance = this;

        this.core = new Core(this);
        registerCommands();
    }

    @Override
    public String getPlatformName() {
        return "BungeeCord";
    }

    @Override
    public String getServerVersion() {
        return ProxyServer.getInstance().getVersion();
    }

    @Override
    public File getFolder() {
        return this.getDataFolder();
    }

    private void registerCommands(){
        for(Chat chat : ChatManager.getInstance().getChats()){
            ProxyServer.getInstance().getPluginManager().registerCommand(this, new ChatCommand(chat));
        }
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new MainCommand());
    }


}
