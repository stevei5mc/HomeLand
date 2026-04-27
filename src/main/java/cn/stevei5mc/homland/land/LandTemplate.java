package cn.stevei5mc.homland.land;

import cn.nukkit.level.Level;
import cn.nukkit.level.Location;
import cn.nukkit.level.Position;
import cn.nukkit.utils.ConfigSection;
import lombok.AccessLevel;
import lombok.Getter;

@Getter
public class LandTemplate {

    public LandTemplate(ConfigSection template) {
        this.templateConfig = template;
        this.name = template.getString("name");
        this.file = template.getString("file") + ".zip";
        String[] pos = template.getString("spawnPosition").split("&");
        if (pos.length == 6) {
            this.spawnPosition = new Position(Double.parseDouble(pos[0]), Double.parseDouble(pos[1]), Double.parseDouble(pos[2]));
            this.tpLocation = new Location(Double.parseDouble(pos[0]), Double.parseDouble(pos[1]), Double.parseDouble(pos[2]), Double.parseDouble(pos[3]), Double.parseDouble(pos[4]), Double.parseDouble(pos[5]));
        }
    }

    private final ConfigSection templateConfig;
    private final String name;
    private final String file;
    private Position spawnPosition;
    @Getter(AccessLevel.NONE)
    private Location tpLocation;

    public Location getTpLocation(Level level) {
        LandTemplate template = new LandTemplate(templateConfig);
        template.tpLocation.setLevel(level);
        return template.tpLocation;
    }
}