package cn.stevei5mc.homland.land;

import cn.nukkit.level.Location;
import cn.nukkit.level.Position;
import cn.nukkit.utils.ConfigSection;
import lombok.Getter;

@Getter
public class LandTemplate {

    public LandTemplate(ConfigSection template) {
        this.name = template.getString("name");
        this.file = template.getString("file");
        String[] pos = template.getString("spawnPosition").split("&");
        if (pos.length == 6) {
            this.spawnPosition = new Position(Double.parseDouble(pos[0]), Double.parseDouble(pos[1]), Double.parseDouble(pos[2]));
            this.tpPosition = new Location(Double.parseDouble(pos[0]), Double.parseDouble(pos[1]), Double.parseDouble(pos[2]), Double.parseDouble(pos[3]), Double.parseDouble(pos[4]), Double.parseDouble(pos[5]));
        }
    }

    private final String name;
    private final String file;
    private Position spawnPosition;
    private Location tpPosition;
}