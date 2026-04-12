package cn.stevei5mc.homland.tasks;

import cn.nukkit.scheduler.PluginTask;
import cn.stevei5mc.homland.HomeLandMain;
import cn.stevei5mc.homland.utils.ZipUtils;

import java.io.IOException;

public class SaveLandWorldTask extends PluginTask<HomeLandMain> {

    private final HomeLandMain main = HomeLandMain.getInstance();
    public SaveLandWorldTask(HomeLandMain main) {
        super(main);
    }

    @Override
    public void onRun(int i) {
         this.main.getServer().getLevels().forEach((i1, level) -> {
             if (level.getFolderName().startsWith("land-")) {
                 level.save();
                 level.getPlayers().forEach((i2, player) -> {
                     player.sendMessage("§a正在为您保存所在的领地");
                 });
                 try {
                     ZipUtils.compress(main.getServer().getDataPath() + "/worlds/" + level.getFolderName() + "/", main.getLandDataPath() + "/player_land/" + level.getFolderName());
                 } catch (IOException e) {
                     main.getLogger().error("§c在自动保存领地时发生错误", e);
                 }
             }
         });
    }
}