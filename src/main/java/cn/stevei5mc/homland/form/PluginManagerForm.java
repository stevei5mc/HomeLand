package cn.stevei5mc.homland.form;

import cn.lanink.gamecore.form.element.ResponseElementButton;
import cn.lanink.gamecore.form.windows.AdvancedFormWindowSimple;
import cn.nukkit.Player;
import cn.stevei5mc.homland.HomeLandMain;
import org.jetbrains.annotations.NotNull;

public class PluginManagerForm {

    private static final HomeLandMain main = HomeLandMain.getInstance();

    public static void sendMain(@NotNull Player player) {
        AdvancedFormWindowSimple simple = new AdvancedFormWindowSimple("HomeLand 管理员菜单");
        simple.addButton(new ResponseElementButton("重载配置文件").onClicked(player1 -> main.getServer().dispatchCommand(player1, "land-admin reload")));
        player.showFormWindow(simple);
    }
}