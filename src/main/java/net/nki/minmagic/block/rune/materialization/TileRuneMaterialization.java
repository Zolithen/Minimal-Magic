package net.nki.minmagic.block.rune.materialization;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.nki.minmagic.block.base.noncontainer.BlockRunetBase;
import net.nki.minmagic.block.base.noncontainer.TileRunetBase;

import java.util.List;

public class TileRuneMaterialization extends TileRunetBase {

    public final double RANGE = 5;

    public static class RuneSupplier extends BlockRunetBase.RuneEntitySupplier implements BlockEntityType.BlockEntitySupplier {
        @Override
        public BlockEntity create(BlockPos p_155268_, BlockState p_155269_) {
            return new TileRuneMaterialization(p_155268_, p_155269_);
        }
    }

    public String getRuneID() {
        return "materialization";
    }

    @Override
    public int getMaxTimer() {
        return 40;
    }

    public TileRuneMaterialization(BlockPos pos, BlockState state) {
        super("materialization", pos, state);
    }

    public void runeAction() {
        if (this != null) {
            BlockPos p = this.getBlockPos();
            Level world = this.getLevel();
            CompoundTag tag = this.getTileData();
            BlockState bl = world.getBlockState(new BlockPos(tag.getDouble("bindX"), tag.getDouble("bindY"), tag.getDouble("bindZ")));
            if (bl.getBlock() == Blocks.AIR) {
                List<ItemEntity> list = world.getEntitiesOfClass(
                        ItemEntity.class,
                        new AABB(this.worldPosition.offset(-RANGE, -RANGE, -RANGE),
                                this.worldPosition.offset(RANGE+1, RANGE+1, RANGE+1)),
                        (p_184067_) -> {
                            return true;
                });

                for (ItemEntity i : list) {
                    ItemStack it = i.getItem();
                    if (it.getItem() instanceof BlockItem) {
                        double bx = tag.getDouble("bindX");
                        double by = tag.getDouble("bindY");
                        double bz = tag.getDouble("bindZ");
                        //world.setBlock(new BlockPos(myself.getTileData().getDouble("bindX"), myself.getTileData().getDouble("bindY"), myself.getTileData().getDouble("bindZ")));
                        Direction dir = Direction.UP;
                        BlockPlaceContext ctx = new BlockPlaceContext(world, null, InteractionHand.MAIN_HAND, it, new BlockHitResult(
                                new Vec3(
                                        bx + 0.5D + (double)dir.getStepX() * 0.5D,
                                        by + 0.5D + (double)dir.getStepY() * 0.5D,
                                        bz + 0.5D + (double)dir.getStepZ() * 0.5D
                                ), dir, new BlockPos(bx, by, bz), false));
                        ((BlockItem) it.getItem()).place(ctx);
                        //it.shrink(1);
                    }
                }
            }
        }
    }
}
