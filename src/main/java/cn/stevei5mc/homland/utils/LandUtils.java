package cn.stevei5mc.homland.utils;

import cn.nukkit.Player;
import cn.nukkit.level.Level;
import cn.stevei5mc.homland.HomeLandMain;
import cn.stevei5mc.homland.utils.enums.LandDataDirectory;
import cn.stevei5mc.homland.utils.enums.SaveDateType;

import java.io.IOException;
import java.nio.file.Paths;

public class LandUtils {

    private static final HomeLandMain main = HomeLandMain.getInstance();

    public static SaveDateType getSaveType() {
        switch (main.getConfig().getString("saveType", "auto").toLowerCase().trim()) {
            case "xuid": return SaveDateType.XUID;
            case "name": return SaveDateType.NAME;
            default: return SaveDateType.AUTO;
        }
    }

    public static String getSaveDate(Player player) {
        boolean rule = (getSaveType().equals(SaveDateType.AUTO) && main.getServer().xboxAuth) || getSaveType().equals(SaveDateType.XUID);
        return rule ? player.getLoginChainData().getXUID() : player.getName();
    }

    public static void deleteLand(Player targetPlayer) {
        String landName = "land-" + LandUtils.getSaveDate(targetPlayer);
        Level level = main.getServer().getLevelByName(landName);
        if (level != null) {
            level.unload();
            try {
                FilesUtils.deleteDirectory(Paths.get(main.getServer().getDataPath() + "/worlds/" + landName));
            } catch (IOException e) {
                main.getLogger().error("删除世界文件夹时发生了一个错误", e);
            }
            FilesUtils.deleteFile(main.getLandDataPath() + LandDataDirectory.PLAYER_LAND.getPath() + landName + ".zip");
            FilesUtils.deleteFile(main.getLandDataPath() + LandDataDirectory.PLAYER_LAND_BACKUP.getPath() + landName + "_backup.zip");
        }
    }
}