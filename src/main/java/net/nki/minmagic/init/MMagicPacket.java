package net.nki.minmagic.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import net.nki.minmagic.MMagic;
import net.nki.minmagic.networking.CbGiveRuneBindingPacket;

public final class MMagicPacket {
    private MMagicPacket() {

    }
    private static final String PROTOCOL_VER = "1";
    public static SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(MMagic.MODID, "channel"),
            () -> PROTOCOL_VER,
            PROTOCOL_VER::equals,
            PROTOCOL_VER::equals);

    public static void init() {
        int index = 0;
        // Packets to client
        CHANNEL.messageBuilder(CbGiveRuneBindingPacket.class, index++, NetworkDirection.PLAY_TO_CLIENT)
                .encoder(CbGiveRuneBindingPacket::encode)
                .decoder(CbGiveRuneBindingPacket::new)
                .consumer(CbGiveRuneBindingPacket::handle)
                .add();
    }
}
