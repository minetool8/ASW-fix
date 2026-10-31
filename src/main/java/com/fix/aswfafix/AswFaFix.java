package com.fix.aswfafix;

import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.common.ForgeConfigSpec;

@Mod("asw_fa_fix")
public class AswFaFix {
    public static final ForgeConfigSpec SPEC;
    /** HIDE = don't draw the weathering layer on the first-person arm (safe).
     *  RESET = reset arm/sleeve transforms first, then draw (experimental). */
    public static final ForgeConfigSpec.EnumValue<Mode> MODE;
    public enum Mode { HIDE, RESET, OFF }
    static {
        ForgeConfigSpec.Builder b = new ForgeConfigSpec.Builder();
        MODE = b.comment("Fix mode when EMF/ETF (Fresh Animations) is loaded: HIDE, RESET or OFF")
                .defineEnum("mode", Mode.HIDE);
        SPEC = b.build();
    }
    public AswFaFix() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, SPEC, "asw_fa_fix-client.toml");
    }
    public static boolean emfPresent() {
        ModList l = ModList.get();
        return l.isLoaded("entity_model_features") || l.isLoaded("entity_texture_features");
    }
}
