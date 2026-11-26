# Changelog

## Unreleased

No changes yet.

## 1.3.0.1 — 2026-11-26

Maintenance release for all four maintained Minecraft/Forge platforms.

- Raise the 1.16.5 Forge minimum from 36.0.42 to 36.2.26, matching the core; keep the 36.2.42 build target and other ports' existing targets/minima.
- Accept core 1.3.0.1 alongside 1.3.0 and 1.3.0-prerelease for the matching Minecraft version. Preserve existing integration behavior and standalone configured-core-JAR development.
- Order the exact core-version alternatives for Forge's Maven range parser; verify all three intended same-Minecraft versions are accepted and a different Minecraft version is rejected.
- Pair with the 1.20.1 core no-argument entrypoint correction; the loader fix belongs to the core rather than new addon gameplay.

All eight fresh core/addon builds and 16 runtime/sources archive audits passed, including exact core-version range acceptance/rejection. A new 26-run matrix checked exact 1.3.0.1 release JAR bytes across 13 Minecraft/Forge combinations; every client passed startup and normal exit 0. A separate isolated OccultCraft copy on Forge 47.2.5 passed with both final JARs. No worlds, gameplay, multiplayer, dedicated-server checks, automated tests, GameTests, or UI automation were run. The earlier 1.3.0-versioned matrix is separate historical evidence. See [release notes](docs/releases/1.3.0.1.md).

## 1.3.0 — 2026-11-25

Release for Minecraft 1.16.5/1.19.2/1.20.1/26.3, paired with core 1.3.0. Forge build targets remain 36.2.42/43.5.0/47.4.28/66.1.0 with Java 8/17/17/25; runtime minima are declared separately.

- Extend terrain protection to supported owned Twilight Forest, Aquamirae, and Mowzie souls and their attacks. Preserve projectile provenance and filter block/explosion effects without globally disabling wild behavior or native entity-damage paths.
- Cover version-specific Twilight direct block writes, transformations, plant growth, ice removal, and falling-ice creation/landing; prevent protected falling blocks from replacing or dropping terrain blocks.
- Guard Aquamirae Maze Mother ice breaking and Mowzie ice-breath freezing using version-matched caster resolution. Add matching Sculptor trade-test protection on 1.19.2 and Elokosa leap-marker protection on 1.20.1.
- Broaden explicit core acceptance to matching 1.3.0 stable or 1.3.0-prerelease, retain independent installable-core-JAR setup, and separate lowered Forge runtime minima from build targets. Compat 1.2.3 remains the pairing for core 1.2.3.
- Retain existing IAF forms/terrain/Siren corrections, Twilight ownership, Mowzie ally filtering, and version-specific Aquamirae music hooks. No universal integration support or verified creature dependency is claimed for 26.3.

All four fresh addon builds and eight addon runtime/sources archive audits passed, alongside the four core builds. All eight core/addon installed-production-client profiles completed startup and normal closure with exit 0 using final release bytes. The older three addon profiles used version-matched creature dependencies; 26.3 used core/addon only. No new dedicated-server, automated-test, GameTest, UI-automation, gameplay, or multiplayer verification ran. See [release notes](docs/releases/1.3.0.md) for exact profiles and known nonfatal external resource diagnostics.

## 1.2.3 — 2026-11-13

Standalone addon builds require core 1.2.3 for Minecraft 1.16.5/1.19.2/1.20.1/26.3; Forge targets are 36.2.42/43.5.0/47.4.28/66.1.0 with Java 8/17/17/25.

- Show owned IAF Sirens in normal form except during native BITE/PULL attack animations, preserving native server combat and wild behavior.
- Block owner/allied charm from an owned Siren and clear existing charm from that same friendly source, preventing its pink overlay/forced approach/jump-scare sequence without disabling unrelated wild charm.
- Port the Siren correction to matching IAF 1.19.2/1.20.1 APIs. Legacy SirenProperties guards remain on 16/19; the 20 SirenData tick clears friendly charm after owner restoration and before effects/data sync. No creature integration is certified for 26.3.
- Require the matching 1.2.3 core, retain previous IAF/TF/Mowzie/Aquamirae corrections and approved branding, and preserve the separate installable-core-JAR development setup.

All four fresh addon builds, archive audits, and ordinary installed-production-client startups passed before publication; clients closed normally without entering worlds. Final source/API review found no actionable issues. Older clients used version-matched creature dependencies; 26.3 used core/addon only. No automated tests, GameTests, UI automation, gameplay, multiplayer, manual combat, or fresh dedicated-server checks ran. See [release notes](docs/releases/1.2.3.md) for profiles and known external warnings.

