package org.witchtel.seedBased_base_finder.client.commands;

import com.mojang.brigadier.CommandDispatcher;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.*;

import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.text.Text;
import org.witchtel.seedBased_base_finder.client.SeedBased_base_finderClient;

public class GetSeedCommand {
    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(literal("getseed")
                .executes(GetSeedCommand::run));
    }
    public static int run(CommandContext<FabricClientCommandSource> context) {
        String sd = SeedBased_base_finderClient.seed;
        if (sd != null){
            context.getSource().sendFeedback(Text.literal("Seed is: " + sd));
        } else {
            context.getSource().sendFeedback(Text.literal("Seed is not defined yet. (Set using /setseed <seed>)").withColor(11141120));
        }
        return 1;
    }
}
