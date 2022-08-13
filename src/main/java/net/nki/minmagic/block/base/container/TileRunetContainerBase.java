package net.nki.minmagic.block.base.container;

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
import net.nki.minmagic.block.base.noncontainer.BlockRunetBase;
import net.nki.minmagic.block.base.noncontainer.TileRunetBase;
import net.nki.minmagic.block.rune.entropy.TileRuneEntropy;
import net.nki.minmagic.init.MMagicBE;

import javax.annotation.Nullable;
import java.util.stream.IntStream;

public class TileRunetContainerBase extends RandomizableContainerBlockEntity implements WorldlyContainer, IRunetTile {
    public int timer = 0;

    public int bindX = 0;
    public int bindY = -1000;
    public int bindZ = 0;

    private final LazyOptional<? extends IItemHandler>[] handlers = SidedInvWrapper.create(this, Direction.values());

    private NonNullList<ItemStack> stacks = NonNullList.<ItemStack>withSize(1, ItemStack.EMPTY);

    public String getRuneID() {
        return "default";
    }

    protected TileRunetContainerBase(BlockEntityType<?> p_155629_, BlockPos p_155630_, BlockState p_155631_) {
        super(p_155629_, p_155630_, p_155631_);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return this.saveWithFullMetadata();
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        if (!this.tryLoadLootTable(compound))
            this.stacks = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(compound, this.stacks);

        this.bindX = compound.getInt("bindX");
        this.bindY = compound.getInt("bindY");
        this.bindZ = compound.getInt("bindZ");
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);
        if (!this.trySaveLootTable(compound)) {
            ContainerHelper.saveAllItems(compound, this.stacks);
        }

        compound.putInt("bindX", this.bindX);
        compound.putInt("bindY", this.bindY);
        compound.putInt("bindZ", this.bindZ);
    }

    @Override
    protected Component getDefaultName() {
        return new TextComponent("rune_"+getRuneID());
    }

    @Override
    public Component getDisplayName() {
        return new TextComponent(getRuneID()+" Rune");
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        return IntStream.range(0, this.getContainerSize()).toArray();
    }

    @Override
    public boolean canPlaceItemThroughFace(int p_19235_, ItemStack p_19236_, @Nullable Direction p_19237_) {
        return true;
    }

    @Override
    public boolean canTakeItemThroughFace(int p_19239_, ItemStack p_19240_, Direction p_19241_) {
        return true;
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.stacks;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> p_59625_) {
        this.stacks = p_59625_;
    }

    @Override
    public int getContainerSize() {
        return stacks.size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack itemstack : this.stacks)
            if (!itemstack.isEmpty())
                return false;
        return true;
    }

    @Override
    protected AbstractContainerMenu createMenu(int p_58627_, Inventory p_58628_) {
        return null;
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
        if (!this.remove && facing != null && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return handlers[facing.ordinal()].cast();
        return super.getCapability(capability, facing);
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        for (LazyOptional<? extends IItemHandler> handler : handlers)
            handler.invalidate();
    }

    @Override
    public int getTimer() {
        return this.timer;
    }

    @Override
    public int getMaxTimer() {
        return 20;
    }

    @Override
    public void runeAction() {

    }

    @Override
    public void setTimer(int t) {
        this.timer = t;
    }

    public static class RuneSupplier extends BlockRunetBase.RuneEntitySupplier implements BlockEntityType.BlockEntitySupplier {
        @Override
        public BlockEntity create(BlockPos p_155268_, BlockState p_155269_) {
            return new TileRuneEntropy(p_155268_, p_155269_);
        }
    }
}
