package net.nki.minmagic.block.rune.entropy;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import net.nki.minmagic.MMagic;
import net.nki.minmagic.block.base.IRunetTile;
import net.nki.minmagic.block.base.container.TileRunetContainerBase;
import net.nki.minmagic.block.base.noncontainer.BlockRunetBase;
import net.nki.minmagic.block.rune.envy.TileRuneEnvy;
import net.nki.minmagic.block.rune.materialization.TileRuneMaterialization;
import net.nki.minmagic.init.MMagicBE;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.stream.IntStream;

public class TileRuneEntropy extends TileRunetContainerBase {
    public static class RuneSupplier extends BlockRunetBase.RuneEntitySupplier implements BlockEntityType.BlockEntitySupplier {
        @Override
        public BlockEntity create(BlockPos p_155268_, BlockState p_155269_) {
            return new TileRuneEntropy(p_155268_, p_155269_);
        }
    }
    private final LazyOptional<? extends IItemHandler>[] handlers = SidedInvWrapper.create(this, Direction.values());
    public TileRuneEntropy(BlockPos p_155229_, BlockState p_155230_) {
        //super(MMagicBE.RUNE_ENTROPY.get(), p_155229_, p_155230_);
        super(MMagicBE.RUNES.get("entropy").get(), p_155229_, p_155230_);
    }

    @Override
    protected Component getDefaultName() {
        return new TextComponent("rune_entropy");
    }

    @Override
    public Component getDisplayName() {
        return new TextComponent("Entropy Rune");
    }

    @Override
    protected AbstractContainerMenu createMenu(int p_58627_, Inventory p_58628_) {
        return null;
    }

    @Override
    public void runeAction() {
        Level world = this.getLevel();
        CompoundTag tag = this.getTileData();
        BlockPos boundPos = new BlockPos(tag.getInt("bindX"), tag.getInt("bindY"), tag.getInt("bindZ"));
        BlockState bl = world.getBlockState(boundPos);
        IItemHandler itemHandler = this.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).resolve().get();
        ItemStack it = itemHandler.getStackInSlot(0);

        if (it.getItem().isCorrectToolForDrops(bl) && it.getDamageValue()+1<it.getMaxDamage()) {
            Block.dropResources(world.getBlockState(boundPos), world, this.worldPosition.offset(0, 1, 0), null);
            world.destroyBlock(boundPos, false);

            if (it.hurt(1, MMagic.random, null)) {
                it.shrink(1);
                it.setDamageValue(0);
            }
        }
        //MMagic.LOGGER.info("Entropy rune running.");
    }
}
