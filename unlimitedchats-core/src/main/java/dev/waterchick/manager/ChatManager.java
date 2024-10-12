package dev.waterchick.manager;


import dev.dejvokep.boostedyaml.YamlDocument;
import dev.waterchick.Chat;
import dev.waterchick.Core;

import java.util.ArrayList;
import java.util.List;

public class ChatManager {

    private static ChatManager instance;
    private List<Chat> chats;

    public ChatManager(){
        instance = this;
        loadChats();
    }

    public static ChatManager getInstance() {
        return instance;
    }

    private void loadChats(){
        this.chats = new ArrayList<>();
        YamlDocument config = Core.getInstance().getChatsConfig().getConfig();
        for(Object key : config.getKeys()){
            String command = config.getString(key + ".command");
            List<String> aliases = config.getStringList(key+".aliases");
            String permission = config.getString(key+".permission");
            String message = config.getString(key+".message");
            chats.add(new Chat(command,aliases,permission,message));
        }
    }

    public List<Chat> getChats() {
        return chats;
    }
}
