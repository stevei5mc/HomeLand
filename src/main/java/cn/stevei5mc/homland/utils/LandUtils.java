package cn.stevei5mc.homland.utils;

import cn.nukkit.Player;
import cn.nukkit.event.player.PlayerTeleportEvent;
import cn.nukkit.level.Level;
import cn.nukkit.level.Location;
import cn.nukkit.utils.ConfigSection;
import cn.stevei5mc.homland.HomeLandMain;
import cn.stevei5mc.homland.land.LandTemplate;
import cn.stevei5mc.homland.utils.enums.LandDataDirectory;
import cn.stevei5mc.homland.utils.enums.SaveDateType;
import lombok.Getter;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class LandUtils {

    private static final HomeLandMain main = HomeLandMain.getInstance();

    @Getter
    private static final Map<String, LandTemplate> landTemplates = new HashMap<>();

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

    public static void createLand(Player targetPlayer, LandTemplate template) {
        try {
            String landName = "land-" + LandUtils.getSaveDate(targetPlayer);
            targetPlayer.sendMessage("§a领地正在生成中，请耐心等候。");
            ZipUtils.decompress(main.getDataFolder() + "/test.zip", main.getServer().getDataPath() + "/worlds/" + landName);
            main.getServer().loadLevel(landName);
            Level level = main.getServer().getLevelByName(landName);
            targetPlayer.sendMessage("§a领地生成成功，正在尝试将您传送至领地。");
            if (level != null) {
                level.setSpawnLocation(template.getSpawnPosition());
                Location location = template.getTpPosition().setLevel(level)
                targetPlayer.teleport(location, PlayerTeleportEvent.TeleportCause.PLUGIN);
            }else {
                targetPlayer.sendMessage("§c传送至目标领地失败，请自行传送至领地");
            }
        } catch (IOException e) {
            targetPlayer.sendMessage("§c创建领地时发生了一个错误，请尝试重新创建，如果还是失败请及时联系管理员！");
            main.getLogger().error("§c在创建领地时发生一个错误！", e);
        }
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

    public static void loadLandTemplate() {
        landTemplates.clear();
        ConfigSection configSections = main.getTemplatesConfig().getSections("land-templates");
        for (String template: configSections.getAllMap().keySet()) {
            landTemplates.put(template, new LandTemplate(configSections.getSection(template)));
        }
    }
}