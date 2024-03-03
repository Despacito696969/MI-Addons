package org.despacito696969.mi_addons.batch_crafting;

import aztech.modern_industrialization.machines.BEP;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.HashMap;
import java.util.Objects;

public class BatchCraftingRegistry {
    static public HashMap<String, Integer> machines = new HashMap<>();
    static public int defaultMaxBatch = 1;

    public static int getBatchSize(BEP bep) {
        var name = Objects.requireNonNull(BlockEntityType.getKey(bep.type())).toString();
        return machines.getOrDefault(name, defaultMaxBatch);
    }
}
