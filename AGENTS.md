# Soul Catcher Plus Compat

The addon owns all mod-specific adapters, mixins, profiles, metadata and integration
build wiring. Generic ownership, summoning and extension APIs belong to the core.
All creature-mod hooks must remain optional. Never bundle core or third-party classes.

`develop` targets Forge 1.16.5 (36.2.42), Java 8 runtime / Java 17 + Jabel compiler.
`forge/1.19.2` and `forge/1.20.1` target their matching core branches, Java 17.
`main` is the public baseline. Do not create releases, tags or push without a request.
Keep historical release tags in the original repository unchanged.

The adjacent `SoulCatcherPlus` checkout supplies the public API, Forge build and
development environment. `core-integration.gradle` is applied there; this repository's
`build.gradle` delegates tasks to that matching checkout. Use `-PcoreDir=<path>` from
this repository, or `-PcompatAddonDir=<path>` from the core, for another local layout.
The core must match `minecraft_version` and `mod_version` in this repository.

Build: `.\scripts\gradle.ps1 build --offline` (no automated tests).
Outputs: `build/<minecraft_version>/libs/soulcatcherplus-compat-<mod_version>.jar`.
1.16.5 manual client: `.\scripts\gradle.ps1 runClient --offline -PgameTests -PwithTwilightForest -PwithAquamirae`.
Do not force-close a running client or run automated tests/GameTests/UI without a request.
After substantive code changes request a read-only review from `code_quality_reviewer`.
Keep caches, tools, builds and local worlds ignored. Do not commit dependency JARs.
