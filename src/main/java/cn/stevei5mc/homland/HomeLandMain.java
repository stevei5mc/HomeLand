package cn.stevei5mc.homland;

import cn.nukkit.level.Location;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import cn.stevei5mc.homland.commands.land.LandMainCmd;
import cn.stevei5mc.homland.listener.PlayerListener;
import lombok.Getter;

public class HomeLandMain extends PluginBase {

    @Getter
    private static HomeLandMain instance;
    @Getter
    private Config config;
    @Getter
    private Location hubLocation;

    @Override
    public void onLoad() {
        instance = this;
        this.saveResource("config.yml");
        this.config = new Config(this.getDataFolder() + "/config.yml", Config.YAML);
    }

    @Override
    public void onEnable() {
        this.hubLocation = new Location(this.config.getDouble("hub.x"), this.config.getDouble("hub.y"), this.config.getDouble("hub.z"), this.config.getDouble("hub.yaw"),
                this.config.getDouble("hub.pitch"), this.getServer().getLevelByName(this.config.getString("hub.world")));
        this.getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        this.getServer().getCommandMap().register("land", new LandMainCmd("land", "HomeLand 主命令"));
        this.getServer().getScheduler().scheduleDelayedTask(this, () -> {
            this.getLogger().warning("§c警告! §c本插件为免费且开源的，如果您付费获取获取的，则有可能被误导");
            this.getLogger().info("§a开源链接和使用方法: §bhttps://github.com/stevei5mc/HomeLand");
        },20);
    }

    @Override
    public void onDisable() {
        this.getLogger().info("已停止运行，感谢你的使用");
    }
}