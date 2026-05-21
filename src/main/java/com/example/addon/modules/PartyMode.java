package com.example.addon.modules;

import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.orbit.EventHandler;

import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.settings.DoubleSetting;
import meteordevelopment.meteorclient.settings.EnumSetting;

import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;

import com.example.addon.AddonTemplate;

public class PartyMode extends Module {
    private int timer = 0;

    public enum Song {
        Titanium,
        KingVon,
        HotNCold,
        JustDance,
        AlorsOnDanse,
        Amore
    }

    public enum Mode {
        Single,
        Playlist
    }

    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Mode> modeSetting = sgGeneral.add(new EnumSetting.Builder<Mode>()
        .name("mode")
        .description("Playback mode.")
        .defaultValue(Mode.Single)
        .build()
    );

    private final Setting<Song> songSetting = sgGeneral.add(new EnumSetting.Builder<Song>()
        .name("song")
        .description("Which song to play.")
        .defaultValue(Song.Titanium)
        .build()
    );

    private final Setting<Double> volumeSetting = sgGeneral.add(new DoubleSetting.Builder()
        .name("volume")
        .description("Playback volume.")
        .defaultValue(1.0)
        .min(0.0)
        .max(2.0)
        .sliderRange(0.0, 2.0)
        .build()
    );

    // Song lengths in ticks (+40 tick safety buffer)
    private final int titaniumLength = 2040 + 40;
    private final int kingVonLength = 3400 + 40;
    private final int hotNColdLength = 4360 + 40;
    private final int justDanceLength = 5040 + 40;
    private final int alorsOnDanseLength = 4100 + 40;
    private final int amoreLength = 3640 + 40;

    // Playlist order
    private final Song[] playlist = {
        Song.Titanium,
        Song.KingVon,
        Song.HotNCold,
        Song.JustDance,
        Song.AlorsOnDanse,
        Song.Amore
    };

    private int playlistIndex = 0;

    public PartyMode() {
        super(AddonTemplate.CATEGORY, "party-mode", "plays music : ).");
    }

    @Override
    public void onActivate() {
        timer = 0;

        if (modeSetting.get() == Mode.Playlist) {
            playlistIndex = 0;
            songSetting.set(playlist[0]);
        }

        playSong();
    }

    @Override
    public void onDeactivate() {
        if (mc.player != null) {
            mc.getSoundManager().stopAll();
        }
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        if (mc.player == null) return;

        timer++;

        int len = switch (songSetting.get()) {
            case Titanium -> titaniumLength;
            case KingVon -> kingVonLength;
            case HotNCold -> hotNColdLength;
            case JustDance -> justDanceLength;
            case AlorsOnDanse -> alorsOnDanseLength;
            case Amore -> amoreLength;
        };

        if (timer >= len) {
            timer = 0;

            if (modeSetting.get() == Mode.Playlist) {
                playlistIndex++;

                if (playlistIndex >= playlist.length) {
                    playlistIndex = 0;
                }

                songSetting.set(playlist[playlistIndex]);
            }

            playSong();
        }
    }

    private void playSong() {
        if (mc.player == null || mc.world == null) return;

        String idPath = switch (songSetting.get()) {
            case Titanium -> "titanium";
            case KingVon -> "king_von";
            case HotNCold -> "hot_n_cold";
            case JustDance -> "just_dance";
            case AlorsOnDanse -> "alors_on_danse";
            case Amore -> "amore";
        };

        SoundEvent sound = SoundEvent.of(Identifier.of("heteroaddon", idPath));

        mc.world.playSound(
            mc.player,
            mc.player.getBlockPos(),
            sound,
            SoundCategory.RECORDS,
            volumeSetting.get().floatValue(),
            1f
        );
    }
}
