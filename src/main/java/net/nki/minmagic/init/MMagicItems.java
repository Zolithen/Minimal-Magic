package net.nki.minmagic.init;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.nki.minmagic.MMagic;
import net.nki.minmagic.item.ItemMagicWrench;
import net.nki.minmagic.item.ItemPositionBinder;

public class MMagicItems {
    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, MMagic.MODID);

    public static final RegistryObject<Item> POSITION_BINDER = REGISTRY.register("pos_binder",
            () -> new ItemPositionBinder());
    public static final RegistryObject<Item> MAGIC_WRENCH = REGISTRY.register("magic_wrench", ItemMagicWrench::new);

    public static class MMagicTab extends CreativeModeTab {

        public static final MMagicTab instance = new MMagicTab(CreativeModeTab.TABS.length, "minmagic");

        private MMagicTab(int index, String label) {
            super(index, label);
        }

        @Override
        public ItemStack makeIcon() {
            return new ItemStack(MMagicBlocks.RUNE_STONE.get());
        }
    }
}
