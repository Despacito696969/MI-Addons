package org.despacito696969.mi_addons.kubejs;

import dev.latvian.mods.kubejs.KubeJSPlugin;
import org.despacito696969.mi_addons.kubejs.events.MIBatchCraftingKubeJSEvents;

public class MIAddonKubeJSPlugin extends KubeJSPlugin {
    @Override
    public void registerEvents() {
        MIBatchCraftingKubeJSEvents.EVENT_GROUP.register();
    }

    @Override
    public void initStartup() {
        KubeJSProxy.instance = new LoadedKubeJSProxy();
        KubeJSProxy.instance.fireBatchCraftingModifyMachines();
    }
}
