package cn.stevei5mc.homland.form;

import cn.lanink.gamecore.form.element.ResponseElementButton;
import cn.lanink.gamecore.form.windows.AdvancedFormWindowSimple;
import cn.nukkit.Player;
import cn.stevei5mc.homland.HomeLandMain;
import cn.stevei5mc.homland.land.LandTemplate;
import cn.stevei5mc.homland.utils.LandUtils;
import org.jetbrains.annotations.NotNull;

public class CreateLandForm {

    private static final HomeLandMain main = HomeLandMain.getInstance();

    public static void createLand(@NotNull Player player) {
        AdvancedFormWindowSimple simple = new AdvancedFormWindowSimple("创建领地");

        for (String land: LandUtils.getLandTemplates().keySet()) {
            LandTemplate template = LandUtils.getLandTemplates().get(land);
            simple.addButton(new ResponseElementButton(template.getName()).onClicked(player1 -> LandUtils.createLand(player1, template)));
        }
        player.showFormWindow(simple);
    }
}