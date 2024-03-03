package org.despacito696969.mi_addons.mixin;

import aztech.modern_industrialization.machines.components.CrafterComponent;
import aztech.modern_industrialization.machines.recipe.MachineRecipe;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.nbt.CompoundTag;
import org.despacito696969.mi_addons.MIAddonsConfig;
import org.despacito696969.mi_addons.batch_crafting.BatchSelection;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = CrafterComponent.class, priority = 2000)
public abstract class CrafterComponentMixin implements BatchSelection.BatchCrafterComponent
{
   @Unique
   public float mi_addons$overclockFractional = 0;
   @Unique
   public int mi_addons$prevEfficiency = 0;

   @Unique
   public int MIAddons$currentBatchSize = 1;
   @Unique
   public int MIAddons$desiredBatchSize = 1;
   @Unique
   public int MIAddons$maxBatchSize = 1;

   @Unique
   private static final String CURRENT_BATCH_SIZE_NBT = "currentBatchSize";
   @Unique
   private static final String DESIRED_BATCH_SIZE_NBT = "desiredBatchSize";

   @Shadow(remap = false) private long recipeMaxEu;
   @Shadow(remap = false) private int efficiencyTicks;
   @Shadow(remap = false) private int maxEfficiencyTicks;

   @Override
   public int MIAddons$getDesiredRecipeBatching() {
      return MIAddons$desiredBatchSize;
   }

   @Override
   public void MIAddons$setDesiredRecipeBatching(int batchSize) {
      MIAddons$desiredBatchSize = batchSize;
   }

   @Override
   public int MIAddons$getMaxBatch() {
      return MIAddons$maxBatchSize;
   }

   @Override
   public void MIBatchCraftingAddon$setMaxBatch(int max_batch) {
      MIAddons$maxBatchSize = max_batch;
   }

   @Inject(method = "updateActiveRecipe", at = @At(value = "HEAD", remap = false), cancellable = true, remap = false)
   private void updateActiveRecipeMixin(CallbackInfoReturnable<Boolean> ci) {
      // Sanity checks, these ifs should never succeed
      if (MIAddons$desiredBatchSize > MIAddons$maxBatchSize) {
         MIAddons$desiredBatchSize = MIAddons$maxBatchSize;
      }
      if (MIAddons$desiredBatchSize < 1) {
         MIAddons$desiredBatchSize = 1;
      }

      if (efficiencyTicks == 0) {
         MIAddons$currentBatchSize = MIAddons$desiredBatchSize;
      }

      if (MIAddons$currentBatchSize != MIAddons$desiredBatchSize) {
         ci.setReturnValue(false);
      }
   }

   @Redirect(method = "updateActiveRecipe", at = @At(value = "INVOKE", remap = false, target = "Laztech/modern_industrialization/machines/recipe/MachineRecipe;getTotalEu()J"), remap = false)
   private long getTotalEuRedirect(MachineRecipe instance) {
      return instance.getTotalEu() * MIAddons$currentBatchSize;
   }

   @ModifyArg(method = "updateActiveRecipe", at = @At(value = "INVOKE", remap = false, target = "Laztech/modern_industrialization/machines/components/CrafterComponent;getRecipeMaxEu(JJI)J"), index = 0, remap = false)
   private long recipeEuModifier(long eu) {
      return eu * MIAddons$currentBatchSize;
   }

   @Inject(method = "writeNbt", at = @At("TAIL"), remap = false)
   public void writeNbtMixin(CompoundTag tag, CallbackInfo ci) {
      tag.putInt(CURRENT_BATCH_SIZE_NBT, this.MIAddons$currentBatchSize);
      if (this.MIAddons$maxBatchSize > 1) {
         // We don't need to save desiredBatchSize == 1
         tag.putInt(DESIRED_BATCH_SIZE_NBT, this.MIAddons$desiredBatchSize);
      }
      else {
         // Troll (not really, nbt clean up is very important :gladeline:)
         tag.remove(DESIRED_BATCH_SIZE_NBT);
      }
   }

   @Inject(method = "readNbt", at = @At("TAIL"), remap = false)
   public void readNbtMixin(CompoundTag tag, CallbackInfo ci) {
      if (tag.contains(CURRENT_BATCH_SIZE_NBT)) {
         MIAddons$currentBatchSize = tag.getInt(CURRENT_BATCH_SIZE_NBT);
      }
      if (tag.contains(DESIRED_BATCH_SIZE_NBT)) {
         MIAddons$desiredBatchSize = tag.getInt(DESIRED_BATCH_SIZE_NBT);
      }
   }


