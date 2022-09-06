package net.nki.minmagic.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.block.state.BlockBehaviour;

import javax.annotation.Nullable;

public class BlockRuneStone extends Block {
    public BlockRuneStone() {
        super(BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.STONE).strength(1f, 10f));
    }
}
