package net.nki.minmagic.block.base.container;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.nki.minmagic.NykiUtil;
import net.nki.minmagic.block.base.IRunetBindable;

public class TileRunetContainerBindable extends TileRunetContainerBase implements IRunetBindable {
    protected TileRunetContainerBindable(BlockEntityType<?> p_155629_, BlockPos p_155630_, BlockState p_155631_, Integer invSize) {
        super(p_155629_, p_155630_, p_155631_, invSize);
    }

    private int bindX = 0;
    private int bindY = -1000;
    private int bindZ = 0;

    @Override
    public BlockPos getBind() {
        return new BlockPos(bindX, bindY, bindZ);
    }

    @Override
    public void setBind(BlockPos pos) {
        bindX = pos.getX();
        bindY = pos.getY();
        bindZ = pos.getZ();
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);

        NykiUtil.loadBind(this, compound);
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);

        NykiUtil.saveBind(this, compound);
    }
}
