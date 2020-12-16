package me.alexprogrammerde.shadowoffline;

import io.github.waterfallmc.waterfall.event.ConnectionInitEvent;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ShadowListener implements Listener {
    protected boolean mainOnline = true;

    @EventHandler
    public void onPing(ConnectionInitEvent event) {
        if (!mainOnline) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPreLogin(PreLoginEvent ple) {
        if (!mainOnline) {
            ple.setCancelled(true);
        }
    }
}
