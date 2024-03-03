package org.despacito696969.mi_addons.mixin;

import aztech.modern_industrialization.compat.viewer.usage.MultiblockCategory;
import org.despacito696969.mi_addons.MIAddonsConfig;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(MultiblockCategory.class)
public abstract class MultiblockCategoryMixin {
   @ModifyArg(
           method = "<init>",
           at = @At(
                   value = "INVOKE",
                   target = "Laztech/modern_industrialization/compat/viewer/abstraction/ViewerCategory;<init>(Ljava/lang/Class;Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/network/chat/Component;Lnet/minecraft/world/item/ItemStack;II)V"
           ),
           index = 5
   )
   private static int modifyH(int value) {
      return value + (((mi_addons$getSlots() + 5) / 6) - 1) * 20;
   }

   @Unique
   private static int mi_addons$getSlots() {
      return MIAddonsConfig.getConfigAndPossiblyRegister().rei_multiblock_slot_count;
   }


   @ModifyArgs(
           remap = false,
           method = "buildLayout(Laztech/modern_industrialization/compat/viewer/usage/MultiblockCategory$Recipe;Laztech/modern_industrialization/compat/viewer/abstraction/ViewerCategory$LayoutBuilder;)V",
           at = @At(remap = false, value = "INVOKE", target = "Laztech/modern_industrialization/compat/viewer/abstraction/ViewerCategory$LayoutBuilder;inputSlot(II)Laztech/modern_industrialization/compat/viewer/abstraction/ViewerCategory$SlotBuilder;")
   )
   public void injected(Args args) {
      int x = args.get(0);
      int i = (x - 10) / 20;
      args.set(0, 10 + (i % 6) * 20);
      args.set(1, 10 + (i / 6) * 20);
   }

   @ModifyConstant(
           remap = false,
           method = "buildLayout(Laztech/modern_industrialization/compat/viewer/usage/MultiblockCategory$Recipe;Laztech/modern_industrialization/compat/viewer/abstraction/ViewerCategory$LayoutBuilder;)V",
           constant = @Constant(intValue = 6)
   )
   private int modifySlotCount(int s) {
      return mi_addons$getSlots();
   }
}
