package org.despacito696969.mi_addons;

import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class MIAddonsMixinPlugin implements IMixinConfigPlugin {
   @Override
   public void onLoad(String mixinPackage) {
   }

   static public final String ROOT = "org.despacito696969.mi_addons.mixin";
   @Override
   public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
      if (mixinClassName.equals(ROOT + ".CrafterComponentMixin")) {
         var config = MIAddonsConfig.getConfigAndPossiblyRegister();
         return config.overclock_rework;
      }
      return true;
   }

   @Override
   public String getRefMapperConfig() {
      return null;
   }

   @Override
   public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
   }

   @Override
   public List<String> getMixins() {
      return null;
   }

   @Override
   public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
   }

   @Override
   public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
   }
}
