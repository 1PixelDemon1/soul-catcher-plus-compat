# Soul Catcher Plus Compat

Optional mod integrations for [Soul Catcher Plus](https://github.com/1PixelDemon1/soul-catcher-plus).
Includes Mowzie's Mobs adapters and ally filtering, Twilight Forest summon ownership
hooks, and Aquamirae companion music suppression. Creature mods and their libraries
are optional and installed separately; support depends on their version and behavior.
Generic capture, AI, ownership and rendering remain in the core mod.

This branch targets **Minecraft 1.16.5 / Forge**. Install the addon and the exact
matching core JAR on client and server. The addon bundles no core or third-party classes.
Registry IDs, profiles and saved collection data are unchanged by the repository move.

| Branch | Target |
| --- | --- |
| `main`, `develop` | Forge 1.16.5; Java 8 runtime, Java 17/Jabel build |
| `forge/1.19.2` | Forge 1.19.2; Java 17 |
| `forge/1.20.1` | Forge 1.20.1; Java 17 |

## Local development

Keep this repository next to the `SoulCatcherPlus` core checkout. Version-specific
ports use the matching core worktree at `SoulCatcherPlus/.worktrees/forge-<version>`.
Use the matching branch in both projects. The core supplies its Forge build and public
API; all addon sources and integration build wiring are owned here.

```powershell
.\scripts\gradle.ps1 build --offline
# 1.16.5 manual development client:
.\scripts\gradle.ps1 runClient --offline -PgameTests -PwithTwilightForest -PwithAquamirae
```

Set `JAVA_HOME` to Java 17 or bootstrap the adjacent core with `python scripts/bootstrap.py`.
Omit `--offline` when dependencies have not been downloaded. For another core location,
pass `-PcoreDir=<path>` (and configure `JAVA_HOME` as needed).

`build.gradle` delegates to the matching core build, which applies `core-integration.gradle`.
The addon compiles against core output and packages only addon classes/resources.
Output: `build/1.16.5/libs/soulcatcherplus-compat-1.0.0+1.16.5.jar`.
The core's `build`, `compatJar`, `runClient` and (1.16.5) `playtestZip` also use this
external checkout when available. `-PwithoutCompatAddon` or an absent addon checkout
leaves the core independent. Core release builds and historical tags are unchanged.

Do not run simultaneous builds of the same core/addon checkout. Keep game worlds
separate for each Minecraft version. See the core's [public API](https://github.com/1PixelDemon1/soul-catcher-plus/blob/develop/docs/API.md)
for extension points; individual integrations must not register duplicate adapters.

## History and license

The original `addons/compat` commit history is preserved with paths rooted here.
This extraction creates no release or release tags. [MIT](LICENSE).
