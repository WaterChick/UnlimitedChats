package dev.waterchick.unlimitedchatsvelocity.commands;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.ServerConnection;
import com.velocitypowered.api.proxy.server.ServerInfo;
import dev.waterchick.Chat;
import dev.waterchick.manager.MessageManager;
import dev.waterchick.unlimitedchatsvelocity.utilities.ChatUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class ChatCommand implements SimpleCommand {

    private final Chat chat;
    private final ProxyServer proxyServer;

    public ChatCommand(Chat chat, ProxyServer proxyServer){
        this.chat = chat;
        this.proxyServer = proxyServer;
    }

    @Override
    public void execute(Invocation invocation) {
        CommandSource sender = invocation.source();
        String[] args =invocation.arguments();
        MessageManager mm = MessageManager.getInstance();
        if(!sender.hasPermission(chat.getPermission())){
            sender.sendMessage(ChatUtils.color(mm.prefix + mm.no_permission));
            return;
        }
        if(!(sender instanceof Player senderPlayer)) return;
        if(args.length == 0){
            sender.sendMessage(ChatUtils.color(mm.prefix + mm.supply_message));
            return;
        }
        Optional<ServerConnection> optionalServer = senderPlayer.getCurrentServer();
        if(optionalServer.isEmpty()) return;
        ServerConnection server =  optionalServer.get();
        String serverName = server.getServerInfo().getName();
        for(Player player : proxyServer.getAllPlayers()){
            if(player.hasPermission(chat.getPermission())){
                StringBuilder message = new StringBuilder();
                for (String string : args) { //loop threw all the arguments
                    String arg = string + " "; //get the argument, and add a space so that the words get spaced out
                    message.append(arg); //add the argument to myString
                }
                String message_format = chat.getMessage();

                Component messageComponent =  ChatUtils.color(message_format.replace("%server%",serverName)
                        .replace("%player%",senderPlayer.getUsername())
                        .replace("%message%", message.toString())

                );
                if(mm.hover_enabled) {
                    messageComponent = messageComponent.hoverEvent(HoverEvent.showText(ChatUtils.color(mm.hover_text.replace("%server%", serverName).replace("%player%",senderPlayer.getUsername()))));
                    messageComponent = messageComponent.clickEvent(ClickEvent.suggestCommand(mm.hover_command.replace("%server%", serverName).replace("%player%",senderPlayer.getUsername()).replace("%chat_command%",chat.getCommand())));
                }
                player.sendMessage(messageComponent);
            }
        }
    }

    @Override
    public CompletableFuture<List<String>> suggestAsync(Invocation invocation) {
        return SimpleCommand.super.suggestAsync(invocation);
    }

    @Override
    public boolean hasPermission(Invocation invocation) {
        return invocation.source().hasPermission(chat.getPermission());
    }
}
