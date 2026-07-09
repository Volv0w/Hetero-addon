package com.example.addon.modules;

import com.example.addon.AddonTemplate;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Category;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.orbit.EventHandler;
import meteordevelopment.meteorclient.settings.IntSetting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import net.minecraft.client.MinecraftClient;

public class JumpReset extends Module {
    private final SettingGroup sgGeneral = settings.createGroup("General");

    private final IntSetting chance = sgGeneral.add(new IntSetting.Builder()
        .name("chance")
        .description("Probability of performing a jump reset.")
        .defaultValue(100)
        .min(0)
        .max(100)
        .build()
    );

    public JumpReset() {
        super(AddonTemplate.CATEGORY, "jump-reset", "reduces knockback.");
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null) return;

        if (mc.player.hurtTime > 0 && mc.player.isOnGround()) {
            int roll = (int) (Math.random() * 100);
            if (roll <= chance.get()) {
                mc.player.jump();
            }
        }
    }
}

