package com.piotreee.pixengine.networking;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClientHandler extends ChannelInboundHandlerAdapter {
    private List<PacketListener> packetListeners;
    private int packetListenersSize = 0;

    public ClientHandler() {
        this.packetListeners = new ArrayList<>();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < packetListenersSize; i++) {
            packetListeners.get(i).active(ctx);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof byte[]) {
            byte[] bytes = (byte[]) msg;
            Packet packet = new Packet(bytes);
            for (int i = 0; i < packetListenersSize; i++) {
                Class<? extends Packet> packetClass = Network.packetRegistry.get(packet.getId());
                PacketListener packetListener = packetListeners.get(i);
                if (packetListener.getType().isAssignableFrom(packetClass)) {
                    packetListener.on(ctx, packetClass.getConstructor(byte[].class).newInstance(bytes));
                }
            }
        }

        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    public void addListeners(PacketListener... packetListeners) {
        packetListenersSize += packetListeners.length;
        this.packetListeners.addAll(Arrays.asList(packetListeners));
    }
}
