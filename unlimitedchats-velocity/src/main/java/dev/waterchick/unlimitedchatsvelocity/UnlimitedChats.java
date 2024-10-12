package dev.waterchick.unlimitedchatsvelocity;

import com.google.inject.Inject;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import dev.waterchick.Chat;
import dev.waterchick.Core;
import dev.waterchick.Platform;
import dev.waterchick.Version;
import dev.waterchick.manager.ChatManager;
import dev.waterchick.unlimitedchatsvelocity.commands.ChatCommand;
import dev.waterchick.unlimitedchatsvelocity.commands.MainCommand;

import java.io.File;
import java.nio.file.Path;
import java.util.logging.Logger;

@Plugin(
        id = "unlimitedchats-velocity",
        name = "unlimitedchats-velocity",
        authors = "Water_Chick",
        version = Version.VERSION_NAME
)
public class UnlimitedChats implements Platform {

    @Inject
    private Logger logger;

    @Inject
    private ProxyServer proxyServer;

    @Inject
    private @DataDirectory Path dataFolderPath;

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        new Core(this);
        registerCommands();
    }


    @Override
    public String getPlatformName() {
        return "Velocity";
    }

    @Override
    public String getServerVersion() {
        return proxyServer.getVersion().getVersion();
    }

    @Override
    public File getFolder() {
        return dataFolderPath.toFile();
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

    private void registerCommands(){
        CommandManager commandManager = proxyServer.getCommandManager();
        for(Chat chat : ChatManager.getInstance().getChats()){
            CommandMeta commandMeta = commandManager.metaBuilder(chat.getCommand().replace("/",""))
                    .plugin(this)
                    .aliases(chat.getAliases().toArray(String[]::new))
                    .build();
            SimpleCommand command = new ChatCommand(chat, proxyServer);
            commandManager.register(commandMeta, command);
        }
        CommandMeta commandMeta = commandManager.metaBuilder("unlimitedchats")
                .plugin(this)
                .aliases("uc")
                .build();
        SimpleCommand command = new MainCommand();
        commandManager.register(commandMeta, command);
    }
}
