package net.nki.minmagic.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import net.nki.minmagic.MMagic;

public class MMagicPacket {
    private static final String PROTOCOL_VER = "1";
    public static SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(MMagic.MODID, "channel"),
            () -> PROTOCOL_VER,
            PROTOCOL_VER::equals,
            PROTOCOL_VER::equals);

    public static void init() {
        int index = 0;
        //CHANNEL.messageBuilder(PacketExample1, index++, NetworkDirection.PLAY_TO_CLIENT) //Server to client
        //CHANNEL.messageBuilder(PacketExample2, index++, NetworkDirection.PLAY_TO_SERVER) //Client to server
        //    .encode(PacketExample2::encode)
        //    .add();
    }
}
