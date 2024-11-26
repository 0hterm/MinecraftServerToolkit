package com.mod.mcservertoolkit;

import com.mojang.brigadier.ParseResults;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerInterface;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.io.IOException;

public class PlayerUsedCommandEvent {
    @SubscribeEvent
    public void onPlayerUseCommand(CommandEvent event) throws IOException {
        ParseResults<CommandSourceStack> results = event.getParseResults();

        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        DedicatedServer dedicatedServer = (DedicatedServer) server;

        String user_command = results.getReader().getString();
        Entity entity = results.getContext().getSource().getEntity();
        if (entity == null) {return;}

        System.out.println("------");

        if (user_command.startsWith("give")) {
            String str = entity.getName().getString() + " cheated: " + user_command;
            dedicatedServer.runCommand("/tellraw @a {\"text\":\"" + str + "\"}");
            System.out.println(entity.getName().getString() + " USED GIVE COMMAND: " + user_command);
            System.out.println("He has been smited.");
            entity.kill();
        }

        if (user_command.startsWith("tp")) {
            String str = entity.getName().getString() + " cheated: " + user_command;
            dedicatedServer.runCommand("/tellraw @a {\"text\":\"" + str + "\"}");
            System.out.println(entity.getName().getString() + " USED TELEPORT: " + user_command);
            System.out.println("He has been smited.");
            entity.kill();
        }

    }
}
