package net.nki.minmagic.block.rune.envy;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.nki.minmagic.block.base.IRunetTile;
import net.nki.minmagic.block.base.noncontainer.BlockRunetBase;
import net.nki.minmagic.block.base.noncontainer.TileRunetBase;

import java.util.List;

public class TileRuneEnvy extends TileRunetBase implements IRunetTile {

    public static final int RANGE = 3;

    public static class RuneSupplier extends BlockRunetBase.RuneEntitySupplier implements BlockEntityType.BlockEntitySupplier {
        @Override
        public BlockEntity create(BlockPos p_155268_, BlockState p_155269_) {
            return new TileRuneEnvy(p_155268_, p_155269_);
        }
    }

    public String getRuneID() {
        return "envy";
    }

    public TileRuneEnvy(BlockPos pos, BlockState state) {
        super("envy", pos, state);
    }

    @Override
    public int getMaxTimer() {
        return 20;
    }

    @Override
    public void runeAction() {
        int x = this.worldPosition.getX();
        int y = this.worldPosition.getY();
        int z = this.worldPosition.getZ();

        List<ItemEntity> list = this.level.getEntitiesOfClass(ItemEntity.class,
                new AABB(this.worldPosition.offset(RANGE+1, RANGE+1, RANGE+1), this.worldPosition.offset(-RANGE, -RANGE, -RANGE)),
                (p_184067_) -> {
                    return true;
                }
        );

        if (!list.isEmpty()) {
            for (ItemEntity i : list) {
                i.setPos(x+0.5, y-1, z+0.5);
            }
        }
    }
}
