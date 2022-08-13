package net.nki.minmagic.init;

import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.BasicItemListing;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MMagicTrades {
    @SubscribeEvent
    public static void registerTrades(VillagerTradesEvent event) {
        if (event.getType() == VillagerProfession.MASON) {
            event.getTrades().get(1).add(
                    new BasicItemListing(new ItemStack(Items.EMERALD, 11), new ItemStack(MMagicBlocks.RUNE_STONE.get(), 6), 10, 5, 0.05f)
            );
        }
    }
}
