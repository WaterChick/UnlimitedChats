package dev.waterchick.config;


import cz.waterchick.configwrapper.Config;
import dev.waterchick.Platform;


public class Chats extends Config {


    public Chats(Platform platform) {
        super(platform.getFolder(),"chats.yml");
        loadConfig();
    }

    @Override
    public void onLoad() {

    }

    @Override
    public void onSave() {

    }

}
