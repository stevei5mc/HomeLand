package cn.stevei5mc.homland.commands.user.sub;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.level.Level;
import cn.stevei5mc.homland.commands.base.BaseSubCommand;
import cn.stevei5mc.homland.utils.LandUtils;
import cn.stevei5mc.homland.utils.ZipUtils;
import cn.stevei5mc.homland.utils.enums.LandDataDirectory;

import java.io.IOException;

public class BackupLandCmd extends BaseSubCommand {
    public BackupLandCmd(String name) {
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
        Level level = main.getServer().getLevelByName(landName);
        if (level != null) {
            try {
                level.save();
                ZipUtils.compress(main.getServer().getDataPath() + "/worlds/" + level.getFolderName() + "/",
                        main.getLandDataPath() + LandDataDirectory.PLAYER_LAND_BACKUP.getPath() + level.getFolderName() + "_backup");
                player.sendMessage("§a领地备份成功");
            } catch (IOException e) {
                sender.sendMessage("§c领地备份失败，请重试，如果还是失败请及时联系管理员！");
                main.getLogger().error("§c在备份领地时出现问题", e);
            }
        }
        return true;
    }

    @Override
    public CommandParameter[] getParameters() {
        return new CommandParameter[0];
    }
}
