package net.nki.minmagic.init;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.nki.minmagic.MMagic;
import net.nki.minmagic.block.rune.entropy.GUIMenuRuneEntropy;
import net.nki.minmagic.block.rune.entropy.GUIScreenRuneEntropy;
import net.nki.minmagic.block.rune.killer.GUIMenuRuneKiller;
import net.nki.minmagic.block.rune.killer.GUIScreenRuneKiller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MMagicGUI {

    public static void init() {

    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class Menu {
        public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.CONTAINERS,
                MMagic.MODID);
        //public static Map<String, MenuType<? extends AbstractContainerMenu>> RUNES = new HashMap<String, MenuType<? extends AbstractContainerMenu>>();
        private static final List<MenuType<?>> REGISTRY = new ArrayList<>();

        public static final RegistryObject<MenuType<GUIMenuRuneEntropy>> RUNE_ENTROPY = MENUS.register("rune_entropy_gui",
                () -> new MenuType<>(GUIMenuRuneEntropy::new));
        public static final RegistryObject<MenuType<GUIMenuRuneKiller>> RUNE_KILLER = MENUS.register("rune_killer_gui",
                () -> new MenuType<>(GUIMenuRuneKiller::new));

        public static <T extends AbstractContainerMenu> MenuType<T> register(String registryname, IContainerFactory<T> containerFactory) {
            MenuType<T> menuType = new MenuType<T>(containerFactory);
            menuType.setRegistryName(registryname);
            REGISTRY.add(menuType);
            return menuType;
        }

        @SubscribeEvent
        public static void registerContainers(RegistryEvent.Register<MenuType<?>> event) {
            event.getRegistry().registerAll(REGISTRY.toArray(new MenuType[0]));
        }

    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class Screen {

        @SubscribeEvent
        public static void clientLoad(FMLClientSetupEvent event) {
            event.enqueueWork(() -> {
                MenuScreens.register(Menu.RUNE_ENTROPY.get(), GUIScreenRuneEntropy::new);
                MenuScreens.register(Menu.RUNE_KILLER.get(), GUIScreenRuneKiller::new);
            });
        }
    }
}
