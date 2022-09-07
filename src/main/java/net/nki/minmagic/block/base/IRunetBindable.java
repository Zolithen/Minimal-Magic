package net.nki.minmagic.block.base;

import net.minecraft.core.BlockPos;

public interface IRunetBindable {
    BlockPos getBind();
    void setBind(BlockPos pos);
}
