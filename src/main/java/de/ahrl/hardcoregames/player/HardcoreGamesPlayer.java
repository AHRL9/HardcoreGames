package de.ahrl.hardcoregames.player;

import de.ahrl.hardcoregames.utility.Kit;
import de.ahrl.hardcoregames.utility.pvp.LastHit;

import java.util.UUID;

public interface HardcoreGamesPlayer {
    Kit getKit();

    void setKit(Kit kit);

    boolean hasKit(Kit kit);

    void enableKit();

    void disableKit();

    void enableKitFunction();

    void disableKitFunction();

    int getCPS();

    void addClick();

    UUID getUUID();

    String getDisplayName();

    LastHit getLastHit();
}
