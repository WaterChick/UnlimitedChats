package dev.waterchick.unlimitedchatsvelocity.utilities;


import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class ChatUtils {
    public static Component color(String message){
        return LegacyComponentSerializer.legacy('&').deserialize(message);
    }
}
