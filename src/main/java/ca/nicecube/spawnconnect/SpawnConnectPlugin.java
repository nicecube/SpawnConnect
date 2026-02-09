package ca.nicecube.spawnconnect;

import ca.nicecube.spawnconnect.config.SpawnConnectConfig;
import ca.nicecube.spawnconnect.config.SpawnConnectConfigService;
import ca.nicecube.spawnconnect.events.HubSpawnOnConnectListener;
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;

import javax.annotation.Nonnull;

public class SpawnConnectPlugin extends JavaPlugin {
    private SpawnConnectConfig config;

    public SpawnConnectPlugin(@Nonnull JavaPluginInit init) {
        super(init);
    }

    @Override
    protected void setup() {
        SpawnConnectConfigService configService = new SpawnConnectConfigService(this.getLogger(), this.getDataDirectory());
        this.config = configService.loadOrCreate();

        this.getEventRegistry().registerGlobal(
            PlayerReadyEvent.class,
            new HubSpawnOnConnectListener(this.config, this.getLogger())::onPlayerReady
        );

        this.getLogger().atInfo().log(
            "[%s] Enabled. Config: %s | overrideSpawn=%s",
            this.getName(),
            configService.getConfigPath().toAbsolutePath(),
            this.config.isOverrideSpawn()
        );
        this.getLogger().atInfo().log(
            "[%s] If overrideSpawn=true, players will be teleported to configured xyz.",
            this.getName()
        );
    }

    @Override
    protected void shutdown() {
        this.getLogger().atInfo().log("[%s] Disabled.", this.getName());
    }
}
