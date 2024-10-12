package dev.waterchick.unlimitedchatsvelocity.commands;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import dev.waterchick.Core;
import dev.waterchick.manager.MessageManager;
import dev.waterchick.unlimitedchatsvelocity.utilities.ChatUtils;

public class MainCommand implements SimpleCommand {

    @Override
    public void execute(Invocation invocation) {
        CommandSource sender = invocation.source();
        String[] args = invocation.arguments();
        if(args.length != 1 && !args[0].equalsIgnoreCase("reload")){
            sender.sendMessage(ChatUtils.color(MessageManager.getInstance().prefix + MessageManager.getInstance().help));
            return;
        }
        if (!sender.hasPermission("uc.reload")) {
            sender.sendMessage(ChatUtils.color(MessageManager.getInstance().prefix + MessageManager.getInstance().no_permission));
            return;
        }

        Core.getInstance().getChatsConfig().reloadConfig();
        Core.getInstance().getMessages().reloadConfig();
        sender.sendMessage(ChatUtils.color(MessageManager.getInstance().prefix + MessageManager.getInstance().reloaded));
    }
}
