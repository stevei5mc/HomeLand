package cn.stevei5mc.homland.commands.land;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.stevei5mc.homland.commands.base.BaseCommand;
import cn.stevei5mc.homland.commands.land.sub.CreateLandCmd;

public class LandMainCmd extends BaseCommand {
    public LandMainCmd(String name, String description) {
        super(name, description);
        this.setPermission("homeland.land");
        this.addSubCommand(new CreateLandCmd("create"));
    }

    @Override
    public void sendHelp(CommandSender sender) {

    }

    @Override
    public void sendUI(Player player) {

    }
}