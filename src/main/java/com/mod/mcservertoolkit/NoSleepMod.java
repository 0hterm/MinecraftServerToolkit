package com.mod.mcservertoolkit;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.event.entity.player.SleepingLocationCheckEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.ArrayList;
import java.util.List;

public class NoSleepMod {
    private final ArrayList<Entity> sleepingPlayers = new ArrayList<Entity>();
    private final ArrayList<String> sleepingPlayerNames = new ArrayList<String>();

    public ArrayList<Entity> getSleepingPlayers() { return this.sleepingPlayers; }
    public ArrayList<String> getSleepingPlayerNames() { return  this.sleepingPlayerNames; }

    @SubscribeEvent
    public void onPlayerSleeping(SleepingLocationCheckEvent event) {
        Entity entity = event.getEntity();

        // If sleeping entity is not a player, do nothing.
        if (!entity.getType().toString().equals("entity.minecraft.player")) { return; }

        // Only check array for current user if the array is not empty.
        if (!this.sleepingPlayers.isEmpty()) {
            // If entity is already in the sleepingPlayers list, do nothing.
            if (this.getSleepingPlayers().contains(entity)) {return;}
        }

        System.out.println("Before adding: " + this.sleepingPlayerNames);
        this.sleepingPlayers.add(entity);
        this.sleepingPlayerNames.add(entity.getName().tryCollapseToString());
        System.out.println("After adding: " + this.sleepingPlayerNames);
    }

    // This event will fire for every chat event.
    @SubscribeEvent
    public void kickAwakePlayers(ServerChatEvent event) {
        if (!event.getPlayer().getType().toString().equals("entity.minecraft.player")) { return; }
        /*
            To run a Minecraft command from Java, we need
            to store the server as a DedicatedServer Object.
            Objects of this class contain the method: runCommand(String).
        */
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        DedicatedServer dedicatedServer = (DedicatedServer) server;

        // Put all player names in a list.
        List<ServerPlayer> serverPlayerList = server.getPlayerList().getPlayers();
        List<String> playerNameList = new ArrayList<String>();
        for (ServerPlayer player : serverPlayerList) {
            String playerName = player.getName().tryCollapseToString();
            playerNameList.add(playerName);
        }

        System.out.println("--- All Player Names ---\n" + playerNameList);
        System.out.println("--- Sleeping Players ---\n" + this.sleepingPlayerNames);

        // If the chat is ".run nosleep", kick all non sleeping players.
        String user_chat = event.getRawText();
        if (user_chat.equals(".run nosleep")) {
            for (String playerName : playerNameList) {
                if (!this.getSleepingPlayerNames().contains(playerName)) {
                    String command = "kick " + playerName;
                    dedicatedServer.runCommand(command);
                }
            }
        }
    }

    @SubscribeEvent
    public void resetLists(PlayerWakeUpEvent event) {
        this.sleepingPlayers.clear();
        this.sleepingPlayerNames.clear();
    }
}
