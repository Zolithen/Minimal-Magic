package net.nki.minmagic.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.nki.minmagic.block.base.IRunetBindable;
import net.nki.minmagic.init.MMagicItems;

import java.util.List;

public class ItemPositionBinder extends Item {
    private int bindX = 0;
    private int bindY = -1000;
    private int bindZ = 0;

    public ItemPositionBinder() {
        super(new Item.Properties().tab(MMagicItems.MMagicTab.instance));
    }

    @Override
    public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
        TranslatableComponent t = new TranslatableComponent("item.minmagic.pos_binder.tooltip");
        t.withStyle(ChatFormatting.DARK_GRAY);
        list.add(t);
    }

    // TODO : Make visual effects
    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        if (!world.isClientSide()) {
            BlockPos clPos = context.getClickedPos();
            Player pl = context.getPlayer();
            ItemStack it = context.getItemInHand();

            if (pl.isShiftKeyDown()) {
                BlockEntity rune = world.getBlockEntity(clPos);

                if ((rune != null) && (rune instanceof IRunetBindable)) {
                    CompoundTag t = rune.getTileData();
                    CompoundTag itt = it.getOrCreateTag();
                    /*t.putDouble("bindX", itt.getDouble("bx"));
                    t.putDouble("bindY", itt.getDouble("by"));
                    t.putDouble("bindZ", itt.getDouble("bz"));*/
                    ((IRunetBindable) rune).setBind(new BlockPos(itt.getDouble("bx"), itt.getDouble("by"), itt.getDouble("bz")));
                    pl.displayClientMessage(new TextComponent(
                            ("Bound to " + itt.getDouble("bx") + "," + itt.getDouble("by") + "," + itt.getDouble("bz"))), false);
                }
            } else {
                it.getOrCreateTag().putDouble("bx", clPos.getX());
                it.getOrCreateTag().putDouble("by", clPos.getY());
                it.getOrCreateTag().putDouble("bz", clPos.getZ());
                pl.displayClientMessage(new TextComponent(("Saved position " + clPos.getX() + "," + clPos.getY() + "," + clPos.getZ())), false);
            }
        }
        return InteractionResult.PASS;
    }
}
