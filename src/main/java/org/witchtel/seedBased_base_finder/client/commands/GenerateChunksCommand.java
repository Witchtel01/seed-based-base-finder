package org.witchtel.seedBased_base_finder.client.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import org.witchtel.seedBased_base_finder.client.ChunkGeneratorUtils;

import java.util.Objects;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.*;

public class GenerateChunksCommand {
    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(literal("genworld")
                .executes( GenerateChunksCommand::run));
    }
    public static int run(CommandContext<FabricClientCommandSource> context) {
        ChunkGeneratorUtils utils = new ChunkGeneratorUtils();
        utils.createTempWorld(Objects.requireNonNull(context.getSource().getWorld().getServer()));

        return 1;
    }
}
