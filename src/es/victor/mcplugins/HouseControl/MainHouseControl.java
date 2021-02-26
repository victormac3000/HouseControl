package es.victor.mcplugins.HouseControl;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class MainHouseControl extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        // OnPlayerJoin Listener
        getServer().getPluginManager().registerEvents(this,this);
        // Command listeners
        Objects.requireNonNull(this.getCommand("house")).setExecutor(new HouseRouter());
    }

    @Override
    public void onDisable() {

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Messages playerMessages = new Messages(event.getPlayer());
        if (event.getPlayer().hasPlayedBefore()) {
            playerMessages.greetings();
        } else {
            playerMessages.introduce();
        }
    }
}
