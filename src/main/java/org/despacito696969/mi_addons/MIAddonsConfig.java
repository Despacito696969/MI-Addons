package org.despacito696969.mi_addons;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = MIAddonsConfig.NAME)
public class MIAddonsConfig implements ConfigData {
   @ConfigEntry.Gui.Excluded
   public static final transient String NAME = "mi_addons";

   @ConfigEntry.Category(value = "default")
   public boolean overclock_rework = false;

   @ConfigEntry.Category(value = "default")
   @Comment(value = "Overclock gain when machine is working, per second")
   public float overclock_gain = 1f;

   @ConfigEntry.Category(value = "default")
   @Comment(value = "Overclock loss when machine is not working, per second")
   public float overclock_loss = 5.0f;

   @ConfigEntry.Category(value = "steam_mining_drill")
   public int steam_drill_level = 4;

   @ConfigEntry.Category(value = "steam_mining_drill")
   public float steam_drill_speed = 4.0f;

   @ConfigEntry.Category(value = "other")
   @Comment(value = "Makes REI/EMI use more/less than 6 slots for showing multiblock material cost. Useful for custom multiblocks with more than 6 materials")
   public int rei_multiblock_slot_count = 6;

   @ConfigEntry.Gui.Excluded
   private transient static boolean registered = false;
   public static synchronized MIAddonsConfig getConfig() {
      return AutoConfig.getConfigHolder(MIAddonsConfig.class).getConfig();
   }

   public static synchronized MIAddonsConfig getConfigAndPossiblyRegister() {
      if (!registered) {
         AutoConfig.register(MIAddonsConfig.class, Toml4jConfigSerializer::new);
         registered = true;
      }
      return AutoConfig.getConfigHolder(MIAddonsConfig.class).getConfig();
   }
}
