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
 * Priority is higher than Adaptive Skin Weathering's mixin (default 1000), so this is applied
 * after it: its "renderWeatheringArm" already exists, and our RETURN injections run after its own.
 *
 * Only the FIRST-PERSON hand is affected: a flag is raised while renderRightHand/renderLeftHand
 * run, and renderWeatheringArm is only touched while that flag is set.
 */
@Mixin(value = PlayerRenderer.class, priority = 2000)
public class PlayerRendererFixMixin {

    private static boolean aswfafix$firstPerson = false;

    @Inject(method = "renderRightHand", at = @At("HEAD"))
    private void aswfafix$rightStart(PoseStack p, MultiBufferSource b, int l, AbstractClientPlayer pl, CallbackInfo ci) {
        aswfafix$firstPerson = true;
    }

    @Inject(method = "renderRightHand", at = @At("RETURN"))
    private void aswfafix$rightEnd(PoseStack p, MultiBufferSource b, int l, AbstractClientPlayer pl, CallbackInfo ci) {
        aswfafix$firstPerson = false;
    }

    @Inject(method = "renderLeftHand", at = @At("HEAD"))
    private void aswfafix$leftStart(PoseStack p, MultiBufferSource b, int l, AbstractClientPlayer pl, CallbackInfo ci) {
        aswfafix$firstPerson = true;
    }

    @Inject(method = "renderLeftHand", at = @At("RETURN"))
    private void aswfafix$leftEnd(PoseStack p, MultiBufferSource b, int l, AbstractClientPlayer pl, CallbackInfo ci) {
        aswfafix$firstPerson = false;
    }

    @Inject(method = "renderWeatheringArm", at = @At("HEAD"), cancellable = true, remap = false, require = 0)
    private void aswfafix$guard(PoseStack pose, MultiBufferSource buf, int light,
                                AbstractClientPlayer player, ModelPart arm, ModelPart sleeve,
                                CallbackInfo ci) {
        if (!aswfafix$firstPerson || !AswFaFix.emfPresent()) return;
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
