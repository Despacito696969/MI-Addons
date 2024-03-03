package org.despacito696969.mi_addons.mixin;

import aztech.modern_industrialization.machines.GuiComponentsClient;
import org.despacito696969.mi_addons.batch_crafting.BatchSelection;
import org.despacito696969.mi_addons.batch_crafting.BatchSelectionClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiComponentsClient.class)
public abstract class GuiComponentsClientMixin {
    @Inject(method = "<clinit>", at = @At("TAIL"), remap = false)
    private static void registerBatchComponentMixin(CallbackInfo ci) {
        GuiComponentsClient.register(BatchSelection.BATCH_SELECTION, BatchSelectionClient::new);
    }
}
