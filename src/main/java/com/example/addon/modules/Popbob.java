package com.example.addon.modules;

import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Category;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.orbit.EventHandler;
import com.example.addon.AddonTemplate;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;

import java.util.Random;

public class Popbob extends Module {
    private final Random random = new Random();

    public Popbob() {
        super(AddonTemplate.CATEGORY, "popbob", "Gives random items if you have OP.");
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        if (mc.player == null) return;

        // Give 100 random items per tick
        for (int i = 0; i < 50; i++) {
            Item randomItem = Registries.ITEM.get(random.nextInt(Registries.ITEM.size()));
            String id = Registries.ITEM.getId(randomItem).toString();
            mc.player.networkHandler.sendChatCommand("give @s " + id);
        }
    }
}
