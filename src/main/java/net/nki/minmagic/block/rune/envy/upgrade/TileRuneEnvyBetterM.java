package net.nki.minmagic.block.rune.envy.upgrade;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.nki.minmagic.block.base.IRunetTile;
import net.nki.minmagic.block.base.noncontainer.BlockRunetBase;
import net.nki.minmagic.block.base.noncontainer.TileRunetBase;
import net.nki.minmagic.init.MMagicBlocks;

import java.util.List;

public class TileRuneEnvyBetterM extends TileRunetBase implements IRunetTile {

    public static final int RANGE = 5;

    public static class RuneSupplier extends BlockRunetBase.RuneEntitySupplier implements BlockEntityType.BlockEntitySupplier {
        @Override
        public BlockEntity create(BlockPos p_155268_, BlockState p_155269_) {
            return new TileRuneEnvyBetterM(p_155268_, p_155269_);
        }
    }

    public String getRuneID() {
        return "envy_betterm";
    }

    public TileRuneEnvyBetterM(BlockPos pos, BlockState state) {
        super("envy_betterm", pos, state);
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
                new AABB(this.worldPosition.offset(0, -1, 0)),
                (p_184067_) -> {
                    return true;
                }
        );

        if (!list.isEmpty()) {
            CompoundTag tag = this.getTileData();
            int bx = tag.getInt("bindX");
            int by = tag.getInt("bindY");
            int bz = tag.getInt("bindZ");

            if (this.getLevel().getBlockState(new BlockPos(bx, by, bz)).getBlock().equals(MMagicBlocks.RUNE_ENVY_OUTPUT.get()) ) {
                for (ItemEntity i : list) {
                    i.setPos(bx+0.5, by-1, bz+0.5);
                }
            }

        }
    }
}
