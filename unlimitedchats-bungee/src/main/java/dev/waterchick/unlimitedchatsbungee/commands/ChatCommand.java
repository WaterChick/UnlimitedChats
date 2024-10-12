package dev.waterchick.unlimitedchatsbungee.commands;

import dev.waterchick.Chat;
import dev.waterchick.manager.MessageManager;
import dev.waterchick.unlimitedchatsbungee.utilities.ChatUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.plugin.Command;

public class ChatCommand extends Command {

    private Chat chat;
    public ChatCommand(Chat chat){
        super(chat.getCommand().replace("/",""), "", chat.getAliases().toArray(new String[chat.getAliases().size()]));
        this.chat = chat;
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        MessageManager mm = MessageManager.getInstance();
        if(!commandSender.hasPermission(chat.getPermission())){
            commandSender.sendMessage(ChatUtils.color(mm.prefix + mm.no_permission));
            return;
        }
        if(strings.length == 0){
            commandSender.sendMessage(ChatUtils.color(mm.prefix + mm.supply_message));
            return;
        }
        if(!(commandSender instanceof ProxiedPlayer senderPlayer)) return;
        Server server = senderPlayer.getServer();
        String serverName = server.getInfo().getName();
        for(ProxiedPlayer player : ProxyServer.getInstance().getPlayers()){
            if(player.hasPermission(chat.getPermission())){
                StringBuilder message = new StringBuilder();
                for (String string : strings) { //loop threw all the arguments
                    String arg = string + " "; //get the argument, and add a space so that the words get spaced out
                    message.append(arg); //add the argument to myString
                }
                String message_format = chat.getMessage();
                message_format = ChatUtils.color(message_format.replace("%server%",serverName));
                message_format = ChatUtils.color(message_format.replace("%player%",commandSender.getName()));
                message_format = ChatUtils.color(message_format.replace("%message%", message.toString()));
                TextComponent message_component = new TextComponent(message_format);
                if(mm.hover_enabled) {
                    message_component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatUtils.color(mm.hover_text.replace("%server%", player.getServer().getInfo().getName()).replace("%player%",player.getName()))).create()));
                    message_component.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND,ChatUtils.color(mm.hover_command.replace("%server%", player.getServer().getInfo().getName()).replace("%player%",player.getName()).replace("%chat_command%",chat.getCommand()))));
                }
                player.sendMessage(message_component);
            }
        }
    }
}
