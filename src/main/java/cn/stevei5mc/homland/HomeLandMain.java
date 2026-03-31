package cn.stevei5mc.homland;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import lombok.Getter;

public class HomeLandMain extends PluginBase {

    @Getter
    private static HomeLandMain instance;
    private Config config;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        this.getServer().getScheduler().scheduleDelayedTask(this, () -> {
            this.getLogger().warning("§c警告! §c本插件为免费且开源的，如果您付费获取获取的，则有可能被误导");
            this.getLogger().info("§a开源链接和使用方法: §bhttps://github.com/stevei5mc/NewTipsVariables");
        },20);
    }

    @Override
    public void onDisable() {
        this.getLogger().info("已停止运行，感谢你的使用");
    }
}