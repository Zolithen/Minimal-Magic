package net.nki.minmagic.block.rune.bindchanger;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.nki.minmagic.block.base.container.TileRunetContainerBase;
import net.nki.minmagic.block.base.noncontainer.BlockRunetBase;
import net.nki.minmagic.block.base.noncontainer.TileRunetBase;
import net.nki.minmagic.block.rune.entropy.TileRuneEntropy;
import net.nki.minmagic.init.MMagicBE;

public class TileRuneBind extends TileRunetBase {

    public TileRuneBind(BlockPos pos, BlockState state) {
        super("bind", pos, state);
    }

    public static class RuneSupplier extends BlockRunetBase.RuneEntitySupplier implements BlockEntityType.BlockEntitySupplier {
        @Override
        public BlockEntity create(BlockPos p_155268_, BlockState p_155269_) {
            return new TileRuneBind(p_155268_, p_155269_);
        }
    }
}
