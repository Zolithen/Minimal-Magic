package net.nki.minmagic.client;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.nki.minmagic.NykiUtil;

public class BindingRenderer {
    // Maybe we'd set this with a packet?
    public Vec3 pos1;
    public Vec3 pos2;

    public void renderBinding(VertexConsumer builder, Matrix4f matrix) {
        if ((this.pos1 != null) && (this.pos2 != null) && (this.pos2.y() != -1000)) {
            RenderHelper.renderLineBetweenBlocks(builder, matrix, this.pos1, this.pos2, RenderHelper.RED);
        }
    }

    public void updatePos(BlockPos poss1, BlockPos poss2) {
        this.pos1 = NykiUtil.vecFromBlockPos(poss1);
        this.pos2 = NykiUtil.vecFromBlockPos(poss2);
    }
}
