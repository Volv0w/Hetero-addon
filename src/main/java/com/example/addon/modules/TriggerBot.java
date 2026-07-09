package com.example.addon.modules;

import com.example.addon.AddonTemplate;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.EnumSetting;
import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.orbit.EventHandler;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;

public class TriggerBot extends Module {
    public enum TargetMode {
        Players,
        Hostiles,
        Passives,
        PlayersAndHostiles,
        Everything
    }

    public enum ClickMode {
        Always,
        OnlyOnClick
    }

    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<TargetMode> targetMode = sgGeneral.add(new EnumSetting.Builder<TargetMode>()
        .name("target-mode")
        .description("What entities TriggerBot is allowed to hit.")
        .defaultValue(TargetMode.Players)
        .build()
    );

    private final Setting<ClickMode> clickMode = sgGeneral.add(new EnumSetting.Builder<ClickMode>()
        .name("click-mode")
        .description("When TriggerBot is allowed to attack.")
        .defaultValue(ClickMode.Always)
        .build()
    );

    public TriggerBot() {
        super(AddonTemplate.CATEGORY, "trigger-bot", "Automatically attacks entities under your crosshair.");
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (mc.player == null || mc.world == null || mc.interactionManager == null) return;

        // Only attack when left click is held (if enabled)
        if (clickMode.get() == ClickMode.OnlyOnClick && !mc.options.attackKey.isPressed()) {
            return;
        }

        if (!(mc.crosshairTarget instanceof EntityHitResult hit)) return;

        Entity target = hit.getEntity();
        if (!(target instanceof LivingEntity)) return;

        if (!isValidTarget(target)) return;

        if (mc.player.getAttackCooldownProgress(0) < 1.0f) return;

        mc.interactionManager.attackEntity(mc.player, target);
        mc.player.swingHand(Hand.MAIN_HAND);
    }

    private boolean isValidTarget(Entity e) {
        return switch (targetMode.get()) {
            case Players -> e.getType().getName().getString().equalsIgnoreCase("player");
            case Hostiles -> e instanceof HostileEntity;
            case Passives -> e instanceof PassiveEntity;
            case PlayersAndHostiles -> e instanceof HostileEntity || e.getType().getName().getString().equalsIgnoreCase("player");
            case Everything -> true;
        };
    }
}

