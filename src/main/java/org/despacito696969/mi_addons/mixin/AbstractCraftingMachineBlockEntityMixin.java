package org.despacito696969.mi_addons.mixin;

import aztech.modern_industrialization.machines.BEP;
import aztech.modern_industrialization.machines.MachineBlockEntity;
import aztech.modern_industrialization.machines.blockentities.AbstractCraftingMachineBlockEntity;
import aztech.modern_industrialization.machines.components.CrafterComponent;
import aztech.modern_industrialization.machines.components.MachineInventoryComponent;
import aztech.modern_industrialization.machines.components.OrientationComponent;
import aztech.modern_industrialization.machines.gui.MachineGuiParameters;
import aztech.modern_industrialization.machines.guicomponents.ProgressBar;
import aztech.modern_industrialization.machines.init.MachineTier;
import aztech.modern_industrialization.machines.recipe.MachineRecipeType;
import org.despacito696969.mi_addons.batch_crafting.BatchCraftingRegistry;
import org.despacito696969.mi_addons.batch_crafting.BatchSelection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractCraftingMachineBlockEntity.class)
public abstract class AbstractCraftingMachineBlockEntityMixin extends MachineBlockEntity {
    public AbstractCraftingMachineBlockEntityMixin(
            BEP bep, MachineGuiParameters guiParams,
            OrientationComponent.Params orientationParams
    ) {
        super(bep, guiParams, orientationParams);
    }

    @Shadow(remap = false) public abstract CrafterComponent getCrafterComponent();

    @Inject(method = "<init>", at = @At("TAIL"), remap = false)
    public void addBatchComponentMixin(
            BEP bep, MachineRecipeType recipeType, MachineInventoryComponent inventory, MachineGuiParameters guiParams,
            ProgressBar.Parameters progressBarParams, MachineTier tier, CallbackInfo ci
    ) {
        // I tried to add these into the block entity constructors when blocks are registered, but I failed :(
        int max_batch = BatchCraftingRegistry.getBatchSize(bep);
        if (max_batch > 1) {
            registerGuiComponent(new BatchSelection.Server((BatchSelection.BatchCrafterComponent) getCrafterComponent()));
            ((BatchSelection.BatchCrafterComponent) getCrafterComponent()).MIBatchCraftingAddon$setMaxBatch(max_batch);
        }
    }
}