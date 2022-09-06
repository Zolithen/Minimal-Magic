package net.nki.minmagic.block.rune.entropy;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.nki.minmagic.MMagic;
import net.nki.minmagic.block.base.container.TileRunetContainerBase;
import net.nki.minmagic.block.base.noncontainer.BlockRunetBase;
import net.nki.minmagic.init.MMagicBE;

public class TileRuneEntropy extends TileRunetContainerBase {
    public static class RuneSupplier extends BlockRunetBase.RuneEntitySupplier implements BlockEntityType.BlockEntitySupplier {
        @Override
        public BlockEntity create(BlockPos p_155268_, BlockState p_155269_) {
            return new TileRuneEntropy(p_155268_, p_155269_);
        }
    }

    public TileRuneEntropy(BlockPos p_155229_, BlockState p_155230_) {
        super(MMagicBE.RUNES.get("entropy").get(), p_155229_, p_155230_, 1);
    }

    @Override
    public void runeAction() {
        Level world = this.getLevel();
        CompoundTag tag = this.getTileData();
        BlockPos boundPos = new BlockPos(tag.getInt("bindX"), tag.getInt("bindY"), tag.getInt("bindZ"));
        BlockState bl = world.getBlockState(boundPos);
        IItemHandler itemHandler = this.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).resolve().get();
        ItemStack it = itemHandler.getStackInSlot(0);

        // TODO: Make it work with fortune
        if (it.getItem().isCorrectToolForDrops(bl) && it.getDamageValue()+1<it.getMaxDamage()) {
            Block.dropResources(world.getBlockState(boundPos), world, this.worldPosition.offset(0, 1, 0), null);
            world.destroyBlock(boundPos, false);

            if (it.hurt(1, MMagic.random, null)) {
                it.shrink(1);
                it.setDamageValue(0);
            }
        }
    }
}
