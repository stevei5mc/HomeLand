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
        this.position = new Double[]{Double.parseDouble(pos[0]), Double.parseDouble(pos[1]), Double.parseDouble(pos[2]), Double.parseDouble(pos[3]), Double.parseDouble(pos[4]), Double.parseDouble(pos[5])};
        if (pos.length == 6) {
            this.spawnPosition = new Position(position[0], position[1], position[2]);
        }
    }

    private final ConfigSection templateConfig;
    private final String name;
    private final String file;
    @Getter(AccessLevel.NONE)
    private final Double[] position;
    private Position spawnPosition;

    public Location getTpLocation(Level level) {
        return new Location(position[0], position[1], position[2], position[3], position[4], position[5], level);
    }
}