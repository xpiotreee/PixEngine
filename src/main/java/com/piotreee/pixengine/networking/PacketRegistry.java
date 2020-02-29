package com.piotreee.pixengine.networking;

import java.util.HashMap;

public class PacketRegistry {
    private static final HashMap<Integer, Class<? extends Packet>> packets = new HashMap<>();
    static {
        packets.put(-1, Packet.class);
    }

    public static void registerPacket(int id, Class<? extends Packet> packetClass) {
        packets.put(id, packetClass);
    }

    public static Class<? extends Packet> getPacketClass(int id) {
        return packets.get(id);
    }
}
