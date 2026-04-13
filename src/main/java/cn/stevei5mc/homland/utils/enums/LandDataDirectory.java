package cn.stevei5mc.homland.utils.enums;

import lombok.Getter;

public enum LandDataDirectory {
    PLAYER_LAND("players_lands"),
    PLAYER_LAND_BACKUP("players_lands/backup");

    @Getter
    private final String name;
    @Getter
    private final String path;

    LandDataDirectory(String name) {
        this.name = name;
        this.path = "/" + name + "/";
    }
}