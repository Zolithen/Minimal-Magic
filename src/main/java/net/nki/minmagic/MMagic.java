package net.nki.minmagic;

import com.mojang.logging.LogUtils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.nki.minmagic.init.MMagicBE;
import net.nki.minmagic.init.MMagicBlocks;
import net.nki.minmagic.init.MMagicGUI;
import net.nki.minmagic.init.MMagicItems;
import org.slf4j.Logger;

import java.util.Random;
import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("minmagic")
public class MMagic
{
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final String MODID = "minmagic";
    public static Random random = new Random();

    public MMagic()
    {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        //MMagicItems.init();

        MMagicBlocks.init();
        MMagicBE.init();
        MMagicGUI.init();
        MMagicBlocks.REGISTRY.register(modEventBus);
        MMagicItems.REGISTRY.register(modEventBus);
        MMagicBE.REGISTRY.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
    }
}
