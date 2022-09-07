package net.nki.minmagic.block.base.noncontainer;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;
import net.nki.minmagic.NykiUtil;
import net.nki.minmagic.block.base.IRunetBindable;

public class TileRunetBindable extends TileRunetBase implements IRunetBindable {
    public TileRunetBindable(String id, BlockPos pos, BlockState state) {
        super(id, pos, state);
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
