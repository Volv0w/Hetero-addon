# Hetero Addon

A Meteor Client addon for Minecraft 1.21.11.

## Features
- AutoMLG (Makes perfect water bucket mlg)
- Party Mode (plays music)
- InvWalk (allows you to walk while inventory is open)
- NoBackground (removes GUI background)
- Custom Hit Sound (plays a custom sound when you hit someone)
- PopBob (gives a random item to you)
- PopBob Aura (gives random items to everyone)
- Derp (moves your head everywhere)
- More coming soon...

## Installation
1. Install Meteor Client (required)  
2. Download the latest release from the Releases page  
   - OR build the addon yourself with `./gradlew build`  
3. If you built it yourself, find the JAR in `build/libs`  
4. Put the JAR into `.minecraft/mods`

## Disclaimers
- This project is fully AI‑coded and not meant to be taken too seriously.  
- I update this project occasionally, whenever I feel like it.

## Contact
For questions, feature suggestions, discussions, or finding people to play with,  
feel free to join my Discord server:

https://discord.gg/YRm4gm7GhS

The community mainly speaks Finnish. English is welcome too.

---

# How to Add Music to PartyMode

**IMPORTANT:**  
If you want to hear music in PartyMode, you must add the songs yourself.  
Instructions for adding your own `.ogg` files are below.

Party Mode supports **7 songs by default**, but you can add **more than 7** if you want.

Default song slots:

song1, song2, song3, song4, song5, song6, song7

To add your own music:

1. Put your `.ogg` files here:  
   `src/main/resources/assets/heteroaddon/sounds/`

2. File names can be ANYTHING you want, as long as:  
   - all lowercase  
   - no spaces  
   - underscores allowed (`my_song.ogg`)  
   - the name matches everywhere (file name, sounds.json, PartyMode.java)

3. Add matching entries in `sounds.json`:
```
"song1": {
  "sounds": [
    { "name": "heteroaddon:song1", "stream": true }
  ]
}
```

4. Update the song lengths in `PartyMode.java`:
```
private final int song1Length = <your length in ticks> + 40;
```

Tick conversion:  
- 1 second = 20 ticks  
- Example: 3 minutes (180s) = 3600 ticks

### Adding MORE than 7 songs

You can add as many songs as you want:

- Add new enum values (Song8, Song9, Song10…)  
- Add new length variables (song8Length, song9Length…)  
- Add them to the playlist array  
- Add matching entries in `sounds.json`  
- Add the `.ogg` files to the sounds folder  

---

# Custom Hit Sound (Hit SFX)

The addon already includes **two custom hit sounds**:

- `bhit1.ogg`  
- `phit2.ogg`

You can add more hit sounds if you want — but you’ll have to figure out how to implement them yourself.

To replace or modify the existing ones:

1. Place your `.ogg` files in the same sounds folder.  
2. Add entries in `sounds.json`:
```
"bhit1": {
  "sounds": [
    { "name": "heteroaddon:bhit1", "stream": false }
  ]
}
```

`stream = false` is recommended because hit sounds are short.

---

No copyrighted audio is included in this project.  
Users must add their own `.ogg` files manually.

## Author
volv0w

---

if anything was unclear you can blame copilot or me for not writing this my self!
