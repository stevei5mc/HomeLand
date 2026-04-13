package cn.stevei5mc.homland.tasks;

import cn.nukkit.scheduler.PluginTask;
import cn.stevei5mc.homland.HomeLandMain;
import cn.stevei5mc.homland.utils.FilesUtils;
import cn.stevei5mc.homland.utils.ZipUtils;
import cn.stevei5mc.homland.utils.enums.LandDataDirectory;

import java.io.IOException;
import java.nio.file.Paths;

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
                 if (level.getPlayers().size() == 0) {
                     level.unload();
                     try {
                         FilesUtils.deleteDirectory(Paths.get(main.getServer().getDataPath() + "/worlds/" + level.getFolderName()));
                     } catch (IOException e) {
                         main.getLogger().error("删除世界文件夹时发生了一个错误", e);
                     }
                 }else {
                     level.getPlayers().forEach((i2, player) -> {
                         player.sendMessage("§a正在为您保存所在的领地");
                     });
                     try {
                         ZipUtils.compress(main.getServer().getDataPath() + "/worlds/" + level.getFolderName() + "/", main.getLandDataPath() + LandDataDirectory.PLAYER_LAND.getPath() + level.getFolderName());
                     } catch (IOException e) {
                         main.getLogger().error("§c在自动保存领地时发生错误", e);
                     }
                 }
             }
         });
    }
}