# Soul Catcher Plus Compat

Optional integrations for [Soul Catcher Plus](https://github.com/1PixelDemon1/soul-catcher-plus):
Mowzie's Mobs adapters/ally filtering, Twilight Forest summon ownership and Aquamirae
companion music suppression. Creature mods and their libraries are optional and
installed separately. Support depends on their versions and behavior.

| Branch | Target | Runtime Java |
| --- | --- | --- |
| `main`, `develop` | Forge 1.16.5 | 8 |
| `forge/1.19.2` | Forge 1.19.2 | 17 |
| `forge/1.20.1` | Forge 1.20.1 | 17 |

This branch targets **Minecraft 1.16.5 / Forge 36.2.42**. Install separate addon
and core JARs on client and server. The required core version is **1.0.0+1.16.5**.
The addon never bundles the core or third-party mods.

## Development setup

This is a standalone ForgeGradle project. **No SoulCatcherPlus source checkout or
particular folder layout is required.**

1. Install Java 17 for Gradle and compilation. 1.16.5 also needs Java 8 for the game.
   `python scripts/bootstrap.py` can install the required JDKs inside this repository.
2. Download an installable core JAR matching this branch (not a sources/dev JAR).
3. Copy `local.properties.example` to `local.properties` and set its path:

```properties
soulcatcherplusJar=C:/Minecraft/mods/soulcatcherplus-1.0.0+1.16.5.jar
```

Use forward slashes on Windows. Absolute and project-relative paths are accepted.
`local.properties` is ignored by Git. Alternatively pass `-PsoulcatcherplusJar=<path>`
or set the property in your user Gradle settings. The build checks the core mod ID
and exact version from the JAR metadata. Copying/rebuilding a JAR at the same path
changes the dependency content hash and invalidates the deobfuscation cache.

```powershell
.\scripts\gradle.ps1 build -x test
# 1.16.5 test profile (other mods are optional):
.\scripts\gradle.ps1 runClient -PwithTwilightForest -PwithAquamirae
```

Use `--offline` once dependencies are cached. The script uses JAVA_HOME or local
`.tools` JDKs; set SOULCATCHER_GAME_JAVA to a Java 8 home if using externally installed
tooling for 1.16.5. GRADLE_USER_HOME can select another cache, but is optional.

Outputs: `build/1.16.5/libs/soulcatcherplus-compat-1.0.0+1.16.5.jar`.
`runClient` loads the configured core JAR and addon sources; `runServer` is available
for manual server checks. A normal core release JAR does not contain the QA command.
`playtestZip` packages the original core JAR, addon JAR and selected original dependencies.
Game worlds live inside this project's version-specific run directories. Existing
core-project worlds remain in their original folders.

The core dependency uses [ForgeGradle's local mod dependency mechanism](https://docs.minecraftforge.net/en/fg-6.x/dependencies/).
Changes to core source require manually rebuilding and selecting a new core JAR;
the addon does not invoke or rebuild another project. The core can likewise load
a built addon using its optional `compatAddonJar` local setting.

## History and license

Original addon history is preserved. No new release or tags are created by this
build change. Public extension APIs are documented in the core repository. [MIT](LICENSE).
