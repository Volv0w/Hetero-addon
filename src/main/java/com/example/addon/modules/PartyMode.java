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

    // These are the song slots users can replace with their own tracks.
    // Each one corresponds to an .ogg file in:
    // src/main/resources/assets/heteroaddon/sounds/
    //
    // Example:
    // Song1 -> song1.ogg
    // Song2 -> song2.ogg
    public enum Song {
        Song1,
        Song2,
        Song3,
        Song4,
        Song5,
        Song6,
        Song7
    }

    public enum Mode {
        Single,   // Plays only the selected song
        Playlist  // Automatically cycles through all songs
    }

    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Mode> modeSetting = sgGeneral.add(new EnumSetting.Builder<Mode>()
        .name("mode")
        .description("Select playback mode (single or playlist).")
        .defaultValue(Mode.Single)
        .build()
    );

    private final Setting<Song> songSetting = sgGeneral.add(new EnumSetting.Builder<Song>()
        .name("song")
        .description("Select which song to play. Make sure the .ogg file exists!")
        .defaultValue(Song.Song1)
        .build()
    );

    private final Setting<Double> volumeSetting = sgGeneral.add(new DoubleSetting.Builder()
        .name("volume")
        .description("Playback volume for the music.")
        .defaultValue(1.0)
        .min(0.0)
        .max(2.0)
        .sliderRange(0.0, 2.0)
        .build()
    );

    // Song lengths in ticks (+40 tick safety buffer)
    // IMPORTANT:
    // Users should adjust these values to match the length of their own .ogg files.
    private final int song1Length = 2040 + 40;
    private final int song2Length = 3400 + 40;
    private final int song3Length = 4360 + 40;
    private final int song4Length = 5040 + 40;
    private final int song5Length = 4100 + 40;
    private final int song6Length = 3640 + 40;
    private final int song7Length = 4000 + 40;

    // Playlist order – users can reorder or remove songs here.
    private final Song[] playlist = {
        Song.Song1,
        Song.Song2,
        Song.Song3,
        Song.Song4,
        Song.Song5,
        Song.Song6,
        Song.Song7
    };

    private int playlistIndex = 0;

    public PartyMode() {
        super(AddonTemplate.CATEGORY, "party-mode", "Plays custom music tracks in single or playlist mode.");
    }

    @Override
    public void onActivate() {
        timer = 0;

        // When playlist mode is enabled, always start from the first song.
        if (modeSetting.get() == Mode.Playlist) {
            playlistIndex = 0;
            songSetting.set(playlist[0]);
        }

        playSong();
    }

    @Override
    public void onDeactivate() {
        // Stop all currently playing sounds when the module is turned off.
        if (mc.player != null) {
            mc.getSoundManager().stopAll();
        }
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        if (mc.player == null) return;

        timer++;

        // Select the correct song length based on the chosen song.
        // Users should update these lengths to match their own audio files.
        int len = switch (songSetting.get()) {
            case Song1 -> song1Length;
            case Song2 -> song2Length;
            case Song3 -> song3Length;
            case Song4 -> song4Length;
            case Song5 -> song5Length;
            case Song6 -> song6Length;
            case Song7 -> song7Length;
        };

        // When the song finishes, move to the next one (if playlist mode is enabled)
        if (timer >= len) {
            timer = 0;

            if (modeSetting.get() == Mode.Playlist) {
                playlistIndex++;

                // Loop back to the start of the playlist
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

        // Convert enum to lowercase sound file name.
        // Song1 -> "song1"
        // Song2 -> "song2"
        // Make sure your sounds.json contains matching entries!
        String idPath = switch (songSetting.get()) {
            case Song1 -> "song1";
            case Song2 -> "song2";
            case Song3 -> "song3";
            case Song4 -> "song4";
            case Song5 -> "song5";
            case Song6 -> "song6";
            case Song7 -> "song7";
        };

        // Create the SoundEvent for the selected song.
        // The .ogg file must be located at:
        // assets/heteroaddon/sounds/<idPath>.ogg
        SoundEvent sound = SoundEvent.of(Identifier.of("heteroaddon", idPath));

        // Play the sound at the player's position.
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
