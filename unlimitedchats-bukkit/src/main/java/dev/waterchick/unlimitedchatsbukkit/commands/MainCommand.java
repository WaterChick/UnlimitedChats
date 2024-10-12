package dev.waterchick.unlimitedchatsbukkit.commands;

import dev.waterchick.Core;
import dev.waterchick.manager.MessageManager;
import dev.waterchick.unlimitedchatsbukkit.utilities.ChatUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;

import java.util.Arrays;
import java.util.List;

public class MainCommand extends BukkitCommand {
    public MainCommand(){
        super("unlimitedchats");
        setAliases(List.of("uc"));

    }
    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if(strings.length == 0){
            commandSender.sendMessage(ChatUtils.color(MessageManager.getInstance().prefix + MessageManager.getInstance().help));
            return false;
        }if(strings.length == 1) {
            if(strings[0].equalsIgnoreCase("reload")) {
                if (!commandSender.hasPermission("uc.reload")) {
                    commandSender.sendMessage(ChatUtils.color(MessageManager.getInstance().prefix + MessageManager.getInstance().no_permission));
                    return false;
                }
                Core.getInstance().getChatsConfig().reloadConfig();
                Core.getInstance().getMessages().reloadConfig();
                commandSender.sendMessage(ChatUtils.color(MessageManager.getInstance().prefix + MessageManager.getInstance().reloaded));
                return true;
            }
        }
        return false;
    }

}