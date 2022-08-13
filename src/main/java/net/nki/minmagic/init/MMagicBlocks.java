package net.nki.minmagic.init;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.BasicItemListing;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;
import net.nki.minmagic.MMagic;
import net.nki.minmagic.block.base.noncontainer.BlockRunetBase;
import net.nki.minmagic.block.rune.entropy.BlockRuneEntropy;
import net.nki.minmagic.block.rune.envy.BlockRuneEnvy;
import net.nki.minmagic.block.BlockRuneStone;
import net.nki.minmagic.block.rune.envy.upgrade.BlockRuneEnvyBetterOutput;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class MMagicBlocks {
    public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, MMagic.MODID);
    public static final RegistryObject<Block> RUNE_STONE = REGISTRY.register("rune_stone", () -> new BlockRuneStone());
    public static final RegistryObject<Block> RUNE_ENVY_OUTPUT = REGISTRY.register("rune_envy_output", () -> new BlockRuneEnvyBetterOutput());
    //public static final RegistryObject<Block> RUNE_ENVY = REGISTRY.register("rune_envy", () -> new BlockRuneEnvy());
    //public static final RegistryObject<Block> RUNE_ENTROPY = REGISTRY.register("rune_entropy", () -> new BlockRuneEntropy());
    //public static final RegistryObject<Block> RUNE_TEST = REGISTRY.register("rune_test", () -> new BlockRuneTest());

    public static Map<String, RegistryObject<Block>> RUNES = new HashMap<String, RegistryObject<Block>>();

    public static void init() {

    }

    @SubscribeEvent
    public static void onRegisterItems(final RegistryEvent.Register<Item> event) {
        final IForgeRegistry<Item> registry = event.getRegistry();

        REGISTRY.getEntries().stream().map(RegistryObject::get).forEach( (block) -> {
            final Item.Properties properties = new Item.Properties().tab(MMagicItems.MMagicTab.instance);
            final BlockItem blockItem = new BlockItem(block, properties);
            blockItem.setRegistryName(block.getRegistryName());
            registry.register(blockItem);
        });
    }
}
