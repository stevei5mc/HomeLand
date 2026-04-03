package cn.stevei5mc.homland.utils;

import cn.nukkit.Player;
import cn.stevei5mc.homland.HomeLandMain;

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
}