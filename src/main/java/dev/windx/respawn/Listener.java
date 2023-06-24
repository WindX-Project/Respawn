package dev.windx.respawn;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.ArrayList;
import java.util.List;

public class Listener implements org.bukkit.event.Listener {
    List<Player> creatives = new ArrayList<>();

    @EventHandler
    public void PlayerDeath (PlayerDeathEvent event) {
        Player player = event.getEntity();
        Location respawn = player.getBedSpawnLocation();
        boolean isBedSpawn = true;
        if (respawn == null) {
            respawn = player.getWorld().getSpawnLocation();
            isBedSpawn = false;
        }
        if (Main.getInstance().getConfig().getBoolean("fast", true)) {
            player.setHealth(20);
            player.teleport(respawn);
            Main.getInstance().getServer().getPluginManager().callEvent(new FastRespawnEvent(player, respawn, isBedSpawn));
            return;
        }
        if (player.getGameMode() == GameMode.CREATIVE) {
            creatives.add(player);
            player.setGameMode(GameMode.SURVIVAL);
            player.setHealth(0); //击杀玩家
        }
    }

    @EventHandler
    public void PlayerRespawn (PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        if (creatives.remove(player)) {
            player.setGameMode(GameMode.CREATIVE);
        }
    }

    static class FastRespawnEvent extends PlayerRespawnEvent {

        public FastRespawnEvent(Player respawnPlayer, Location respawnLocation, boolean isBedSpawn) {
            super(respawnPlayer, respawnLocation, isBedSpawn);
        }
    }
}
