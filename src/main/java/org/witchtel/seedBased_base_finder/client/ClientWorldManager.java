package org.witchtel.seedBased_base_finder.client;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.WorldGenSettings;
import net.minecraft.world.level.storage.LevelStorage;
import org.slf4j.Logger;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientWorldManager {
    private static ExecutorService worldGenExecutor;
    private static MinecraftServer server;
    private static Logger LOGGER = SeedBased_base_finderClient.LOGGER;
    public static void createClientWorld(String seed) {
//        worldGenExecutor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
//        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
//            ClientWorldManager.server = server;
//            CompletableFuture.runAsync(() -> {
//                try {
//                    LOGGER.info("Starting parallel world gen");
//                    WorldGenSettings worldGenSettings = server.getOverworld().toServerWorld().
//                }
//            })
//        });

    }
}
