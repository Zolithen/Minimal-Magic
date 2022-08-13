package net.nki.minmagic.block.rune.materialization;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.nki.minmagic.block.base.noncontainer.BlockRunetBase;

import java.util.List;

public class BlockRuneMaterialization extends BlockRunetBase {
    @Override
    public String getRuneID() {
        return "materialization";
    }

    @Override
    public void appendHoverText(ItemStack itemstack, BlockGetter world, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
        list.add(new TranslatableComponent("block.minmagic.rune_materialization.tooltip"));
    }
}
