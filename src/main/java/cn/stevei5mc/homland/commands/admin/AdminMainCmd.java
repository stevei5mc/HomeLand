package cn.stevei5mc.homland.commands.admin;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.stevei5mc.homland.commands.admin.sub.ReloadCmd;
import cn.stevei5mc.homland.commands.base.BaseCommand;
import cn.stevei5mc.homland.form.PluginManagerForm;

public class AdminMainCmd extends BaseCommand {
    public AdminMainCmd(String name, String description) {
        super(name, description);
        this.setPermission("homeland.admin");
        this.addSubCommand(new ReloadCmd("reload"));
        this.setAliases("lda");
    }

    @Override
    public void sendHelp(CommandSender sender) {}

    @Override
    public void sendUI(Player player) {
        PluginManagerForm.sendMain(player);
    }
}