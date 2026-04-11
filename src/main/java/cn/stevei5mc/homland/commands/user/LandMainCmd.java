package cn.stevei5mc.homland.commands.user;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.stevei5mc.homland.commands.base.BaseCommand;
import cn.stevei5mc.homland.commands.user.sub.CreateLandCmd;
import cn.stevei5mc.homland.commands.user.sub.ToHomeCmd;

public class LandMainCmd extends BaseCommand {
    public LandMainCmd(String name, String description) {
        super(name, description);
        this.setPermission("homeland.user");
        this.addSubCommand(new CreateLandCmd("create"));
        this.addSubCommand(new ToHomeCmd("home"));
    }

    @Override
    public void sendHelp(CommandSender sender) {

    }

    @Override
    public void sendUI(Player player) {

    }
}