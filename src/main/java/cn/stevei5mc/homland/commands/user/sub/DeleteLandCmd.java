package cn.stevei5mc.homland.commands.user.sub;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import cn.stevei5mc.homland.commands.base.BaseSubCommand;
import cn.stevei5mc.homland.utils.LandUtils;

public class DeleteLandCmd extends BaseSubCommand {
    public DeleteLandCmd(String name) {
        super(name);
    }

    @Override
    public boolean canUser(CommandSender sender) {
        return sender.isPlayer();
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        Player player = (Player) sender;
        LandUtils.deleteLand(player);
        return true;
    }

    @Override
    public CommandParameter[] getParameters() {
        return new CommandParameter[0];
    }
}