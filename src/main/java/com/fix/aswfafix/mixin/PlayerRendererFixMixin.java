package com.fix.aswfafix.mixin;

import com.fix.aswfafix.AswFaFix;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Priority is higher than Adaptive Skin Weathering's mixin (default 1000) so it is applied
 * AFTER it, which means its merged method "renderWeatheringArm" already exists on PlayerRenderer.
 */
@Mixin(value = PlayerRenderer.class, priority = 2000)
public class PlayerRendererFixMixin {

    @Inject(method = "renderWeatheringArm", at = @At("HEAD"), cancellable = true, remap = false, require = 0)
    private void aswfafix$guard(PoseStack pose, MultiBufferSource buf, int light,
                                AbstractClientPlayer player, ModelPart arm, ModelPart sleeve,
                                CallbackInfo ci) {
        if (!AswFaFix.emfPresent()) return;
        switch (AswFaFix.MODE.get()) {
            case HIDE -> ci.cancel();
            case RESET -> { reset(arm); reset(sleeve); }
            default -> { }
        }
    }

    private static void reset(ModelPart p) {
        p.x = 0; p.y = 0; p.z = 0;
        p.xRot = 0; p.yRot = 0; p.zRot = 0;
        p.xScale = 1; p.yScale = 1; p.zScale = 1;
    }
}