## 1.2.2 — 2026-11-12

Matching core/addon builds target Minecraft 1.16.5/1.19.2/1.20.1/26.3 with Forge 36.2.42/43.5.0/47.4.28/66.1.0 and Java 8/17/17/25.

- Add Ice and Fire terrain/form/preview corrections on 1.16.5, 1.19.2, and 1.20.1. Preserve native charge area damage with scoped newer-port terrain hooks, freeze captured growth, use a 100-block dragon range, and preserve Hydra head/severing previews.
- Baselines: IAF 2.1.12 patch-1 + Citadel 1.8.1 (16), IAF 2.1.13 beta3/file5037952 + Citadel 2.1.4/file4556677 (19), IAF 2.1.13 beta5/file5633453 + Citadel 2.6.3/file7476570 (20).
- Extend Twilight Forest 4.0.870/4.2.1518/4.3.2508 ownership to Ur-Ghast, Swarm Spider, and Tower/Carminite Broodling descendants; isolate master searches/clone cleanup by lineage. The old Lich constructor/type fix applies only to 1.16.5.
- Retain Mowzie ally filtering and version-specific Aquamirae music; Wroughtnaut-specific activation stays removed and Aquamirae 7.x remains unverified.
- Align 26.3 with Forge 66.1.0; certify no creature integration without matching verified dependencies.
- Adopt the approved right-aligned COMPAT banner and portable standalone core-JAR setup. Original dependencies remain unmodified in playtest archives; legacy IAF dev normalization stays local.

All four fresh addon builds, archive audits, and dedicated-server startups passed with graceful exit 0. Profiles used IAF/TF/Aquamirae (16), IAF/TF (19/20), and core/addon only (26.3); the 1.16.5 client also completed resource/sound/atlas startup. Source/API review found no remaining actionable issues. No automated tests/GameTests/UI or gameplay/multiplayer tests ran. Known external diagnostics do not certify error-free logs or 26.3 creature integrations. See [release notes](docs/releases/1.2.2.md).

## 1.2.0 — 2026-11-10

Release separate addon builds matching core 1.2.0 for Minecraft 1.16.5 / Forge 36.2.42 / Java 8, Minecraft 1.19.2 / Forge 43.5.0 / Java 17, Minecraft 1.20.1 / Forge 47.4.28 / Java 17, and Minecraft 26.3 / Forge 66.1.5 / Java 25.

- Build against an installable core JAR configured in ignored `local.properties` or `-PsoulcatcherplusJar=...`, checking mod ID and exact version. Legacy projects use a content-hashed `fg.deobf` dependency; the unobfuscated 26.3 project uses `implementation files(coreJar)`. Neither requires adjacent sources or an automatic core build.
- Remove the dedicated Ferrous Wroughtnaut activation/appearance adapter and soul-profile override across maintained ports. Retain generic Mowzie area-attack ally filtering.
- Preserve version-specific Twilight Forest summoner/master and Aquamirae companion-music corrections. The inspected 1.20.1 music controller is 6.x; 7.x is not verified.
- Add a Java 25 / Forge 66.1.5 addon load baseline for 26.3. The release lookup found no Modrinth Forge 26.3 versions of Mowzie/Aquamirae/Patchouli; an official Twilight Forest Forge 26 build was not verified. No creature-mod integration is certified for that platform.
- Set the new 1.19.2 target/minimum to Forge 43.5.0 and retain 47.4.28 for 1.20.1. Preserve historical release requirements and tags.
- Adopt supplied branding and document portable core-JAR paths, command-line overrides, matching Java/Minecraft versions, bootstrap, build, IDE import, and runtime setup.

All four addon JAR builds passed. Client title/resource reload passed with Twilight Forest (CurseForge file 3575220) and Aquamirae 5.4.API11/Obscure API/GeckoLib on 1.16.5, Twilight Forest/Mowzie/GeckoLib on 1.19.2 and 1.20.1, and the core alone on 26.3. Existing external Twilight texture errors on 1.16.5 and Grottol model warnings on 1.20.1 were observed. These are startup checks, not verification of combat, music, ownership, multiplayer, or every mod version. No addon dedicated-server or GameTest/UI-automation result is claimed. See [release notes](docs/releases/1.2.0.md).
