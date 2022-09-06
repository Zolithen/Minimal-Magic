package net.nki.minmagic.block.base.noncontainer;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.nki.minmagic.MMagic;
import net.nki.minmagic.block.BlockRuneBase;
import net.nki.minmagic.block.base.IRunetTile;
import net.nki.minmagic.init.MMagicBE;
import net.nki.minmagic.init.MMagicBlocks;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

public class BlockRunetBase extends BlockRuneBase implements EntityBlock {

    public String getRuneID() {
        return "default";
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return MMagicBE.RUNES.get(getRuneID()).get().create(pos, state);
    }

    @Nullable
    @Override
    public <A extends BlockEntity> BlockEntityTicker<A> getTicker(Level world, BlockState state, BlockEntityType<A> type) {
       return type == MMagicBE.RUNES.get(getRuneID()).get() ? this::tick : null;
    }

    private <A extends BlockEntity> void tick(Level world, BlockPos blockPos, BlockState blockState, A be) {
        if (!world.isClientSide()) { // TODO : Add possibility to add code to client side.
            IRunetTile tile = (IRunetTile) be;
            tile.setTimer(tile.getTimer()+1);
            tile.tickUpdate();
            if (tile.getTimer() > tile.getMaxTimer()) {
                tile.setTimer(0);

                tile.runeAction();
            }
        }
    }

    public static class RuneEntitySupplier implements BlockEntityType.BlockEntitySupplier {

        @Override
        public BlockEntity create(BlockPos p_155268_, BlockState p_155269_) {
            return new TileRunetBase(p_155268_, p_155269_);
        }
    }

    @Override
    public void appendHoverText(ItemStack itemstack, BlockGetter world, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
        TranslatableComponent t = new TranslatableComponent("block.minmagic.rune_" + getRuneID() +".tooltip");
        t.withStyle(ChatFormatting.DARK_GRAY);
        list.add(t);
    }
}
