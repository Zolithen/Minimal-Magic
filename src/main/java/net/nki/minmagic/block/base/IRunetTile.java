package net.nki.minmagic.block.base;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.nki.minmagic.block.base.noncontainer.BlockRunetBase;

public interface IRunetTile {
    public int getTimer();
    public int getMaxTimer();
    public void runeAction();
    public void setTimer(int t);

    public class RuneSupplier extends BlockRunetBase.RuneEntitySupplier implements BlockEntityType.BlockEntitySupplier {};
}
