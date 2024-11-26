package com.mod.mcservertoolkit;

import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import java.util.Date;

public class PlayerJoinEvent {
    @SubscribeEvent
    public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        Entity entity = event.getEntity();
        String type = entity.getType().toString();
        if (type.equals("entity.minecraft.player")) {
            // If we reach here, the entity is a player
            System.out.println("------\n" + new Date().toString().substring(0,23) + "\n" + entity.getName().getString() + " has joined the server!");
        }
    }
}
