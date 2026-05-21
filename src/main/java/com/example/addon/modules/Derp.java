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
        .description("Determines how derp rotations are applied.")
        .defaultValue(Mode.Server_Side_Only)
        .build()
    );

    public Derp() {
        super(AddonTemplate.CATEGORY, "Derp", "Random derp head movement with selectable rotation mode.");
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (mc.player == null) return;

        // Generate random yaw and pitch
        float yaw = mc.player.getYaw() + random.nextFloat() * 360f - 180f;
        float pitch = random.nextFloat() * 180f - 90f;

        switch (mode.get()) {

            // ⭐ Server-side only derp (silent)
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

            // ⭐ Client + server derp (visible locally + sent to server)
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

