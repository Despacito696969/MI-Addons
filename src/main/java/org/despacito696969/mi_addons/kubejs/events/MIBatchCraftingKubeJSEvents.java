package org.despacito696969.mi_addons.kubejs.events;// package org.despacito696969.mi_batch_crafting_addon.compat.kubejs;


import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;

public interface MIBatchCraftingKubeJSEvents {
    EventGroup EVENT_GROUP = EventGroup.of("MIAddonsBatchCrafting");

    EventHandler MODIFY = EVENT_GROUP.startup("modify", () -> ModifyMachinesKubeJSEvents.class);
}