   @Redirect(method = "takeItemInputs", at = @At(value = "FIELD", remap = false, target = "Laztech/modern_industrialization/machines/recipe/MachineRecipe$ItemInput;amount:I", opcode = Opcodes.GETFIELD), remap = false)
   private int itemInputAmountModifier(MachineRecipe.ItemInput input) {
      return input.amount * MIAddons$currentBatchSize;
   }

   @Redirect(method = "putItemOutputs", at = @At(value = "FIELD", remap = false, target = "Laztech/modern_industrialization/machines/recipe/MachineRecipe$ItemOutput;amount:I", opcode = Opcodes.GETFIELD), remap = false)
   private int itemOutputAmountModifier(MachineRecipe.ItemOutput output) {
      return output.amount * MIAddons$currentBatchSize;
   }

   @Redirect(method = "takeFluidInputs", at = @At(value = "FIELD", remap = false, target = "Laztech/modern_industrialization/machines/recipe/MachineRecipe$FluidInput;amount:J", opcode = Opcodes.GETFIELD), remap = false)
   private long fluidInputAmountModifier(MachineRecipe.FluidInput input) {
      return input.amount * MIAddons$currentBatchSize;
   }

   @Redirect(method = "putFluidOutputs", at = @At(value = "FIELD", remap = false, target = "Laztech/modern_industrialization/machines/recipe/MachineRecipe$FluidOutput;amount:J", opcode = Opcodes.GETFIELD), remap = false)
   private long fluidOutputAmountModifier(MachineRecipe.FluidOutput output) {
      return output.amount * MIAddons$currentBatchSize;
   }

   @Redirect(method = "getRecipeMaxEfficiencyTicks", at = @At(value = "INVOKE", remap = false, target = "Laztech/modern_industrialization/machines/recipe/MachineRecipe;getTotalEu()J"), remap = false)
   private long getTotalEuMixin(MachineRecipe recipe) {
      return recipe.getTotalEu() * MIAddons$currentBatchSize;
   }

   /**
    * @author despacito696969
    * @reason meow
    */

   @Inject(method = "tickRecipe", at = @At(value = "HEAD"), remap = false)
   public void tickRecipe_head(CallbackInfoReturnable<Boolean> cir) {
      mi_addons$prevEfficiency = efficiencyTicks;
   }

   @Inject(method = "tickRecipe", at = @At(value = "INVOKE", target = "Laztech/modern_industrialization/machines/components/CrafterComponent;clearActiveRecipeIfPossible()V"), remap = false)
   public void tickRecipe_tail(CallbackInfoReturnable<Boolean> cir, @Local long eu) {
      efficiencyTicks = mi_addons$prevEfficiency;
      if (eu == recipeMaxEu) {
         if (efficiencyTicks < maxEfficiencyTicks) {
            mi_addons$overclockFractional += MIAddonsConfig.getConfig().overclock_gain / 20.0f;
            if (mi_addons$overclockFractional >= 1.0f) {
               float ticks = (float)(long)mi_addons$overclockFractional;
               efficiencyTicks += (int)ticks;
               mi_addons$overclockFractional -= ticks;
               if (efficiencyTicks >= maxEfficiencyTicks) {
                  mi_addons$overclockFractional = 0.0f;
                  efficiencyTicks = maxEfficiencyTicks;
               }
            }
         }
      } else if (eu < recipeMaxEu) { // If we didn't use the max energy this tick and the recipe is still ongoing,
         mi_addons$overclockFractional -= MIAddonsConfig.getConfig().overclock_loss / 20.0f;
         if (mi_addons$overclockFractional < 0) {
            if (efficiencyTicks <= 0) {
               efficiencyTicks = 0;
               mi_addons$overclockFractional = 0.0f;
            }
            else {
               float ticks = -(float)Math.floor(mi_addons$overclockFractional);
               mi_addons$overclockFractional += ticks;
               efficiencyTicks -= (int)ticks;
               efficiencyTicks = Math.max(efficiencyTicks, 0);
            }
         }
         // remove one efficiency tick
      }
   }
}
