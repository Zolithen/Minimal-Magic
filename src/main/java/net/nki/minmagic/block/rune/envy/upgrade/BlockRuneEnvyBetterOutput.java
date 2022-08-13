package net.nki.minmagic.block.rune.envy.upgrade;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

public class BlockRuneEnvyBetterOutput extends Block {
    public BlockRuneEnvyBetterOutput() {
        super(BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.STONE).strength(1f, 10f));
    }
}
