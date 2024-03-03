package org.despacito696969.mi_addons;

import net.fabricmc.api.ModInitializer;

public class MIAddons implements ModInitializer {
    @Override
    public void onInitialize() {
        MIAddonsConfig.getConfigAndPossiblyRegister();
    }
}
