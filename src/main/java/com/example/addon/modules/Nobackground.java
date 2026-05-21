package com.example.addon.modules;

import meteordevelopment.meteorclient.systems.modules.Module;
import com.example.addon.AddonTemplate;

public class Nobackground extends Module {
    public static Nobackground INSTANCE;

    public Nobackground() {
        super(AddonTemplate.CATEGORY, "NoBackground", "Removes GUI background.");
        INSTANCE = this;
    }
}
