package com.example.addon.mixin;

import com.example.addon.modules.Nobackground;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net.minecraft.client.gui.screen.Screen")
public class ScreenMixin {
    @Inject(method = "renderBackground", at = @At("HEAD"), cancellable = true)
    private void onRenderBackground(CallbackInfo ci) {
        // Cancel background rendering only if the module is enabled
        if (Nobackground.INSTANCE != null && Nobackground.INSTANCE.isActive()) {
            ci.cancel();
        }
    }
}
