package cn.stevei5mc.homland.listener;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.stevei5mc.homland.HomeLandMain;

public class PlayerListener implements Listener {

    private final HomeLandMain main = HomeLandMain.getInstance();

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        if(this.main.getConfig().getBoolean("hub.enable", true)) {
            event.getPlayer().teleport(this.main.getHubLocation());
        }
    }
}