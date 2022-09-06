package net.nki.minmagic.block.rune.entropy;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import net.nki.minmagic.block.base.noncontainer.BlockRunetBase;

public class BlockRuneEntropy extends BlockRunetBase {
    // TODO : Add the BlockRunetContainerBase class
    @Override
    public String getRuneID() {
        return "entropy";
    }

    // Opens up the correct screen
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand,
                                 BlockHitResult result) {
        if (!level.isClientSide && level.getBlockEntity(pos) instanceof TileRuneEntropy) {

            final MenuProvider container = new SimpleMenuProvider(
                    GUIMenuRuneEntropy.getServerContainer((TileRuneEntropy) level.getBlockEntity(pos), pos),
                    new TextComponent("Entropy Rune")
            );
            NetworkHooks.openGui((ServerPlayer) player, container, pos);
        }

        return InteractionResult.SUCCESS;
    }

    // Drops the items from the tile's inventory
    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof TileRuneEntropy) {
                Containers.dropContents(world, pos, ((TileRuneEntropy) blockEntity).getItems());
                world.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, world, pos, newState, isMoving);
        }
    }

    // If there's an item inside, output signal. If not, then not
    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState blockState, Level world, BlockPos pos) {
        BlockEntity tileentity = world.getBlockEntity(pos);
        if (tileentity instanceof TileRuneEntropy)
            return ((TileRuneEntropy) tileentity).getItemInSlot(0) == ItemStack.EMPTY ? 0 : 15;
        else
            return 0;
    }
}
