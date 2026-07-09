package com.example.addon;

import com.example.addon.modules.*;
import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.addons.GithubRepo;

import meteordevelopment.meteorclient.systems.modules.Category;
import meteordevelopment.meteorclient.systems.modules.Modules;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minecraft.item.Items;
import net.minecraft.item.ItemStack;

public class AddonTemplate extends MeteorAddon {
    public static final Logger LOG = LoggerFactory.getLogger("HeteroAddon");

    public static final Category CATEGORY =
        new Category("Hetero Addon", new ItemStack(Items.TNT));

    @Override
    public void onInitialize() {
        LOG.info("Hetero Addon loaded!");

        // Register all modules
        Modules.get().add(new Popbob());
        Modules.get().add(new PopbobAura());
        Modules.get().add(new PartyMode());
        Modules.get().add(new CustomHitSound());
        Modules.get().add(new Derp());
        Modules.get().add(new InvWalk());
        Modules.get().add(new Nobackground());
        Modules.get().add(new AutoMLG());
        Modules.get().add(new TriggerBot());
        Modules.get().add(new PetProtect());
        Modules.get().add(new UltimateFovChanger());
        Modules.get().add(new JumpReset());


    }

    @Override
    public void onRegisterCategories() {
        Modules.registerCategory(CATEGORY);
    }

    @Override
    public String getPackage() {
        return "com.example.addon";
    }

    @Override
    public GithubRepo getRepo() {
        return new GithubRepo("YourNameHere", "HeteroAddon");
    }
}

