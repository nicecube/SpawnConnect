package ca.nicecube.spawnconnect.events;

import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import com.hypixel.hytale.server.core.modules.entity.teleport.Teleport;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.spawn.ISpawnProvider;
import com.hypixel.hytale.math.vector.Transform;

public class HubSpawnOnConnectListener {

    public static void onPlayerReady(PlayerReadyEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();
        if (world == null) {
            return;
        }

        ISpawnProvider spawnProvider = world.getWorldConfig().getSpawnProvider();
        if (spawnProvider == null) {
            return;
        }

        PlayerRef playerRef = world.getEntityStore().getStore().getComponent(event.getPlayerRef(), PlayerRef.getComponentType());
        if (playerRef == null) {
            return;
        }

        Transform spawnTransform = spawnProvider.getSpawnPoint(world, playerRef.getUuid());
        if (spawnTransform == null) {
            return;
        }

        world.execute(() -> world.getEntityStore().getStore().addComponent(
            event.getPlayerRef(),
            Teleport.getComponentType(),
            Teleport.createForPlayer(world, spawnTransform)
        ));
    }

}
