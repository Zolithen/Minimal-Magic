package net.nki.minmagic.item;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.nki.minmagic.MMagic;
import net.nki.minmagic.MMagicClient;
import net.nki.minmagic.NykiUtil;
import net.nki.minmagic.block.base.IRunetBindable;
import net.nki.minmagic.block.base.IRunetTile;
import net.nki.minmagic.client.BindingRenderer;
import net.nki.minmagic.init.MMagicItems;

public class ItemMagicWrench extends Item {
    public ItemMagicWrench() {
        super(new Item.Properties().tab(MMagicItems.MMagicTab.instance));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        if (world.isClientSide()) {
            BlockEntity be = world.getBlockEntity(context.getClickedPos());
            if (be != null && be instanceof IRunetBindable) {
                MMagicClient.BINDING_RENDERER.pos1 = NykiUtil.vecFromBlockPos(context.getClickedPos());
                MMagicClient.BINDING_RENDERER.pos2 = NykiUtil.vecFromBlockPos(((IRunetBindable) be).getBind());
            } else {
                MMagicClient.BINDING_RENDERER.pos1 = null;
                MMagicClient.BINDING_RENDERER.pos2 = null;
            }
        }
        return InteractionResult.SUCCESS;
    }
}
