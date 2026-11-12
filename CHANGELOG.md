# Changelog

## Unreleased

No changes yet.

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
