package com.example.addon.modules;

import com.example.addon.AddonTemplate;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.settings.IntSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.events.render.GetFovEvent;
import meteordevelopment.orbit.EventHandler;

public class UltimateFovChanger extends Module {
    // Setting group for the module
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    // Custom FOV slider (10–300)
    private final Setting<Integer> fov = sgGeneral.add(new IntSetting.Builder()
        .name("custom-fov")
        .description("Overrides the game's FOV with any value you choose.")
        .defaultValue(90)
        .min(5)
        .max(360)          // You can increase this if you want higher FOV
        .sliderRange(5, 360)
        .build()
    );

    public UltimateFovChanger() {
        super(AddonTemplate.CATEGORY, "ultimate-fov-changer", "ULTIMATE FOV CHANGER : ).");
    }

    // This event fires every frame when the game calculates FOV.
    // We override the FOV here so Minecraft cannot clamp it.
    @EventHandler
    private void onFov(GetFovEvent event) {
        event.fov = fov.get();
    }
}
