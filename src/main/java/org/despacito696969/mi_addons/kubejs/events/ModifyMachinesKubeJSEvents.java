package org.despacito696969.mi_addons.kubejs.events;// package org.despacito696969.mi_batch_crafting_addon.compat.kubejs;

import dev.latvian.mods.kubejs.event.EventJS;
import org.despacito696969.mi_addons.batch_crafting.BatchCraftingRegistry;

import java.util.HashMap;

public class ModifyMachinesKubeJSEvents extends EventJS {
    public HashMap<String, Integer> getMachines() {
        return BatchCraftingRegistry.machines;
    }

    public int getDefaultMaxBatch() {
        return BatchCraftingRegistry.default_max_batch;
    }

    public void setDefaultMaxBatch(int batch) {
        if (0 >= batch) {
            throw new IllegalArgumentException("Default max batch " + batch + " is below 1");
        }
        BatchCraftingRegistry.default_max_batch = batch;
    }

    public Integer add(String elem, int max_batch) {
        if (elem == null) {
            throw new IllegalArgumentException("Machine name is null");
        }
        if (0 >= max_batch) {
            throw new IllegalArgumentException("Max batch for machine " + elem + " is " + max_batch + " which is below 1");
        }
        return BatchCraftingRegistry.machines.put(elem, max_batch);
    }

    public Integer remove(String elem) {
        if (elem == null) {
            throw new IllegalArgumentException("Machine name is null");
        }
        return BatchCraftingRegistry.machines.remove(elem);
    }

    public Integer get(String elem) {
        if (elem == null) {
            throw new IllegalArgumentException("Machine name is null");
        }
        return BatchCraftingRegistry.machines.get(elem);
    }
}
