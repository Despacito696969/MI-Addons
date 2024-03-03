package org.despacito696969.mi_addons.mixin;

import aztech.modern_industrialization.ModernIndustrialization;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import org.despacito696969.mi_addons.batch_crafting.BatchSelection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ModernIndustrialization.class)
public class ModernIndustrializationMixin {
    @Inject(method = "setupPackets", at = @At("TAIL"), remap = false)
    private static void setupPacketsMixin(CallbackInfo ci) {
        ServerPlayNetworking.registerGlobalReceiver(BatchSelection.PacketsC2S.CHANGE_BATCH_SIZE, BatchSelection.PacketsC2S.ON_CHANGE_BATCH_SIZE);
    }
}
