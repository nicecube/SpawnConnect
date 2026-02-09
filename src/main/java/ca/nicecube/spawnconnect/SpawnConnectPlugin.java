package ca.nicecube.spawnconnect;

import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import ca.nicecube.spawnconnect.events.HubSpawnOnConnectListener;

import javax.annotation.Nonnull;

public class SpawnConnectPlugin extends JavaPlugin {

    public SpawnConnectPlugin(@Nonnull JavaPluginInit init) {
        super(init);
    }

    @Override
    protected void setup() {
        this.getEventRegistry().registerGlobal(PlayerReadyEvent.class, HubSpawnOnConnectListener::onPlayerReady);
    }
}
