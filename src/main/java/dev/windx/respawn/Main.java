package dev.windx.respawn;

import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    public static Main plugin;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new Listener(), this);
        getLogger().info("plugin load!");
    }

    public static Main getInstance() {
        return plugin;
    }
}
