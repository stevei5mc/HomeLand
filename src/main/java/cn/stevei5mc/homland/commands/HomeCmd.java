package cn.stevei5mc.homland.commands;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.stevei5mc.homland.commands.base.BaseCommand;

public class HomeCmd extends BaseCommand {
    public HomeCmd(String name, String description) {
        super(name, description);
        this.setPermission("homeland.user");
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if (sender.isPlayer()) {
            main.getServer().dispatchCommand(sender, "land home");
        }
        return true;
    }

    @Override
    public void sendHelp(CommandSender sender) {}

    @Override
    public void sendUI(Player player) {}
}