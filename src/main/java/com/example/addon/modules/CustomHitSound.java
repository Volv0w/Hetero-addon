package com.example.addon.modules;

import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.events.entity.player.AttackEntityEvent;
import meteordevelopment.orbit.EventHandler;

import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.settings.DoubleSetting;
import meteordevelopment.meteorclient.settings.EnumSetting;

import net.minecraft.entity.LivingEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;

import com.example.addon.AddonTemplate;

public class CustomHitSound extends Module {
    public enum SoundType {
        BHit1,
        PHit2
    }

    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<SoundType> soundSetting = sgGeneral.add(new EnumSetting.Builder<SoundType>()
        .name("sound")
        .description("Which custom hit sound to use.")
        .defaultValue(SoundType.BHit1)
        .build()
    );

    private final Setting<Double> volumeSetting = sgGeneral.add(new DoubleSetting.Builder()
        .name("volume")
        .description("Hit sound volume.")
        .defaultValue(1.0)
        .min(0.0)
        .max(2.0)
        .sliderRange(0.0, 2.0)
        .build()
    );

    public CustomHitSound() {
        super(AddonTemplate.CATEGORY, "custom-hit-sound", "Custom hit sound.");
    }

    private String getSelectedSound() {
        return switch (soundSetting.get()) {
            case BHit1 -> "bhit1";
            case PHit2 -> "phit2";
        };
    }

    private float randomPitch() {
        return 0.9f + (float) Math.random() * 0.2f;
    }

    private void playCustomSound() {
        if (mc.world == null || mc.player == null) return;

        SoundEvent sound = SoundEvent.of(Identifier.of("heteroaddon", getSelectedSound()));

        mc.world.playSound(
            mc.player,
            mc.player.getBlockPos(),
            sound,
            SoundCategory.PLAYERS,
            volumeSetting.get().floatValue(),
            randomPitch()
        );
    }

    @EventHandler
    private void onAttack(AttackEntityEvent event) {
        if (event.entity instanceof LivingEntity) {
            playCustomSound();
        }
    }
}


