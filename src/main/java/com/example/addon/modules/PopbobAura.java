package com.example.addon.modules;

import com.example.addon.AddonTemplate;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;

import java.util.Random;

public class PopbobAura extends Module {
    private final Random random = new Random();

    public PopbobAura() {
        super(AddonTemplate.CATEGORY, "popbob-aura", "Gives random items to everyone if you have OP.");
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        if (mc.player == null) return;

        // Give 50 random items per tick to all players
        for (int i = 0; i < 50; i++) {
            Item randomItem = Registries.ITEM.get(random.nextInt(Registries.ITEM.size()));
            String id = Registries.ITEM.getId(randomItem).toString();
            mc.player.networkHandler.sendChatCommand("give @a " + id);
        }
    }
}
