package dev.waterchick.unlimitedchatsbungee.utilities;

import net.md_5.bungee.api.ChatColor;

public class ChatUtils {
    public static String color(String message){
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
