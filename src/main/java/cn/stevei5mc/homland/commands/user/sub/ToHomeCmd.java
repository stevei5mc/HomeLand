package cn.stevei5mc.homland.commands.user.sub;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.event.player.PlayerTeleportEvent;
import cn.nukkit.level.Level;
import cn.stevei5mc.homland.commands.base.BaseSubCommand;
import cn.stevei5mc.homland.utils.LandUtils;

public class ToHomeCmd extends BaseSubCommand {
    public ToHomeCmd(String name) {
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
        String landName = "land-" + LandUtils.getSaveDate(player);
        main.getServer().loadLevel(landName);
        Level level = main.getServer().getLevelByName(landName);
        player.sendMessage("§a领地加载中，请耐心等候。");
        if (level != null) {
            player.teleport(level.getSafeSpawn(), PlayerTeleportEvent.TeleportCause.COMMAND);
        }else {
            player.sendMessage("§c传送至目标领地失败，请重试，如果还是无法传送请及时联系管理员处理！");
        }
        return true;
    }

    @Override
    public CommandParameter[] getParameters() {
        return new CommandParameter[0];
    }
}