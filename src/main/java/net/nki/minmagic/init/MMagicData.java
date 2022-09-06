package net.nki.minmagic.init;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.ChunkPos;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import java.util.HashMap;
import java.util.Map;

public class MMagicData {

    //public int VERSION = 1; // Represents the version of the data stored.
    public Map<ChunkPos, Float> energy = new HashMap<ChunkPos, Float>();
    public Map<ChunkPos, Boolean> status = new HashMap<ChunkPos, Boolean>();
    public final float TRANSFER_RATE = 0.1f;

    @SubscribeEvent
    public void chunkSave(ChunkDataEvent.Save event) {
        if (event.getWorld() == null) return;
        if (event.getWorld().isClientSide()) return;
        CompoundTag nbt = event.getData();
        nbt.putFloat("MMagic_Energy", getEnergySafely(event.getChunk().getPos()));
        nbt.putBoolean("MMagic_EnStatus", getStatusSafely(event.getChunk().getPos()));
    }

    @SubscribeEvent
    public void chunkLoad(ChunkDataEvent.Load event) {
        if (event.getWorld() == null) return;
        if (event.getWorld().isClientSide()) return;
        CompoundTag nbt = event.getData();
        energy.put(event.getChunk().getPos(), nbt.contains("MMagic_Energy") ? nbt.getFloat("MMagic_Energy") : 0f);
        status.put(event.getChunk().getPos(), nbt.contains("MMagic_EnStatus") && nbt.getBoolean("MMagic_EnStatus"));
    }

    @SubscribeEvent
    public void chunkUnload(ChunkEvent.Unload event) {
        if (event.getWorld() == null) return;
        if (event.getWorld().isClientSide()) return;
        energy.remove(event.getChunk().getPos());
        status.remove(event.getChunk().getPos());
    }

    public float getEnergySafely(ChunkPos pos) {
        Float en = energy.get(pos);
        if (en == null) return 0;
        return en;
    }

    public boolean getStatusSafely(ChunkPos pos) {
        Boolean status = this.status.get(pos);
        if (status == null) return false;
        return status;
    }

    public void spreadEnergy(ChunkPos pos) {
        for (short i = 1; i <= 10; i++) {
            if ((i!=3) && (i!=7)) {
                ChunkPos receptor = getChunkAround(pos.x, pos.z, i);
                if (!getStatusSafely(receptor)) {
                    float emitterA = getEnergySafely(pos);
                    float receptorA = getEnergySafely(receptor);
                    if (emitterA < TRANSFER_RATE) return;
                    energy.put(receptor, receptorA + TRANSFER_RATE);
                    energy.put(pos, emitterA - TRANSFER_RATE);
                }
            }
        }
    }

    public ChunkPos getChunkAround(int x, int z, short gr) {
        switch (gr) {
            case GRID_FACING.DOWN:
                return new ChunkPos(x, z-1);
            case GRID_FACING.UP:
                return new ChunkPos(x, z+1);
            case GRID_FACING.RIGHT:
                return new ChunkPos(x+1, z);
            case GRID_FACING.LEFT:
                return new ChunkPos(x-1, z);
            case GRID_FACING.RIGHT | GRID_FACING.UP:
                return new ChunkPos(x+1, z+1);
            case GRID_FACING.RIGHT | GRID_FACING.DOWN:
                return new ChunkPos(x+1, z-1);
            case GRID_FACING.LEFT | GRID_FACING.UP:
                return new ChunkPos(x-1, z+1);
            case GRID_FACING.LEFT | GRID_FACING.DOWN:
                return new ChunkPos(x-1, z-1);
            default:
                return new ChunkPos(x, z);
        }
    }

    public class GRID_FACING {
        public static final short UP = 1;
        public static final short DOWN = 2;
        public static final short LEFT = 4;
        public static final short RIGHT = 8;
    }

    /*public void doSave(CompoundTag nbt, ChunkPos pos) {
        Float en = energy.get(pos);
        if (en != null) {
            nbt.putFloat("energy", en);
        }
    }*/
}
