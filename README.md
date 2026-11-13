![Soul Catcher Plus Compat](art/branding/logo-banner-compat-v2.png)

# Soul Catcher Plus Compat

Version **1.2.3** adds owned Ice and Fire Siren appearance/charm corrections for [Soul Catcher Plus](https://github.com/1PixelDemon1/soul-catcher-plus), alongside existing dragon/terrain, Twilight summon, Mowzie ally, and Aquamirae music integrations. Install creature mods/libraries separately; behavior depends on native APIs and versions.

| Minecraft | Forge target/minimum | Game Java | Required core version |
|---|---|---|---|
| 1.16.5 | 36.2.42 | 8 | `1.2.3+1.16.5` |
| 1.19.2 | 43.5.0 | 17 | `1.2.3+1.19.2` |
| 1.20.1 | 47.4.28 | 17 | `1.2.3+1.20.1` |
| 26.3 | 66.1.0 | 25 | `1.2.3+26.3` |

Install matching core/addon JARs on both client and server. The addon checks its exact core version and bundles neither core nor third-party code/assets. Older released artifacts retain their original requirements.

## Integrations

| Minecraft | Ice and Fire / Citadel | Twilight Forest |
|---|---|---|
| 1.16.5 | 2.1.12 patch-1 / 1.8.1 | 4.0.870 |
| 1.19.2 | 2.1.13 beta 3 / 2.1.4 | 4.2.1518 |
| 1.20.1 | 2.1.13 beta 5 / 2.6.3 | 4.3.2508 |
| 26.3 | No verified creature dependency | No verified creature dependency |

Soul dragons retain captured color/growth-stage forms and size, use a 100-block owner range, and have neutral detached previews. Hydra previews retain head/severing state. Scoped terrain hooks cover dragons, Cyclops, Sea Serpents, and Death Worms while preserving wild behavior and native entity damage, including newer-port dragon charge area damage.

Twilight ownership covers Snow Queen/Lich descendants, Ur-Ghast ghastlings, Swarm Spiders, and Tower/Carminite Broodlings. Master searches and clone cleanup respect exact lineage. The old Lich constructor/type correction is needed only on 1.16.5.

The dedicated Wroughtnaut activation/appearance adapter stays removed. Aquamirae 7.x and creature integrations on 26.3 are unverified. No universal compatibility is promised; see [release notes](docs/releases/1.2.3.md) for exact artifacts and limits.


Owned Sirens keep their normal appearance while idle/chasing and show their
monster form during native BITE/PULL attack animations. Their charm cannot affect
the owner/allied companions; existing charm from that same friendly source is
cleared. Wild Sirens and native server combat remain unchanged. The 1.19.2 port uses native SirenProperties; 1.20.1 uses SirenData after owner
restoration and before effect/data synchronization. No Siren/IAF integration is
certified for 26.3.
## Standalone development

No adjacent core source checkout is needed. Use the matching branch/JDK: Java 17 build + Java 8 game on 1.16.5, Java 17 on 1.19.2/1.20.1, Java 25 on 26.3.

```powershell
python scripts/bootstrap.py
```

Copy `local.properties.example` to ignored `local.properties` and point it at an existing **installable runtime core JAR**:

```properties
soulcatcherplusJar=C:/Minecraft/mods/soulcatcherplus-1.2.3+1.16.5.jar
```

Absolute/project-relative paths are accepted; use forward slashes on Windows. Select the matching Minecraft/core version, not a sources/dev/addon JAR. For 26.3 use `soulcatcherplus-1.2.3+26.3.jar` in its branch. A command-line override takes precedence:

```powershell
.\scripts\gradle.ps1 build -x test -PsoulcatcherplusJar=C:/Minecraft/mods/soulcatcherplus-1.2.3+1.16.5.jar
```

Legacy branches use content-hashed `fg.deobf`; 26.3 uses `implementation files(coreJar)`. Rebuild/download and select the core explicitly when its sources change. Configure the path before IDE Gradle import/sync; the declared dependency supplies core classes and runtime without another source project.

```powershell
.\scripts\gradle.ps1 build -x test
.\scripts\gradle.ps1 runClient
.\scripts\gradle.ps1 runServer
# Optional legacy creature-mod profile:
.\scripts\gradle.ps1 runClient -PwithIceAndFire -PwithTwilightForest -PwithAquamirae
```

Use flags/dependencies available on the chosen branch. The wrapper/bootstrap selects local tooling; `JAVA_HOME`, `SOULCATCHER_GAME_JAVA` on 1.16.5, and optional `GRADLE_USER_HOME` configure external JDKs/caches. Use `--offline` after dependencies are cached.

Output: `build/<minecraft-version>/libs/soulcatcherplus-compat-1.2.3+<minecraft-version>.jar`. Normal release core JARs have no QA command. Legacy playtest archives use original dependencies; IAF16 development alone normalizes three mapping-forwarder collisions in an ignored copy. Routine core builds do not load this addon.

## Validation

All four fresh addon builds, archive audits, and ordinary installed-client startups
passed before publication. Clients loaded sound/atlases and closed normally without
entering worlds. The older three used matching creature mods; 26.3 used only
core/addon. No automated tests, GameTests, gameplay, multiplayer, or fresh
dedicated-server checks ran. See [release notes](docs/releases/1.2.3.md) for exact
profiles, known external warnings, and integration limits.

[MIT](LICENSE)
