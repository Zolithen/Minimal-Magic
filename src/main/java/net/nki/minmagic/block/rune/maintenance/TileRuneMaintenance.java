package net.nki.minmagic.block.rune.maintenance;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.nki.minmagic.MMagic;
import net.nki.minmagic.block.base.container.BlockRunetContainerBase;
import net.nki.minmagic.block.base.container.TileRunetContainerBase;
import net.nki.minmagic.block.base.noncontainer.BlockRunetBase;
import net.nki.minmagic.block.base.noncontainer.TileRunetBase;
import net.nki.minmagic.block.base.noncontainer.TileRunetBindable;
import net.nki.minmagic.block.rune.materialization.TileRuneMaterialization;

import java.util.List;

public class TileRuneMaintenance extends TileRunetBindable {
    public static class RuneSupplier extends BlockRunetBase.RuneEntitySupplier implements BlockEntityType.BlockEntitySupplier {
        @Override
        public BlockEntity create(BlockPos p_155268_, BlockState p_155269_) {
            return new TileRuneMaintenance(p_155268_, p_155269_);
        }
    }

    public String getRuneID() {
        return "maintenance";
    }

    @Override
    public int getMaxTimer() {
        return 40;
    }

    public TileRuneMaintenance(BlockPos pos, BlockState state) {
        super("maintenance", pos, state);
    }

    @Override
    public void runeAction() {
        CompoundTag tag = this.getTileData();
        Level world = this.getLevel();
        BlockPos pos = this.getBind();
        BlockState bl = world.getBlockState(pos);
        ItemStack it = ItemStack.EMPTY;
        if ((bl.getBlock() instanceof BlockRunetBase) && (world.getBlockEntity(pos) instanceof TileRunetContainerBase)) {
            TileRunetContainerBase runet = (TileRunetContainerBase) world.getBlockEntity(pos);
            LazyOptional<IItemHandler> cap = runet.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
            if (cap.isPresent()) {
                IItemHandler itemHandler = cap.resolve().get();
                for (int i = 0; i < runet.getInvSize(); i++) {
                    it = itemHandler.getStackInSlot(i);
                    if ( it.isDamageableItem() && (it.getDamageValue() + 1 >= it.getMaxDamage()) ) {
                        world.addFreshEntity(new ItemEntity(world, this.worldPosition.getX(), this.worldPosition.getY() - 1, this.worldPosition.getZ(), itemHandler.extractItem(i, 1, false)));
                    }
                }
            }
        }
    }
}
