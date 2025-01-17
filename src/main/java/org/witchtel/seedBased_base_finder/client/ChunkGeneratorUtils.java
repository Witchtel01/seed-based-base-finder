package org.witchtel.seedBased_base_finder.client;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkStatus;
import org.slf4j.Logger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChunkGeneratorUtils {
    private static final Logger LOGGER = SeedBased_base_finderClient.LOGGER;
    private static final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    public void createTempWorld(MinecraftServer server) {
        server.getWorlds().forEach(this::generateChunksInBackground);
    }

    private void generateChunksInBackground(World world) {
        executor.submit(() -> {
            try {
                while (true) {
                    ChunkPos chunkPos = findChunkToGenerate(world);
                    if (chunkPos == null) {
                        break;
                    }

                    Chunk chunk = world.getChunk(chunkPos.x, chunkPos.z, ChunkStatus.FULL);

                    LOGGER.info("Generated chunk {}", chunkPos);

                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }

    private ChunkPos findChunkToGenerate(World world) {
        for (int x = -100; x < 100; x ++) {
            for (int z = -100; z < 100; z++) {
                ChunkPos chunkPos = new ChunkPos(x, z);
                if (!world.getChunk(x, z).isEmpty()) {
                    return chunkPos;
                }
            }
        }
        return null;
    }
}
