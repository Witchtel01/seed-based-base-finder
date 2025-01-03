package org.witchtel.seedBased_base_finder.client;

import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.text.Text;

import java.util.Objects;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.*;

public class SeedBased_base_finderClient implements ClientModInitializer {

    private String seed;
    private  boolean isOnServer;
    private SeedConfig config;
    @Override
    public void onInitializeClient() {
        config = new SeedConfig();
        ClientPlayConnectionEvents.JOIN.register((phase, listener, client)-> {
                if(client.getCurrentServerEntry() != null){
                    isOnServer = true;
                    String serverAddress = client.getCurrentServerEntry().address;
                    if (config.getSeed(serverAddress) == null){
                        client.player.sendMessage(Text.literal("Enter server seed using /setseed"), false);
                    } else {
                        seed = config.getSeed(serverAddress);
                        System.out.println("Seed is: " + seed);
                    }
                    System.out.println("Player joined a server");
                }
            }
        );
        ClientPlayConnectionEvents.DISCONNECT.register((phase, client) -> {
                System.out.println("Player disconnected");
                isOnServer = false;
            }
        );
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) ->
                dispatcher.register(literal("setseed")
                        .then(argument("seed", StringArgumentType.string())
                                .executes(context -> {
                                    final String value = StringArgumentType.getString(context, "seed");
                                    seed = value;
                                    config.setSeed(Objects.requireNonNull(context.getSource().getClient().getCurrentServerEntry()).address, value);
                                    context.getSource().sendFeedback(Text.literal("Set seed to %s".formatted(value)));
                                    return 1;
                                }))));
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) ->
                dispatcher.register(literal("getseed")
                        .executes(context -> {
                            context.getSource().sendFeedback(Text.literal("Seed is %s".formatted(seed)));
                            return 1;
                        })));
    }
}