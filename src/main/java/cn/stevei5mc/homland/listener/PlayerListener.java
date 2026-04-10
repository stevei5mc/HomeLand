package cn.stevei5mc.homland.listener;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.level.Level;
import cn.stevei5mc.homland.HomeLandMain;
import cn.stevei5mc.homland.utils.LandUtils;
import cn.stevei5mc.homland.utils.ZipUtils;

import java.io.IOException;

public class PlayerListener implements Listener {

    private final HomeLandMain main = HomeLandMain.getInstance();

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        if(this.main.getConfig().getBoolean("hub.enable", true)) {
            event.getPlayer().teleport(this.main.getHubLocation());
        }
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        String landName = "land-" + LandUtils.getSaveDate(event.getPlayer());
        Level level =  main.getServer().getLevelByName(landName);
        if (level != null) {
            main.getServer().getScheduler().scheduleDelayedTask(main, () -> {
                level.save();
                level.unload();
                try {
                    ZipUtils.compress(main.getServer().getDataPath() + "/worlds/" + landName + "/", main.getLandDataPath() + "/player_land/" + landName + ".zip");
                } catch (IOException e) {
                    main.getLogger().error("§c在尝试压缩领地世界文件时发生一个错误！", e);
                }
            }, 200, true);
        }
    }
}