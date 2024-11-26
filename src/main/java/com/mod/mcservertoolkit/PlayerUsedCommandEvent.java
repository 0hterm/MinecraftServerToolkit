package com.mod.mcservertoolkit;

import com.mojang.brigadier.ParseResults;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerInterface;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.io.IOException;

public class PlayerUsedCommandEvent {
    @SubscribeEvent
    public void onPlayerUseCommand(CommandEvent event) {
        ParseResults<CommandSourceStack> parsedEvent = event.getParseResults();

        /*
            To run a Minecraft command from Java, we need
            to store the server as a DedicatedServer Object.
            Objects of this class contain the method: runCommand(String).
        */
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        DedicatedServer dedicatedServer = (DedicatedServer) server;

        // Store the command used by the user from the parsed event.
        String user_command = parsedEvent.getReader().getString();
        // Store the entity who used the command from the parsed event.
        Entity entity = parsedEvent.getContext().getSource().getEntity();
        if (entity == null) {return;}

        System.out.println("------");

        // If a player uses the give command, kill the player and announce they have cheated
        if (user_command.startsWith("give")) {
            String str = entity.getName().getString() + " cheated: " + user_command;

            // Announce to server that the player has cheated
            dedicatedServer.runCommand("/tellraw @a {\"text\":\"" + str + "\"}");
            // Log in console
            System.out.println(entity.getName().getString() + " USED GIVE COMMAND: " + user_command);
            System.out.println("He has been smited.");
            entity.kill();
        }

        // If a player uses the tp command, kill the player and announce they have cheated
        if (user_command.startsWith("tp")) {
            String str = entity.getName().getString() + " cheated: " + user_command;

            // Announce to server that the player has cheated
            dedicatedServer.runCommand("/tellraw @a {\"text\":\"" + str + "\"}");
            // Log in console
            System.out.println(entity.getName().getString() + " USED TELEPORT: " + user_command);
            System.out.println("He has been smited.");
            entity.kill();
        }

    }
}
