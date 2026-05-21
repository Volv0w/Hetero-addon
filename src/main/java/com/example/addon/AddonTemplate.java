package com.example.addon;

import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.addons.GithubRepo;

import meteordevelopment.meteorclient.systems.modules.Category;
import meteordevelopment.meteorclient.systems.modules.Modules;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minecraft.item.Items;
import net.minecraft.item.ItemStack;

import com.example.addon.modules.Popbob;
import com.example.addon.modules.PopbobAura;
import com.example.addon.modules.PartyMode;
import com.example.addon.modules.CustomHitSound;
import com.example.addon.modules.Derp;
import com.example.addon.modules.InvWalk;
import com.example.addon.modules.Nobackground;
import com.example.addon.modules.AutoMLG;

public class AddonTemplate extends MeteorAddon {
    public static final Logger LOG = LoggerFactory.getLogger("HeteroAddon");

    public static final Category CATEGORY =
        new Category("Hetero Addon", new ItemStack(Items.TNT));

    @Override
    public void onInitialize() {
        LOG.info("Hetero Addon loaded!");


        // Moduuli rekisteröinti
        Modules.get().add(new Popbob());
        Modules.get().add(new PopbobAura());
        Modules.get().add(new PartyMode());
        Modules.get().add(new CustomHitSound());
        Modules.get().add(new Derp());
        Modules.get().add(new InvWalk());
        Modules.get().add(new Nobackground());
        Modules.get().add(new AutoMLG());

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
