package com.example.addon.modules;

import meteordevelopment.meteorclient.systems.modules.Module;
import com.example.addon.AddonTemplate;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.orbit.EventHandler;

public class InvWalk extends Module {
    public InvWalk() {
        super(AddonTemplate.CATEGORY, "inv-walk", "Walk while using inventory, chest, crafting table, furnace, etc.");
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (mc.player == null || mc.world == null) return;

        // Toimii vain container-GUI:ssa
        if (mc.currentScreen instanceof HandledScreen) {
            mc.options.forwardKey.setPressed(mc.options.forwardKey.isPressed());
            mc.options.backKey.setPressed(mc.options.backKey.isPressed());
            mc.options.leftKey.setPressed(mc.options.leftKey.isPressed());
            mc.options.rightKey.setPressed(mc.options.rightKey.isPressed());
            mc.options.jumpKey.setPressed(mc.options.jumpKey.isPressed());
            mc.options.sprintKey.setPressed(mc.options.sprintKey.isPressed());
        }
    }
}
