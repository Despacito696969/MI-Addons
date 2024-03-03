package org.despacito696969.mi_addons.batch_crafting;

import aztech.modern_industrialization.machines.BEP;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.HashMap;

public class BatchCraftingRegistry {
    static public HashMap<String, Integer> machines = new HashMap<>();
    static public int default_max_batch = 64;

    public static int getBatchSize(BEP bep) {
        return machines.getOrDefault(BlockEntityType.getKey(bep.type()).toString(), default_max_batch);
    }
}
