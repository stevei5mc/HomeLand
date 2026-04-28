package cn.stevei5mc.homland;

import cn.nukkit.level.Location;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import cn.stevei5mc.homland.commands.HomeCmd;
import cn.stevei5mc.homland.commands.admin.AdminMainCmd;
import cn.stevei5mc.homland.commands.user.LandMainCmd;
import cn.stevei5mc.homland.listener.PlayerListener;
import cn.stevei5mc.homland.tasks.SaveLandWorldTask;
import cn.stevei5mc.homland.utils.FilesUtils;
import cn.stevei5mc.homland.utils.LandUtils;
import cn.stevei5mc.homland.utils.enums.LandDataDirectory;
import lombok.Getter;

public class HomeLandMain extends PluginBase {

    @Getter
    private static HomeLandMain instance;
    @Getter
    private Config config;
    @Getter
    private Config templatesConfig;
    @Getter
    private Location hubLocation;

    @Override
    public void onLoad() {
        instance = this;
        saveResources();
        loadResource();
    }

    @Override
    public void onEnable() {
        this.hubLocation = new Location(this.config.getDouble("hub.x"), this.config.getDouble("hub.y"), this.config.getDouble("hub.z"), this.config.getDouble("hub.yaw"),
                this.config.getDouble("hub.pitch"), this.getServer().getLevelByName(this.config.getString("hub.world")));

        this.getServer().getPluginManager().registerEvents(new PlayerListener(), this);

        this.getServer().getCommandMap().register("land", new LandMainCmd("land", "HomeLand 主命令"));
        this.getServer().getCommandMap().register("home", new HomeCmd("home", "HomeLand 返回领地命令"));
        this.getServer().getCommandMap().register("land-admin", new AdminMainCmd("land-admin", "HomeLand 管理员主命令"));

        this.getServer().getScheduler().scheduleRepeatingTask(this, new SaveLandWorldTask(this), this.config.getInt("landSaveCycle", 10) * 60 * 20,true);
        this.getServer().getScheduler().scheduleDelayedTask(this, () -> {
            this.getLogger().warning("§c警告! §c本插件为免费且开源的，如果您付费获取获取的，则有可能被误导");
            this.getLogger().info("§a开源链接和使用方法: §bhttps://github.com/stevei5mc/HomeLand");
        },20);
    }

    @Override
    public void onDisable() {
        this.getLogger().info("已停止运行，感谢你的使用");
    }

    private void saveResources() {
        this.saveResource("config.yml");
        this.saveResource("templates/templates-config.yml");
    }

    public void loadResource() {
        this.config = new Config(this.getDataFolder() + "/config.yml", Config.YAML);
        this.templatesConfig = new Config(this.getDataFolder() + "/templates/templates-config.yml", Config.YAML);

        String directoryPath = getLandDataPath();
        if (this.config.getString("saveDataPath", "{server}").toLowerCase().trim().equals("{server}")) {
            directoryPath = this.getDataFolder() + "/data";
        }
        for (LandDataDirectory dir: LandDataDirectory.values()) {
            FilesUtils.createDirectory(directoryPath + "/" + dir.getName());
        }

        LandUtils.loadLandTemplate();
    }

    public String getLandDataPath() {
        String directoryPath = this.config.getString("saveDataPath").trim();
        if (this.config.getString("saveDataPath", "{server}").toLowerCase().trim().equals("{server}")) {
            directoryPath = this.getDataFolder() + "/data";
        }
        return directoryPath;
    }
}