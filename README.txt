ASW + Fresh Animations Fix (Forge 1.20.1) - SOURCE, untested

Build (needs JDK 17 + internet):
  1. Install JDK 17.
  2. In this folder run:  gradle wrapper   (or copy a gradlew from any Forge MDK)
  3. Run: ./gradlew build        (Windows: gradlew.bat build)
  4. Jar appears in build/libs/asw_fa_fix-1.20.1-1.0.0.jar -> put in your mods folder.

Config: config/asw_fa_fix-client.toml  ->  mode = "HIDE" | "RESET" | "OFF"
 HIDE  : no weathering layer on first-person arm when EMF/ETF is installed (safest)
 RESET : resets arm/sleeve transforms then draws layer (experimental, may still glitch
         because EMF replaces the arm geometry)
If the game says the mixin target isn't found, the mod's method name differs; send me the log.
