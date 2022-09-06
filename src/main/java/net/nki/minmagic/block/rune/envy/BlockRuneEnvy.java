package net.nki.minmagic.block.rune.envy;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.nki.minmagic.block.base.noncontainer.BlockRunetBase;

import java.util.List;


public class BlockRuneEnvy extends BlockRunetBase {
    @Override
    public String getRuneID() {
        return "envy";
    }
}
