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

**https://discord.gg/YRm4gm7GhS**

The community mainly speaks Finnish. English is welcome too, but I'm not the best at it.

## Author
volv0w

---

## How to Add Your Own Sounds

### Party Mode (Music)
Party Mode supports 7 custom songs:

song1, song2, song3, song4, song5, song6, song7

To add your own music:

1. Put your .ogg files here:
   src/main/resources/assets/heteroaddon/sounds/

2. Name the files exactly like this:
   song1.ogg
   song2.ogg
   song3.ogg
   ...

3. Add matching entries in sounds.json:
   "song1": {
     "sounds": [
       { "name": "heteroaddon:song1", "stream": true }
     ]
   }

4. Open PartyMode.java and update the song lengths:
   private final int song1Length = <your length in ticks> + 40;

   How to calculate length:
   - 1 second = 20 ticks
   - Example: 3 minutes (180s) = 180 * 20 = 3600 ticks

   So a 3-minute song:
   song1Length = 3600 + 40;

5. After this, Party Mode will play your songs normally.

---

### Custom Hit Sound (Hit SFX)
These sounds are used by the Custom Hit Sound module:

bhit1.ogg  
phit2.ogg  

To add or replace hit sounds:

1. Place your .ogg files in the same sounds folder.
2. Add entries in sounds.json:
   "bhit1": {
     "sounds": [
       { "name": "heteroaddon:bhit1", "stream": false }
     ]
   }

stream = false is recommended because hit sounds are short.

---

No copyrighted audio is included in this project.  
Users must add their own .ogg files manually.
