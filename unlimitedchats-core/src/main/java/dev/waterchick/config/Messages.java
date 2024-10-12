package dev.waterchick.config;


import cz.waterchick.configwrapper.Config;
import dev.waterchick.Platform;
import dev.waterchick.manager.MessageManager;

import java.io.IOException;

public class Messages extends Config {


    public Messages(Platform platform) {
        super(platform.getFolder(),"messages.yml");
        loadConfig();
    }

    @Override
    public void onLoad() {
        MessageManager mm = MessageManager.getInstance();
        mm.prefix = getConfig().getString("prefix");
        mm.no_permission = getConfig().getString("no_permission");
        mm.reloaded = getConfig().getString("reloaded");
        mm.supply_message = getConfig().getString("supply_message");
        mm.help = getConfig().getString("help");
        mm.hover_enabled = getConfig().getBoolean("hover.enabled");
        mm.hover_text = getConfig().getString("hover.text");
        mm.hover_command = getConfig().getString("hover.command");
    }

    @Override
    public void onSave() {

    }


}
