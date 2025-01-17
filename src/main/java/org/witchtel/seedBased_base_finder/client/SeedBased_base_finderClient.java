package org.witchtel.seedBased_base_finder.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.witchtel.seedBased_base_finder.client.commands.GenerateChunksCommand;
import org.witchtel.seedBased_base_finder.client.commands.GetSeedCommand;
import org.witchtel.seedBased_base_finder.client.commands.SetSeedCommand;

public class SeedBased_base_finderClient implements ClientModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger(SeedBased_base_finderClient.class);
    public static String seed;
    public static SeedStorage config;

    @Override
    public void onInitializeClient() {
        config = new SeedStorage();
        ClientPlayConnectionEvents.JOIN.register((phase, listener, client)-> {
                handleSeedOnJoin(client);
            }
        );

        ClientPlayConnectionEvents.DISCONNECT.register((phase, client) -> {
                System.out.println("Player disconnected");
                seed = null;
            }
        );

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
                SetSeedCommand.register(dispatcher);
                GetSeedCommand.register(dispatcher);
                GenerateChunksCommand.register(dispatcher);
            }
        );
    }
    private void handleSeedOnJoin(MinecraftClient client) {
        if(client.getCurrentServerEntry() != null) {
            String serverAddress = client.getCurrentServerEntry().address;
            if (config.getSeed(serverAddress) == null) {
                client.player.sendMessage(Text.literal("Enter server seed using /setseed"), false);
            } else {
                seed = config.getSeed(serverAddress);
                client.player.sendMessage(Text.literal("Found stored server seed: %s".formatted(seed)), false);
                LOGGER.info("Seed is: {}", seed);
            }
            LOGGER.info("Player joined a server");
        }
    }
}