package me.ed333.easyBot;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

public class Events implements ValuePool, Listener {
    @EventHandler
    private void onJoin(@NotNull PlayerJoinEvent event) {
        Player p = event.getPlayer();
        if (ValuePool.vars.PlayerData.getBoolean(p.getUniqueId() + ".enable_Bot")) {
            enabled_Bot_Player.add(p);
        }else if (!utils.name_isBound(p.getName())) {
            ValuePool.vars.PlayerData.set(p.getUniqueId() + ".enable_Bot", true);
            enabled_Bot_Player.add(p);
        }

        sender.sendMessage(enabled_Bot_Player.toString());
    }

    @EventHandler
    private void onLeave(@NotNull PlayerQuitEvent event) {
        enabled_Bot_Player.remove(event.getPlayer());
    }

    @EventHandler
    private void onChat(@NotNull AsyncPlayerChatEvent event) throws Exception {
        String message = event.getMessage();
        if (bot.isConnected)
            utils.sendGroupMessage(
                    ValuePool.vars.sessionKey,
                    groupID,
                    false,
                    0,
                    new JSONArray().element(
                            new JSONObject().element("type", "Plain")
                                    .element("text", event.getPlayer().getName() + ": " + message)
                    ));
    }
}
