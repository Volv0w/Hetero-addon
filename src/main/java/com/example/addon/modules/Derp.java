package com.example.addon.modules;

import com.example.addon.AddonTemplate;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.EnumSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

import java.util.Random;

public class Derp extends Module {
    private final Random random = new Random();
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    public enum Mode {
        Server_Side_Only,
        Client_And_Server
    }

    private final Setting<Mode> mode = sgGeneral.add(new EnumSetting.Builder<Mode>()
        .name("mode")
        .description("How derp rotations are applied.")
        .defaultValue(Mode.Server_Side_Only)
        .build()
    );

    public Derp() {
        super(AddonTemplate.CATEGORY, "Derp", "Derp head movement with mode selection.");
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (mc.player == null) return;

        float yaw = mc.player.getYaw() + random.nextFloat() * 360f - 180f;
        float pitch = random.nextFloat() * 180f - 90f;

        switch (mode.get()) {
            case Server_Side_Only -> {
                mc.player.networkHandler.sendPacket(
                    new PlayerMoveC2SPacket.LookAndOnGround(
                        yaw,
                        pitch,
                        mc.player.isOnGround(),
                        false
                    )
                );
            }

            case Client_And_Server -> {
                mc.player.setYaw(yaw);
                mc.player.setPitch(pitch);

                mc.player.networkHandler.sendPacket(
                    new PlayerMoveC2SPacket.LookAndOnGround(
                        yaw,
                        pitch,
                        mc.player.isOnGround(),
                        false
                    )
                );
            }
        }
    }
}

