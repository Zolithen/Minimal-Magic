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
        //public static Map<String, MenuType<? extends AbstractContainerMenu>> RUNES = new HashMap<String, MenuType<? extends AbstractContainerMenu>>();
        private static final List<MenuType<?>> REGISTRY = new ArrayList<>();
        public static final MenuType<GUIMenuRuneEntropy> RUNE_ENTROPY = register("rune_entropy_gui",
                (id, inv, extraData) -> new GUIMenuRuneEntropy(id, inv, extraData));
        public static final MenuType<GUIMenuRuneKiller> RUNE_KILLER = register("rune_killer_gui",
                (id, inv, extraData) -> new GUIMenuRuneKiller(id, inv, extraData));

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

        //public static Map<String, MenuScreens.ScreenConstructor<?, ?>> RUNES = new HashMap<String, MenuScreens.ScreenConstructor<?, ?>>();
        //MenuType<? extends M> p_96207_, MenuScreens.ScreenConstructor<M, U> p_96208_

        @SubscribeEvent
        public static void clientLoad(FMLClientSetupEvent event) {
            event.enqueueWork(() -> {
                MenuScreens.register(Menu.RUNE_ENTROPY, GUIScreenRuneEntropy::new);
                MenuScreens.register(Menu.RUNE_KILLER, GUIScreenRuneKiller::new);

                //MenuScreens.register(Menu.RUNES.get("test"), GUIScreenRuneTest::new);


                //RUNES.forEach((id, cons) -> (MenuScreens.register(Menu.RUNES.get(id), cons)));
                /*for(Map.Entry<String, MenuScreens.ScreenConstructor<?, ?>> s : RUNES.entrySet()) {
                    MenuScreens.register(Menu.RUNES.get(s.getKey()), s.getValue());
                }*/
                //for (String, MenuScreens.ScreenConstructor<?, ?> )
            });
        }
    }
}
