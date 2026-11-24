# Tiny Mob Farm Remastered — Minecraft 26.3

Fabric and NeoForge builds require Java 25, UI Lib 22.1.0 and YAML Config
22.1.0. Fabric additionally requires Fabric Loader 0.20.1 and Fabric API
0.167.0+26.3. The NeoForge build targets NeoForge 26.3.0.117.

Knot is no longer required. Its published 26.2 builds fail during Minecraft
26.3 startup. Registration, entity interactions, fake players and inventory
transfers now use the corresponding loader APIs. Existing mod IDs, item IDs,
block IDs, lasso components and configuration paths are preserved.

## Build

```powershell
# CI mode avoids the memory-intensive Minecraft source decompilation step.
$env:CI = 'true'
.\gradlew.bat build
```

On Linux/macOS: `CI=true ./gradlew build`.

Install the JAR from `fabric/build/libs` or `neoforge/build/libs`, excluding
the `-sources.jar`, together with that loader's dependencies.

## Validation

The port has been compiled for both loaders. Fabric server initialization
reached the EULA gate. NeoForge loaded the mod and recipe registries and
started a new world successfully. There are no unit tests in this repository.

Before releasing, check both clients in a disposable world: capture a mob,
open each farm's UI, verify its miniature rendering, collect drops in a chest
below it, test looting and lasso durability, and disable a farm in the config
to verify its recipe and creative-tab entry disappear. Client visuals and
these gameplay interactions have not yet been manually verified.
