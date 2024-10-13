package dev.waterchick;


import dev.waterchick.config.Chats;
import dev.waterchick.config.Messages;
import dev.waterchick.manager.ChatManager;
import dev.waterchick.manager.MessageManager;

import java.text.MessageFormat;
import java.util.logging.Logger;

public class Core {

    private static Core instance;

    private final Platform platform;

    private Chats chatsConfig;
    private Messages messages;


    public Core(Platform platform){
        instance = this;
        this.platform = platform;

        new MessageManager("UnlimitedChats");

        platform.getFolder().mkdirs();

        // Přidání migrace složky
        FolderMigrator folderMigrator = new FolderMigrator("UnlimitedChats", platform.getFolder().getPath());
        folderMigrator.migrate(); // Volání metody pro migraci složky


        loadConfigs();
        loadMessages();

        new ChatManager();
        logSystemInfo();

    }

    public static Core getInstance() {
        return instance;
    }

    private void loadConfigs(){
        this.chatsConfig = new Chats(platform);
    }

    private void loadMessages(){
        this.messages = new Messages(platform);
    }


    public Chats getChatsConfig() {
        return chatsConfig;
    }

    public Messages getMessages() {
        return messages;
    }


    private void logSystemInfo(){
        Logger logger = platform.getLogger();
        logger.info("---------------------------");
        logger.info(" Starting: UnlimitedChats-CORE" + " v" + Version.VERSION_NAME);
        logger.info(" Server platform: " + platform.getPlatformName());
        logger.info(" Server version: " + platform.getServerVersion());
        logger.info("---------------------------");
    }


}
