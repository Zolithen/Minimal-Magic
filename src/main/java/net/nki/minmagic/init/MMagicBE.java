package net.nki.minmagic.init;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.nki.minmagic.MMagic;
import net.nki.minmagic.block.base.noncontainer.BlockRunetBase;
import net.nki.minmagic.block.rune.entropy.BlockRuneEntropy;
import net.nki.minmagic.block.rune.entropy.TileRuneEntropy;
import net.nki.minmagic.block.rune.envy.BlockRuneEnvy;
import net.nki.minmagic.block.rune.envy.TileRuneEnvy;
import net.nki.minmagic.block.rune.envy.upgrade.BlockRuneEnvyBetter;
import net.nki.minmagic.block.rune.envy.upgrade.BlockRuneEnvyBetterM;
import net.nki.minmagic.block.rune.envy.upgrade.TileRuneEnvyBetter;
import net.nki.minmagic.block.rune.envy.upgrade.TileRuneEnvyBetterM;
import net.nki.minmagic.block.rune.killer.BlockRuneKiller;
import net.nki.minmagic.block.rune.killer.TileRuneKiller;
import net.nki.minmagic.block.rune.materialization.BlockRuneMaterialization;
import net.nki.minmagic.block.rune.materialization.TileRuneMaterialization;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class MMagicBE {
    public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, MMagic.MODID);
    public static Map<String, RegistryObject<BlockEntityType<? extends BlockEntity>>> RUNES = new HashMap<String, RegistryObject<BlockEntityType<? extends BlockEntity>>>();

    /*public static final RegistryObject<BlockEntityType<TileRuneEnvy>> RUNE_ENVY = REGISTRY.register("rune_envy",
            () -> BlockEntityType.Builder.of(TileRuneEnvy::new, MMagicBlocks.RUNE_ENVY.get()).build(null));*/

    /*public static final RegistryObject<BlockEntityType<TileRuneEntropy>> RUNE_ENTROPY = REGISTRY.register("rune_entropy",
            () -> BlockEntityType.Builder.of(TileRuneEntropy::new, MMagicBlocks.RUNE_ENTROPY.get()).build(null));*/

    /*public static <T> void registerRune(String id, TileRunetBase t, BlockRunetBase b) {
        RUNES.put(id, REGISTRY.register("rune_"+id,() -> BlockEntityType.Builder.of(T::new, MMagicBlocks.RUNE_ENVY.get()).build(null)));
    }*/

    public static void init() {
        //registerRune("test", new TileRuneTest.TestRuneSupplier(), () -> new BlockRuneTest());
        registerRune("envy", new TileRuneEnvy.RuneSupplier(), () -> new BlockRuneEnvy());
        registerRune("envy_better", new TileRuneEnvyBetter.RuneSupplier(), () -> new BlockRuneEnvyBetter());
        registerRune("envy_betterm", new TileRuneEnvyBetterM.RuneSupplier(), () -> new BlockRuneEnvyBetterM());
        registerRune("materialization", new TileRuneMaterialization.RuneSupplier(), () -> new BlockRuneMaterialization());

        registerRune("entropy", new TileRuneEntropy.RuneSupplier(), () -> new BlockRuneEntropy());
        registerRune("killer", new TileRuneKiller.RuneSupplier(), () -> new BlockRuneKiller());
        //registerRune("entropy", new TileRune);

        //registerRune("test", new TileRuneTest.TestRuneSupplier(), () -> new BlockRuneTest());
    }

    public static void registerRune(String RUN_ID, BlockRunetBase.RuneEntitySupplier run_es, final Supplier<? extends BlockRunetBase> sup) {
        //MMagicBE.RUNES.put(RUNE_ID, MMagicBE.REGISTRY.register("rune_"+RUNE_ID,() -> BlockEntityType.Builder.of(() -> new RuneEntitySupplier(), MMagicBlocks.RUNES.get(RUNE_ID).get()).build(null)));
        MMagicBlocks.RUNES.put(RUN_ID, MMagicBlocks.REGISTRY.register("rune_" + RUN_ID, sup));
        MMagicBE.RUNES.put(RUN_ID, MMagicBE.REGISTRY.register("rune_"+RUN_ID,() -> BlockEntityType.Builder.of(run_es, MMagicBlocks.RUNES.get(RUN_ID).get()).build(null)));
    }

    /*public static void registerContainerRune(String RUN_ID, BlockRunetBase.RuneEntitySupplier run_es, final Supplier<? extends BlockRunetBase> sup, IContainerFactory<GUIMenuRunetBase> cf) {
        registerRune(RUN_ID, run_es, sup);
        MMagicGUI.Menu.register("rune_"+RUN_ID+"_gui", cf);
    }*/

    /*
    * Adding a custom rune.
    * 1. Create 2 classes: the block one, which extends BlockRunetBase and the tile one which extends TileRunetBase
    * 2. The block and tile class need to override the getRuneID() method to return the proper rune id.
    * 3. The tile class needs to have a constructor of the form
    *   public TileRuneEnvy(BlockPos pos, BlockState state) { super("<rune id>", pos, state);}
    * 4. Add the RuneProvider to the TileRune class (static class which implements BlockEntitySupplier and extends the original RuneEntitySupplier)
    * 5. Register on here within the init method with registerRune
    * */
}
