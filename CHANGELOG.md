# Changelog

## Unreleased

No changes yet.

## 1.2.0 — 2026-11-10

Release separate addon builds matching core 1.2.0 for Minecraft 1.16.5 / Forge 36.2.42 / Java 8, Minecraft 1.19.2 / Forge 43.5.0 / Java 17, Minecraft 1.20.1 / Forge 47.4.28 / Java 17, and Minecraft 26.3 / Forge 66.1.5 / Java 25.

- Build against an installable core JAR configured in ignored `local.properties` or `-PsoulcatcherplusJar=...`, checking mod ID and exact version. Legacy projects use a content-hashed `fg.deobf` dependency; the unobfuscated 26.3 project uses `implementation files(coreJar)`. Neither requires adjacent sources or an automatic core build.
- Remove the dedicated Ferrous Wroughtnaut activation/appearance adapter and soul-profile override across maintained ports. Retain generic Mowzie area-attack ally filtering.
- Preserve version-specific Twilight Forest summoner/master and Aquamirae companion-music corrections. The inspected 1.20.1 music controller is 6.x; 7.x is not verified.
- Add a Java 25 / Forge 66.1.5 addon load baseline for 26.3. The release lookup found no Modrinth Forge 26.3 versions of Mowzie/Aquamirae/Patchouli; an official Twilight Forest Forge 26 build was not verified. No creature-mod integration is certified for that platform.
- Set the new 1.19.2 target/minimum to Forge 43.5.0 and retain 47.4.28 for 1.20.1. Preserve historical release requirements and tags.
- Adopt supplied branding and document portable core-JAR paths, command-line overrides, matching Java/Minecraft versions, bootstrap, build, IDE import, and runtime setup.

All four addon JAR builds passed. Client title/resource reload passed with Twilight Forest (CurseForge file 3575220) and Aquamirae 5.4.API11/Obscure API/GeckoLib on 1.16.5, Twilight Forest/Mowzie/GeckoLib on 1.19.2 and 1.20.1, and the core alone on 26.3. Existing external Twilight texture errors on 1.16.5 and Grottol model warnings on 1.20.1 were observed. These are startup checks, not verification of combat, music, ownership, multiplayer, or every mod version. No addon dedicated-server or GameTest/UI-automation result is claimed. See [release notes](docs/releases/1.2.0.md).
