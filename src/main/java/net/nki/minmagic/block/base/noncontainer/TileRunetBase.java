package net.nki.minmagic.block.base.noncontainer;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.nki.minmagic.MMagic;
import net.nki.minmagic.block.base.IRunetBase;
import net.nki.minmagic.block.base.IRunetTile;
import net.nki.minmagic.init.MMagicBE;

public class TileRunetBase extends BlockEntity implements IRunetTile {

    private final int RANGE = 3;
    public int timer = 0;

    public String getRuneID() {
        return "default";
    }

    public TileRunetBase(BlockPos pos, BlockState state) {
        super(MMagicBE.RUNES.get("default").get(), pos, state);
    }

    public TileRunetBase(String runid, BlockPos pos, BlockState state) {
        super(MMagicBE.RUNES.get(runid).get(), pos, state);
    }

    @Override
    public int getTimer() {
        return this.timer;
    }

    @Override
    public int getMaxTimer() {
        return 20;
    }

    public void runeAction() {
        MMagic.LOGGER.debug("Running tile entity.");
    }

    @Override
    public void setTimer(int t) {
        this.timer = t;
    }

    @Override
    public void tickUpdate() {}
}
