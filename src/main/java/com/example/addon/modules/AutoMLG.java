package com.example.addon.modules;

import com.example.addon.AddonTemplate;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import meteordevelopment.meteorclient.utils.player.Rotations;
import meteordevelopment.meteorclient.utils.player.InvUtils;

import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;

public class AutoMLG extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Boolean> autoPickup = sgGeneral.add(new BoolSetting.Builder()
        .name("auto-pickup-water")
        .description("Instantly picks water back up after landing using precise silent rotation! <-- cool... right?")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> autoSelect = sgGeneral.add(new BoolSetting.Builder()
        .name("auto-select-bucket")
        .description("Automatically selects a water bucket from your hotbar when you start falling! <-- isn't that shi cool?")
        .defaultValue(true)
        .build()
    );

    private boolean placedWater = false;
    private BlockPos lastWaterPos = null;

    public AutoMLG() {
        super(AddonTemplate.CATEGORY, "auto-mlg", "just does perfect water bucket mlg, just for you <3");
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (mc.player == null || mc.world == null) return;
        if (!this.isActive()) return;

        // ⭐ Auto-select bucket
        if (autoSelect.get()) {
            if (mc.player.getVelocity().y < 0 && mc.player.fallDistance > 3) {
                for (int i = 0; i < 9; i++) {
                    if (mc.player.getInventory().getStack(i).getItem() == Items.WATER_BUCKET) {
                        InvUtils.swap(i, true);
                        break;
                    }
                }
            }
        }

        // ⭐ INSTANT AUTO-PICKUP
        // Kun fallDistance nollautuu → MLG onnistui → ota vesi HETI
        if (autoPickup.get() && placedWater && lastWaterPos != null && mc.player.fallDistance == 0) {

            if (mc.player.getMainHandStack().getItem() == Items.BUCKET) {

                // Vesiblokin keskikohta
                Vec3d target = new Vec3d(
                    lastWaterPos.getX() + 0.5,
                    lastWaterPos.getY() + 0.5,
                    lastWaterPos.getZ() + 0.5
                );

                // Pelaajan silmien sijainti (1.21.11 yhteensopiva)
                Vec3d eyes = new Vec3d(
                    mc.player.getX(),
                    mc.player.getY() + mc.player.getEyeHeight(mc.player.getPose()),
                    mc.player.getZ()
                );

                Vec3d diff = target.subtract(eyes);
                double distXZ = Math.sqrt(diff.x * diff.x + diff.z * diff.z);

                float yaw = (float) (Math.toDegrees(Math.atan2(diff.z, diff.x)) - 90f);
                float pitch = (float) -Math.toDegrees(Math.atan2(diff.y, distXZ));

                // ⭐ Silent rotation suoraan vesiblokkiin
                Rotations.rotate(yaw, pitch, () -> {
                    mc.interactionManager.interactItem(mc.player, Hand.MAIN_HAND);
                });
            }

            placedWater = false;
            lastWaterPos = null;
            return;
        }

        // ⭐ Ei ämpäriä kädessä → ei MLG
        if (mc.player.getMainHandStack().getItem() != Items.WATER_BUCKET) return;

        // ⭐ Putoatko tarpeeksi korkealta?
        if (mc.player.fallDistance < 5) return;

        // ⭐ Raycast alas (getPos FIXED)
        Vec3d start = new Vec3d(mc.player.getX(), mc.player.getY(), mc.player.getZ());
        Vec3d end   = start.add(0, -7, 0);

        BlockHitResult hit = mc.world.raycast(new RaycastContext(
            start,
            end,
            RaycastContext.ShapeType.COLLIDER,
            RaycastContext.FluidHandling.NONE,
            mc.player
        ));

        if (hit.getType() != HitResult.Type.BLOCK) return;

        double distance = mc.player.getY() - hit.getPos().y;

        if (distance > 7) return;

        // ⭐ Tallennetaan vesiblokin paikka pickupia varten
        lastWaterPos = hit.getBlockPos().down();

        // ⭐ Silent rotation + MLG
        Rotations.rotate(mc.player.getYaw(), 90f, () -> {
            mc.interactionManager.interactItem(mc.player, Hand.MAIN_HAND);
            placedWater = true;
        });
    }
}
