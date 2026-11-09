![Soul Catcher Plus](art/branding/logo-banner.png)

# Soul Catcher Plus Compat

Optional creature-mod corrections for [Soul Catcher Plus](https://github.com/1PixelDemon1/soul-catcher-plus): Mowzie area-attack ally filtering, Twilight Forest summon ownership, and Aquamirae companion music handling. The core and creature mods are separate installations. Support depends on the installed versions and their behavior.

## Release 1.2.0

Separate addon/core builds are available for four Forge platforms. All four addon JARs built and reached the client title screen in the profiles recorded in the [release notes](docs/releases/1.2.0.md). **The 26.3 profile contained only the core and addon, without creature mods.** It is a load baseline with limited integration availability, not verified support for those creature mods.

| Minecraft | Forge build target/minimum | Game Java | Matching core version |
|---|---|---|---|
| 1.16.5 | 36.2.42 | 8 | `1.2.0+1.16.5` |
| 1.19.2 | 43.5.0 | 17 | `1.2.0+1.19.2` |
| 1.20.1 | 47.4.28 | 17 | `1.2.0+1.20.1` |
| 26.3 | 66.1.5 | 25 | `1.2.0+26.3` |

Install matching core and addon JARs on **both client and server**, with selected creature mods and their version-appropriate libraries. The addon requires its exact core version. Update the pair together and replace old files rather than installing duplicates. Neither core nor third-party classes/assets are bundled in the addon.

## Integrations and tested baselines

- **Mowzie's Mobs:** exclude allies from area-attack candidate lists for summoned Mowzie entities. The dedicated Ferrous Wroughtnaut activation/appearance adapter and soul-profile override have been removed; it uses the core's generic behavior plus the area filter.
- **Twilight Forest:** Snow Queen ice-crystal attribution and native master restoration for Lich clones/minions, connecting recognized summons to core ownership/lifecycle rules.
- **Aquamirae:** suppress identifiable companion Cornelia music and preserve the ordinary encounter's shared cooldown. The inspected 1.20.1 correction targets 6.x; 7.x support is not verified.

Addon client-startup profiles passed with Twilight Forest (CurseForge file 3575220) and Aquamirae 5.4.API11/Obscure API/GeckoLib on 1.16.5, and Twilight Forest/Mowzie/GeckoLib on 1.19.2 and 1.20.1. Version-specific API baselines and external resource warnings are recorded in the release notes. Startup is not an in-game combat/music/ownership test.

For **26.3**, the release lookup found no Modrinth Forge releases for Mowzie's Mobs, Aquamirae, or Patchouli; an official Twilight Forest Forge 26 build was not verified. The addon built and loaded with the core alone. Do not infer that legacy creature-mod JARs or every named integration work on 26.3. Universal mod compatibility is not promised.

## Standalone development setup

The dependency is an **installable core JAR**. No adjacent core source checkout, particular directory layout, or shared Gradle cache is required. Use the matching port branch and Minecraft/core version.

1. Prepare the branch's tooling with `python scripts/bootstrap.py`, or install the required JDKs. 1.16.5 uses Java 17 for Gradle/javac plus Java 8 for the game; 1.19.2/1.20.1 use Java 17; 26.3 uses Java 25 for build and game.
2. Obtain a matching runtime core JAR. The example below assumes the file exists at `C:/Minecraft/mods/soulcatcherplus-1.2.0+1.16.5.jar`.
3. Copy `local.properties.example` to ignored `local.properties` and configure the exact file:

```properties
soulcatcherplusJar=C:/Minecraft/mods/soulcatcherplus-1.2.0+1.16.5.jar
```

Use forward slashes on Windows. Absolute or project-relative paths are accepted. Select the runtime core, not a sources/dev JAR or the addon itself. For 26.3, for example, select `C:/Minecraft/mods/soulcatcherplus-1.2.0+26.3.jar` in its branch. Keep local paths out of Git.

The command-line override takes precedence over `local.properties`:

```powershell
.\scripts\gradle.ps1 build -x test -PsoulcatcherplusJar=C:/Minecraft/mods/soulcatcherplus-1.2.0+1.16.5.jar
```

The build reads the JAR's mod ID/version and requires the branch's `core_version`. Legacy Forge projects use `fg.deobf` and a content-hashed local dependency; replacing the same filename refreshes its remap key. Minecraft 26.3 uses the unobfuscated runtime JAR through `implementation files(coreJar)` rather than the legacy deobfuscation path. Neither approach compiles an adjacent core checkout. Rebuild/download the core JAR explicitly when core sources change.

Basic commands from the selected branch/project:

```powershell
python scripts/bootstrap.py
.\scripts\gradle.ps1 build -x test
.\scripts\gradle.ps1 runClient
.\scripts\gradle.ps1 runServer
```

The wrapper respects/provides branch-local tooling; set `JAVA_HOME` to the correct JDK when using an external installation. `SOULCATCHER_GAME_JAVA` selects the Java 8 game JDK on 1.16.5. The 26.3 bootstrap/wrapper selects Java 25. Use `--offline` only after dependencies are cached; `GRADLE_USER_HOME` can choose an independent cache.

Import the selected branch's Gradle project into your IDE using the matching Gradle JDK. Configure `local.properties` before Gradle sync. The declared core dependency supplies core classes and the development runtime; there is no need to attach an adjacent source project. Generated run configurations, or `runClient`/`runServer` above, load the addon and supplied core. Normal release core JARs contain no manual QA command.

For the optional **1.16.5** creature-mod profile:

```powershell
.\scripts\gradle.ps1 runClient -PwithTwilightForest -PwithAquamirae
```

Use only available flags/dependencies in the chosen branch. The 26.3 startup baseline has no creature mods. Output uses `build/<minecraft-version>/libs/soulcatcherplus-compat-1.2.0+<minecraft-version>.jar`. Legacy `playtestZip` creates a separate development bundle from the original core/addon/dependency JARs; it is not an automatic cross-platform dependency installer. Run directories/worlds belong to the selected project and platform.

Routine core development does not load this addon. Build/title-screen results do not verify every AI, music, renderer, or multiplayer interaction. See [release notes](docs/releases/1.2.0.md), [changelog](CHANGELOG.md), and the core [public API](https://github.com/1PixelDemon1/soul-catcher-plus/blob/main/docs/API.md).

[MIT](LICENSE)
