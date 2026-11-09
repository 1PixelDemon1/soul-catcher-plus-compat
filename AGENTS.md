# Soul Catcher Plus Compat

Standalone ForgeGradle project. All mod-specific adapters, mixins, profiles and metadata
belong here; generic ownership, summoning and extension APIs belong to the core mod.
Creature mods remain optional. Never bundle core or third-party classes in the addon.

`develop` targets Forge 1.16.5 (36.2.42), Java 8 runtime / Java 17 + Jabel compiler.
`forge/1.19.2` and `forge/1.20.1` target their matching Forge versions, Java 17.
`main` is the public baseline. Keep Git operations local; no push, tags or releases
without an explicit user request. Do not rewrite history or existing release tags.

The core is an installable JAR dependency, never an adjacent source checkout.
Configure `soulcatcherplusJar` in ignored `local.properties`, following
`local.properties.example`, or pass `-PsoulcatcherplusJar=<path>`.
The JAR must declare the expected `core_version`; use the matching Minecraft build.
ForgeGradle deobfuscates it for compilation and the development runtime. Its content
SHA identifies the dependency so replacing the same filename refreshes the remap cache.
Changing core sources does not update this JAR automatically.

Use the installed minecraft-modding skill for mod code, matching the actual port APIs.
Build: `.\scripts\gradle.ps1 build -x test --offline`.
Client16: `.\scripts\gradle.ps1 runClient --offline -PwithTwilightForest -PwithAquamirae`.
QA commands exist only if the chosen core JAR provides them; normal release JARs do not.
Java tooling may be installed with `python scripts/bootstrap.py`. No core repository
or shared Gradle cache is required. Explicit JAVA_HOME/GRADLE_USER_HOME settings are honored.
Outputs: `build/<minecraft_version>/libs/soulcatcherplus-compat-<mod_version>.jar`.

Request a read-only `code_quality_reviewer` review after substantive code changes.
Compile and launch the current development client for manual checks; confirm startup
from logs. Never force-close a running client or discard worlds. Automated tests,
GameTests and game UI automation require a separate request. Keep paths, dependencies,
caches, tools, build outputs and local worlds ignored. Do not commit local.properties.

`forge/26.3` targets Forge 66.1.5 and Java 25. It uses ForgeGradle 7 and the
unobfuscated installable core JAR directly; do not use fg.deobf or reobfJar there.
Creature integrations on 26.3 are unverified until matching third-party builds are
available and checked. Use the matching Java 25 scripts on that branch.
