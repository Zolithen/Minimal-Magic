package net.nki.minmagic.networking;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import net.nki.minmagic.MMagicClient;
import net.nki.minmagic.NykiUtil;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public class CbGiveRuneBindingPacket {
    public CompoundTag tag;
    public CbGiveRuneBindingPacket(BlockPos poss1, BlockPos poss2) {
        tag = new CompoundTag();
        NykiUtil.savePosToTag(poss1, tag, "p1");
        NykiUtil.savePosToTag(poss2, tag, "p2");
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeNbt(this.tag);
    }

    public CbGiveRuneBindingPacket(FriendlyByteBuf buffer) {
        tag = buffer.readAnySizeNbt();
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> MMagicClient.BINDING_RENDERER.updatePos(
                    NykiUtil.loadPosFromTag(this.tag, "p1"),
                    NykiUtil.loadPosFromTag(this.tag, "p2")
            ));
            /*MMagicClient.BINDING_RENDERER.pos1 = NykiUtil.vecFromBlockPos();
            MMagicClient.BINDING_RENDERER.pos2 = NykiUtil.vecFromBlockPos();*/
        });
        ctx.get().setPacketHandled(true);
    }
}
