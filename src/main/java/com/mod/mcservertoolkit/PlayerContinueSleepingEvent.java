package com.mod.mcservertoolkit;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.event.entity.player.SleepingLocationCheckEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.ArrayList;

public class PlayerContinueSleepingEvent {
    private ArrayList<Entity> sleepingPlayers;
    private ArrayList<String> playerNames;

    @SubscribeEvent
    public void onPlayerSleeping(SleepingLocationCheckEvent event) {
        Entity entity = event.getEntity();

        // If sleeping entity is not a player, do nothing.
        if (!entity.getType().toString().equals("entity.minecraft.player")) { return; }
        // If entity is already in the sleepingPlayers list, do nothing.
        if (sleepingPlayers.contains(entity)) {return;}

        sleepingPlayers.add(entity);
        playerNames.add(String.valueOf(entity.getName()));
    }

    @SubscribeEvent
    public void kickAwakePlayers(CommandEvent event) {
        /*
            To run a Minecraft command from Java, we need
            to store the server as a DedicatedServer Object.
            Objects of this class contain the method: runCommand(String).
        */
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        DedicatedServer dedicatedServer = (DedicatedServer) server;

        String user_command = event.getParseResults().toString();
        if (user_command.startsWith("nosleep")) {
            
        }
    }

    @SubscribeEvent
    public void resetLists(PlayerWakeUpEvent event) {
        this.sleepingPlayers.clear();
        this.playerNames.clear();
    }
}
