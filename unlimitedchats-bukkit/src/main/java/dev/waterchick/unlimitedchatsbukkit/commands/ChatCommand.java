package dev.waterchick.unlimitedchatsbukkit.commands;

import dev.waterchick.Chat;
import dev.waterchick.manager.MessageManager;
import dev.waterchick.unlimitedchatsbukkit.UnlimitedChats;
import dev.waterchick.unlimitedchatsbukkit.utilities.ChatUtils;
import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

public class ChatCommand extends BukkitCommand {


    private Chat chat;
    public ChatCommand(Chat chat){
        super(chat.getCommand().replace("/",""));
        this.setAliases(chat.getAliases());

        this.chat = chat;
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        MessageManager mm = MessageManager.getInstance();
        if(!commandSender.hasPermission(chat.getPermission())){
            commandSender.sendMessage(ChatUtils.color(mm.prefix + mm.no_permission));
            return false;
        }
        if(strings.length == 0){
            commandSender.sendMessage(ChatUtils.color(mm.prefix + mm.supply_message));
            return false;
        }
        if(!(commandSender instanceof Player senderPlayer)) return false;
        for(Player player : Bukkit.getServer().getOnlinePlayers()){
            if(player.hasPermission(chat.getPermission())){
                StringBuilder message = new StringBuilder();
                for (String string : strings) { //loop threw all the arguments
                    String arg = string + " "; //get the argument, and add a space so that the words get spaced out
                    message.append(arg); //add the argument to myString
                }
                String message_format = chat.getMessage();
                message_format = ChatUtils.color(message_format.replace("%server%",player.getServer().getName()));
                message_format = ChatUtils.color(message_format.replace("%player%",senderPlayer.getName()));
                message_format = ChatUtils.color(message_format.replace("%message%", message.toString()));
                TextComponent message_component = new TextComponent(message_format);
                String hover_text = ChatUtils.color(mm.hover_text);
                String hover_command = ChatUtils.color(mm.hover_command);
                if(UnlimitedChats.getInstance().canPAPIHook()){
                    hover_text = PlaceholderAPI.setPlaceholders(player, hover_text);
                    hover_command = PlaceholderAPI.setPlaceholders(player, hover_command);
                }
                if(mm.hover_enabled) {
                    message_component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(hover_text.replace("%server%", Bukkit.getServer().getName()).replace("%player%",player.getName())).create()));
                    message_component.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND,hover_command.replace("%server%", Bukkit.getServer().getName()).replace("%player%",player.getName()).replace("%chat_command%",chat.getCommand())));
                }

                player.spigot().sendMessage(message_component);
            }
        }
        return true;
    }
}
