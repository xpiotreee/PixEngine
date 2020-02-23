package com.piotreee.pixengine.networking;

import com.google.gson.Gson;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClientHandler extends ChannelInboundHandlerAdapter {
    private List<PacketListener> packetListeners;
    private int packetListenersSize = 0;
    private Gson gson = new Gson();

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
        if (msg instanceof String) {
            for (int i = 0; i < packetListenersSize; i++) {
                Packet packet = gson.fromJson((String) msg, Packet.class);
                PacketListener packetListener = packetListeners.get(i);
                if (packetListener.getType().isAssignableFrom(packet.getType())) {
                    packetListener.on(ctx, packet.getObject());
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
