package cn.stevei5mc.homland.commands.user.sub;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.event.player.PlayerTeleportEvent;
import cn.nukkit.level.Level;
import cn.stevei5mc.homland.commands.base.BaseSubCommand;
import cn.stevei5mc.homland.utils.LandUtils;
import cn.stevei5mc.homland.utils.ZipUtils;

import java.io.IOException;

public class CreateLandCmd extends BaseSubCommand {
   public CreateLandCmd(String name) {
        super(name);
    }

    @Override
    public boolean canUser(CommandSender sender) {
        return sender.isPlayer();
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        Player player = (Player) sender;
        try {
            String landName = "land-" + LandUtils.getSaveDate(player);
            player.sendMessage("§a领地正在生成中，请耐心等候。");
            ZipUtils.decompress(main.getDataFolder() + "/test.zip", main.getServer().getDataPath() + "/worlds/" + landName);
            main.getServer().loadLevel(landName);
            Level level = main.getServer().getLevelByName(landName);
            player.sendMessage("§a领地生成成功，正在尝试将您传送至领地。");
            if (level != null) {
                player.teleport(level.getSafeSpawn(), PlayerTeleportEvent.TeleportCause.COMMAND);
            }else {
                player.sendMessage("§c传送至目标领地失败，请自行传送至领地");
            }
        } catch (IOException e) {
            player.sendMessage("§c创建领地时发生了一个错误，请尝试重新创建，如果还是失败请及时联系管理员！");
            main.getLogger().error("§c在创建领地时发生一个错误！", e);
        }
        return true;
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }

    @Override
    public CommandParameter[] getParameters() {
        return new CommandParameter[0];
    }
}