package org.witchtel.seedBased_base_finder.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.witchtel.seedBased_base_finder.client.commands.GetSeedCommand;
import org.witchtel.seedBased_base_finder.client.commands.SetSeedCommand;

public class SeedBased_base_finderClient implements ClientModInitializer {

    private static final Logger log = LoggerFactory.getLogger(SeedBased_base_finderClient.class);
    public static String seed;
    private  boolean isOnServer;
    public static SeedStorage config;

    @Override
    public void onInitializeClient() {
        config = new SeedStorage();
        ClientPlayConnectionEvents.JOIN.register((phase, listener, client)-> {
                if(client.getCurrentServerEntry() != null){
                    isOnServer = true;
                    String serverAddress = client.getCurrentServerEntry().address;
                    if (config.getSeed(serverAddress) == null){
                        client.player.sendMessage(Text.literal("Enter server seed using /setseed"), false);
                    } else {
                        seed = config.getSeed(serverAddress);
                        client.player.sendMessage(Text.literal("Found stored server seed: %s".formatted(seed)), false);
                        log.info("Seed is: {}", seed);
                    }
                    log.info("Player joined a server");
                }
            }
        );
        ClientPlayConnectionEvents.DISCONNECT.register((phase, client) -> {
                System.out.println("Player disconnected");
                isOnServer = false;
                seed = null;
            }
        );
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
                SetSeedCommand.register(dispatcher);
                GetSeedCommand.register(dispatcher);
                });
    }
}