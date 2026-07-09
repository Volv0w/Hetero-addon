package com.example.addon.modules;

import com.example.addon.AddonTemplate;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.events.entity.player.AttackEntityEvent;
import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.EnumSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.orbit.EventHandler;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.*;

public class PetProtect extends Module {
    public enum PetOwnerMode {
        OwnPetsOnly,
        AllPets
    }

    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<PetOwnerMode> ownerMode = sgGeneral.add(new EnumSetting.Builder<PetOwnerMode>()
        .name("owner-mode")
        .description("Which pets cannot be attacked.")
        .defaultValue(PetOwnerMode.OwnPetsOnly)
        .build()
    );

    // Multi-select pet types
    private final Setting<Boolean> protectWolves = sgGeneral.add(new BoolSetting.Builder()
        .name("protect-wolves")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> protectCats = sgGeneral.add(new BoolSetting.Builder()
        .name("protect-cats")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> protectParrots = sgGeneral.add(new BoolSetting.Builder()
        .name("protect-parrots")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> protectHorses = sgGeneral.add(new BoolSetting.Builder()
        .name("protect-horses")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> protectDonkeys = sgGeneral.add(new BoolSetting.Builder()
        .name("protect-donkeys")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> protectLlamas = sgGeneral.add(new BoolSetting.Builder()
        .name("protect-llamas")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> protectCamels = sgGeneral.add(new BoolSetting.Builder()
        .name("protect-camels")
        .defaultValue(true)
        .build()
    );

    public PetProtect() {
        super(AddonTemplate.CATEGORY, "pet-protect", "Prevents you from hitting pets <3.");
    }

    @EventHandler
    private void onAttack(AttackEntityEvent event) {
        if (mc.player == null || mc.world == null) return;

        Entity target = event.entity;

        // Check if target is a protected pet type
        if (!isProtectedPet(target)) return;

        // If only own pets are protected, check ownership
        if (ownerMode.get() == PetOwnerMode.OwnPetsOnly) {
            if (target instanceof TameableEntity tameable) {
                if (!tameable.isOwner(mc.player)) return; // not your pet → allow hit
            }
        }

        // Cancel attack
        event.cancel();
    }

    private boolean isProtectedPet(Entity e) {
        if (protectWolves.get() && e instanceof WolfEntity) return true;
        if (protectCats.get() && e instanceof CatEntity) return true;
        if (protectParrots.get() && e instanceof ParrotEntity) return true;
        if (protectHorses.get() && e instanceof HorseEntity) return true;
        if (protectDonkeys.get() && e instanceof DonkeyEntity) return true;
        if (protectLlamas.get() && e instanceof LlamaEntity) return true;
        if (protectCamels.get() && e instanceof CamelEntity) return true;

        return false;
    }
}
