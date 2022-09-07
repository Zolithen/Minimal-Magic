package net.nki.minmagic;

import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.nki.minmagic.init.*;
import org.slf4j.Logger;

import java.util.Random;

@Mod("minmagic")
public class MMagic
{
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final String MODID = "minmagic";
    public static Random random = new Random();
    public static MMagicData DATA = new MMagicData();

    public MMagic()
    {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        //MMagicItems.init();

        MMagicBlocks.init();
        MMagicBE.init();
        MMagicGUI.init();
        MMagicGUI.Menu.MENUS.register(modEventBus);
        MMagicBlocks.REGISTRY.register(modEventBus);
        MMagicItems.REGISTRY.register(modEventBus);
        MMagicBE.REGISTRY.register(modEventBus);
        modEventBus.addListener(MMagicClient::init);

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(this.DATA);
        //MinecraftForge.EVENT_BUS.addListener(MMagicClientRender::init);
    }
}
