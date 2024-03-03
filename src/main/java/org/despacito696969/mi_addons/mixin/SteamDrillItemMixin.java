package org.despacito696969.mi_addons.mixin;

import aztech.modern_industrialization.items.SteamDrillItem;
import org.despacito696969.mi_addons.MIAddonsConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(SteamDrillItem.class)
public class SteamDrillItemMixin {
    @ModifyConstant(method = "isSuitableFor", constant = @Constant(intValue = 4))
    private int modifyMiningLevel(int miningLevel) {
        return MIAddonsConfig.getConfig().steam_drill_level;
    }

    @ModifyConstant(method = "getDestroySpeed", constant = @Constant(floatValue = 4))
    private float modifyMiningSpeed(float miningLevel) {
        return MIAddonsConfig.getConfig().steam_drill_speed;
    }
}
