package dev.waterchick.unlimitedchatsbungee.commands;

import dev.waterchick.Core;
import dev.waterchick.config.Messages;
import dev.waterchick.manager.MessageManager;
import dev.waterchick.unlimitedchatsbungee.utilities.ChatUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class MainCommand extends Command {

    public MainCommand(){
        super("unlimitedchats","","uc");

    }
    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if(strings.length == 0){
            commandSender.sendMessage(ChatUtils.color(MessageManager.getInstance().prefix + MessageManager.getInstance().help));
            return;
        }if(strings.length == 1) {
            if(strings[0].equalsIgnoreCase("reload")) {
                if (!commandSender.hasPermission("uc.reload")) {
                    commandSender.sendMessage(ChatUtils.color(MessageManager.getInstance().prefix + MessageManager.getInstance().no_permission));
                    return;
                }

                Core.getInstance().getChatsConfig().reloadConfig();
                Core.getInstance().getMessages().reloadConfig();


                commandSender.sendMessage(ChatUtils.color(MessageManager.getInstance().prefix + MessageManager.getInstance().reloaded));
            }
        }
    }
}
