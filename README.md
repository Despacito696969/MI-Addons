This mod adds:
# Batch Crafting Component
You can add to every machine a component which allows you to bypass 1 recipe per tick, by scalling inputs, outputs and energy of a recipe by x2, x4, x8, and so on.

For now to use it, you need to configure it using KubeJS, example (.minecraft/kubejs/startup_scripts/example.js):
```js
MIAddonsBatchCrafting.modify(e => {
    let mi = (id) => `modern_industrialization:${id}`
    e.setDefaultMaxBatch(64) // By default max batch is set to 1, which disables the component
    e.add(mi("electric_blast_furnace"), 256)
    e.add(mi("electric_compressor"), 64)
    e.add(mi("electric_macerator"), 4)
    e.add(mi("electric_quarry"), 1)
})
```

There are also other functions which you can find at `src/main/java/org/despacito696969/mi_addons/kubejs/events/ModifyMachinesKubeJSEvents.java` if you need.
# Overclock Rework
Might get removed in the future, since MI devs plan on changing the overclock into something that behaves like this rework.

By default you need to enable it in config file.

Makes so that overclock increases with time instead of crafted recipes, which makes machine speed increase exponentially and not super exponentially.
This makes longer recipes way easier to overclock and shorter ones harder.

# Steam Drill Mining Stats
You can modify mining drill's mining level and mining speed using config.
