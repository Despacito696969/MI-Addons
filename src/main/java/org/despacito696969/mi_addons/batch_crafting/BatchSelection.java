package org.despacito696969.mi_addons.batch_crafting;

import aztech.modern_industrialization.MIIdentifier;
import aztech.modern_industrialization.machines.gui.GuiComponent;
import aztech.modern_industrialization.machines.gui.MachineMenuServer;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.AbstractContainerMenu;

public class BatchSelection {
    public static final ResourceLocation BATCH_SELECTION = new MIIdentifier("batch_selection");
    public static class PacketsC2S {
        public static final ResourceLocation CHANGE_BATCH_SIZE = new MIIdentifier("change_batch_size");
        public static final ServerPlayNetworking.PlayChannelHandler ON_CHANGE_BATCH_SIZE = (ms, player, handler, buf, sender) -> {
            int syncId = buf.readInt();
            boolean clickedPlusButton = buf.readBoolean();
            ms.execute(() -> {
                AbstractContainerMenu menu = player.containerMenu;
                if (menu.containerId == syncId && menu instanceof MachineMenuServer machineMenu) {
                    try {
                        Server component = machineMenu.blockEntity.getComponent(BatchSelection.BATCH_SELECTION);
                        component.changeBatchMode(clickedPlusButton);
                    } catch (RuntimeException e) {
                        // If there isn't BATCH_SELECTION component for a given machine we just ignore the action
                    }
                }
            });
        };
        public static FriendlyByteBuf encodeChangeBatchSize(int syncId, boolean clickedPlusButton) {
            var buf = PacketByteBufs.create();
            buf.writeInt(syncId);
            buf.writeBoolean(clickedPlusButton);
            return buf;
        }
    }

    public interface BatchCrafterComponent {
        int MIAddons$getDesiredRecipeBatching();
        void MIAddons$setDesiredRecipeBatching(int batchSize);
        int MIAddons$getMaxBatch();

        void MIBatchCraftingAddon$setMaxBatch(int maxBatch);
    }

    public static class Server implements GuiComponent.Server<Integer> {
        BatchCrafterComponent crafter;

        public Server(BatchCrafterComponent crafter) {
            this.crafter = crafter;
        }

        public void changeBatchMode(boolean clickedPlusButton) {
            int currentBatch = crafter.MIAddons$getDesiredRecipeBatching();
            if (clickedPlusButton) {
                currentBatch = Math.min(currentBatch * 2, crafter.MIAddons$getMaxBatch());
            } else {
                currentBatch = Math.max(currentBatch / 2, 1);
            }
            this.crafter.MIAddons$setDesiredRecipeBatching(currentBatch);
        }

        @Override
        public Integer copyData() {
            return crafter.MIAddons$getDesiredRecipeBatching();
        }

        @Override
        public boolean needsSync(Integer cachedData) {
            return crafter.MIAddons$getDesiredRecipeBatching() != cachedData;
        }

        @Override
        public void writeInitialData(FriendlyByteBuf buf) {
            writeCurrentData(buf);
        }

        @Override
        public void writeCurrentData(FriendlyByteBuf buf) {
            buf.writeVarInt(crafter.MIAddons$getDesiredRecipeBatching());
            buf.writeVarInt(crafter.MIAddons$getMaxBatch());
        }

        @Override
        public ResourceLocation getId() { return BATCH_SELECTION; }
    }
}
