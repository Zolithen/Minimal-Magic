package net.nki.minmagic.block.rune.killer;

import io.netty.buffer.Unpooled;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import net.nki.minmagic.block.BlockRuneBase;
import net.nki.minmagic.block.base.noncontainer.BlockRunetBase;
import net.nki.minmagic.block.rune.entropy.GUIMenuRuneEntropy;
import net.nki.minmagic.block.rune.entropy.TileRuneEntropy;
import net.nki.minmagic.block.rune.envy.TileRuneEnvy;
import net.nki.minmagic.init.MMagicBE;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class BlockRuneKiller extends BlockRunetBase {
    @Override
    public String getRuneID() {
        return "killer";
    }

    // Copy paste code
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand,
                                 BlockHitResult result) {
        if (!level.isClientSide && level.getBlockEntity(pos) instanceof TileRuneKiller) {

            final MenuProvider container = new SimpleMenuProvider(
                    GUIMenuRuneKiller.getServerContainer((TileRuneKiller) level.getBlockEntity(pos), pos),
                    new TextComponent("Killer Rune") // TODO : Put this into the TileRunetContainerBase and use a translatable component
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
            if (blockEntity instanceof TileRuneKiller) {
                Containers.dropContents(world, pos, ((TileRuneKiller) blockEntity).getItems());
                world.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, world, pos, newState, isMoving);
        }
    }
}
