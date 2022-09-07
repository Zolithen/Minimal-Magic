package net.nki.minmagic;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.phys.Vec3;
import net.nki.minmagic.block.base.IRunetBindable;

public class NykiUtil {
    public static Object thisOrDefault(Object a, Object def) {
        if (a==null) {
            return def;
        }
        return a;
    }

    public static Vec3 vecFromBlockPos(BlockPos pos) {
        return new Vec3(pos.getX(), pos.getY(), pos.getZ());
    }

    public static void saveBind(IRunetBindable rune, CompoundTag tag) {
        BlockPos pos = rune.getBind();
        tag.putInt("bindX", pos.getX());
        tag.putInt("bindY", pos.getY());
        tag.putInt("bindZ", pos.getZ());
    }

    public static void loadBind(IRunetBindable rune, CompoundTag tag) {
        rune.setBind(new BlockPos(tag.getInt("bindX"), tag.getInt("bindY"), tag.getInt("bindZ")));
    }

    public static void savePosToTag(BlockPos pos, CompoundTag tag, String name) {
        tag.putInt(name + "x", pos.getX());
        tag.putInt(name + "y", pos.getY());
        tag.putInt(name + "z", pos.getZ());
    }

    public static BlockPos loadPosFromTag(CompoundTag tag, String name) {
        return new BlockPos(tag.getInt(name + "x"), tag.getInt(name + "y"), tag.getInt(name + "z"));
    }
}
