package com.example.addon.mixin;

import com.example.addon.modules.InvWalk;
import meteordevelopment.meteorclient.systems.modules.Modules;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.Window;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class InvWalkMixin {
    @Inject(method = "tickMovement", at = @At("HEAD"))
    private void onTickMovement(CallbackInfo ci) {
        MinecraftClient mc = MinecraftClient.getInstance();

        if (!Modules.get().isActive(InvWalk.class)) return;

        if (mc.currentScreen instanceof HandledScreen) {
            GameOptions o = mc.options;
            Window w = mc.getWindow();

            o.forwardKey.setPressed(InputUtil.isKeyPressed(w, o.forwardKey.getDefaultKey().getCode()));
            o.backKey.setPressed(InputUtil.isKeyPressed(w, o.backKey.getDefaultKey().getCode()));
            o.leftKey.setPressed(InputUtil.isKeyPressed(w, o.leftKey.getDefaultKey().getCode()));
            o.rightKey.setPressed(InputUtil.isKeyPressed(w, o.rightKey.getDefaultKey().getCode()));
            o.jumpKey.setPressed(InputUtil.isKeyPressed(w, o.jumpKey.getDefaultKey().getCode()));
            o.sprintKey.setPressed(InputUtil.isKeyPressed(w, o.sprintKey.getDefaultKey().getCode()));
        }
    }
}
