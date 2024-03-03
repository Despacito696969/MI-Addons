package org.despacito696969.mi_addons.kubejs;

import org.despacito696969.mi_addons.kubejs.events.MIBatchCraftingKubeJSEvents;
import org.despacito696969.mi_addons.kubejs.events.ModifyMachinesKubeJSEvents;

public class LoadedKubeJSProxy extends KubeJSProxy {
    @Override
    public void fireBatchCraftingModifyMachines() {
        MIBatchCraftingKubeJSEvents.MODIFY.post(new ModifyMachinesKubeJSEvents());
    }
}
