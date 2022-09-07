package net.nki.minmagic.block.base.container;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.nki.minmagic.NykiUtil;
import net.nki.minmagic.block.base.IRunetTile;
import net.nki.minmagic.block.base.noncontainer.BlockRunetBase;
import net.nki.minmagic.block.rune.entropy.TileRuneEntropy;

public class TileRunetContainerBase extends BlockEntity implements IRunetTile {
    public int timer = 0;

    /*public int bindX = 0;
    public int bindY = -1000;
    public int bindZ = 0;*/

    public boolean requiresUpdate;

    private int invSize = 1;
    public final ItemStackHandler inventory;
    protected LazyOptional<ItemStackHandler> handler;

    public String getRuneID() {
        return "default";
    }

    protected TileRunetContainerBase(BlockEntityType<?> p_155629_, BlockPos p_155630_, BlockState p_155631_, Integer invSize) {
        super(p_155629_, p_155630_, p_155631_);
        this.invSize = (Integer) NykiUtil.thisOrDefault(invSize, 1);
        this.inventory = new ItemStackHandler(this.invSize);
        this.handler = LazyOptional.of(() -> this.inventory);
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        this.inventory.deserializeNBT(compound.getCompound("inventory"));

        /*this.bindX = compound.getInt("bindX");
        this.bindY = compound.getInt("bindY");
        this.bindZ = compound.getInt("bindZ");*/
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);
        compound.put("inventory", this.inventory.serializeNBT());

        /*compound.putInt("bindX", this.bindX);
        compound.putInt("bindY", this.bindY);
        compound.putInt("bindZ", this.bindZ);*/
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
    public void runeAction() {}

    @Override
    public void setTimer(int t) {
        this.timer = t;
    }

    /*@Override
    public BlockPos getBind() {
        return new BlockPos(bindX, bindY, bindZ);
    }*/

    @Override
    public void tickUpdate() {
        if (this.requiresUpdate && this.level != null) {
            this.update();
            this.requiresUpdate = false;
        }
    }

    public static class RuneSupplier extends BlockRunetBase.RuneEntitySupplier implements BlockEntityType.BlockEntitySupplier {
        @Override
        public BlockEntity create(BlockPos p_155268_, BlockState p_155269_) {
            return new TileRuneEntropy(p_155268_, p_155269_);
        }
    }

    public void update() {
        requestModelDataUpdate();
        setChanged();
        if (this.level != null) {
            this.level.setBlockAndUpdate(this.worldPosition, getBlockState());
        }
    }

    public int getInvSize() {
        return this.invSize;
    }

    public ItemStack extractItem(int slot) {
        final int count = getItemInSlot(slot).getCount();
        this.requiresUpdate = true;
        return this.handler.map(inv -> inv.extractItem(slot, count, false)).orElse(ItemStack.EMPTY);
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        return cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? this.handler.cast()
                : super.getCapability(cap, side);
    }

    public NonNullList<ItemStack> getItems() {
        NonNullList<ItemStack> items = NonNullList.createWithCapacity(invSize);
        for (int i = 0; i < invSize; i++) {
            items.add(getItemInSlot(i));
        }
        return items;
    }

    public LazyOptional<ItemStackHandler> getHandler() {
        return this.handler;
    }

    public ItemStack getItemInSlot(int slot) {
        return this.handler.map(inv -> inv.getStackInSlot(slot)).orElse(ItemStack.EMPTY);
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return serializeNBT();
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        super.handleUpdateTag(tag);
        load(tag);
    }

    public ItemStack insertItem(int slot, ItemStack stack) {
        final ItemStack copy = stack.copy();
        stack.shrink(copy.getCount());
        this.requiresUpdate = true;
        return this.handler.map(inv -> inv.insertItem(slot, copy, false)).orElse(ItemStack.EMPTY);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        this.handler.invalidate();
    }

    private ItemStackHandler createInventory() {
        return new ItemStackHandler(this.invSize) {
            @Override
            public ItemStack extractItem(int slot, int amount, boolean simulate) {
                TileRunetContainerBase.this.update();
                return super.extractItem(slot, amount, simulate);
            }

            @Override
            public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
                TileRunetContainerBase.this.update();
                return super.insertItem(slot, stack, simulate);
            }
        };
    }
}
