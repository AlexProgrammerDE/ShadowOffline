package me.alexprogrammerde.shadowoffline;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.file.Files;
import java.util.concurrent.TimeUnit;

@SuppressWarnings({"deprecation"})
public final class ShadowOffline extends Plugin {
    ShadowListener listener = new ShadowListener();

    @Override
    public void onEnable() {
        if (!getDataFolder().exists())
            getDataFolder().mkdir();

        File file = new File(getDataFolder(), "config.yml");

        if (!file.exists()) {
            try (InputStream in = getResourceAsStream("config.yml")) {
                Files.copy(in, file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Configuration configuration = null;

        try {
            configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), "config.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Configuration finalConfiguration = configuration;
        getProxy().getScheduler().schedule(this, () -> {
            try {
                InetSocketAddress address = getProxy().getServerInfo(finalConfiguration.getString("mainname")).getAddress();

                Socket s = new Socket(address.getAddress(), address.getPort());
                // ONLINE
                s.close();
                listener.mainOnline = true;
            } catch (IOException e) {
                listener.mainOnline = false;
            }
        }, 0, 1, TimeUnit.SECONDS);

        getProxy().getPluginManager().registerListener(this, listener);
    }

    @Override
    public void onDisable() {}
}
