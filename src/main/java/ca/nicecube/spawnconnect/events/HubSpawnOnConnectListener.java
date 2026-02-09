package ca.nicecube.spawnconnect.events;

import ca.nicecube.spawnconnect.config.SpawnConnectConfig;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import com.hypixel.hytale.server.core.modules.entity.teleport.Teleport;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.spawn.ISpawnProvider;
import com.hypixel.hytale.math.vector.Transform;

public class HubSpawnOnConnectListener {
    private final SpawnConnectConfig config;
    private final HytaleLogger logger;

    public HubSpawnOnConnectListener(SpawnConnectConfig config, HytaleLogger logger) {
        this.config = config;
        this.logger = logger;
    }

    public void onPlayerReady(PlayerReadyEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();
        if (world == null) {
            return;
        }

        PlayerRef playerRef = world.getEntityStore().getStore().getComponent(
            event.getPlayerRef(),
            PlayerRef.getComponentType()
        );
        if (playerRef == null) {
            return;
        }

        Transform targetTransform;
        if (this.config.isOverrideSpawn()) {
            SpawnConnectConfig.OverrideCoordinates coords = this.config.getCoordinates();
            targetTransform = new Transform(coords.getX(), coords.getY(), coords.getZ());
        } else {
            ISpawnProvider spawnProvider = world.getWorldConfig().getSpawnProvider();
            if (spawnProvider == null) {
                this.logger.atWarning().log(
                    "[SpawnConnect] No spawn provider in world '%s'. Skipping teleport for %s.",
                    world.getName(),
                    player.getDisplayName()
                );
                return;
            }

            targetTransform = spawnProvider.getSpawnPoint(world, playerRef.getUuid());
        }

        if (targetTransform == null) {
            return;
        }

        world.execute(() -> world.getEntityStore().getStore().addComponent(
            event.getPlayerRef(),
            Teleport.getComponentType(),
            Teleport.createForPlayer(world, targetTransform)
        ));
    }

}
