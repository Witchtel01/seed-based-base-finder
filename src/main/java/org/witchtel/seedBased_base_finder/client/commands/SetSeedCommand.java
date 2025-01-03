package org.witchtel.seedBased_base_finder.client.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.text.Text;
import org.witchtel.seedBased_base_finder.client.SeedBased_base_finderClient;

import java.util.Objects;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.*;

public class SetSeedCommand{

    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {

        dispatcher.register(literal("setseed")
                .then(argument("seed", StringArgumentType.string())
                        .executes(SetSeedCommand::run)));
    }

    public static int run(CommandContext<FabricClientCommandSource> context) {
        final String value = StringArgumentType.getString(context, "seed");
        SeedBased_base_finderClient.seed = value;
        SeedBased_base_finderClient.config.setSeed(Objects.requireNonNull(context.getSource().getClient().getCurrentServerEntry()).address, value);
        context.getSource().sendFeedback(Text.literal("Set seed to: "+value));
        return 1;
    }
}
