package com.piotreee.pixengine.networking;

import com.google.gson.Gson;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ChannelHandler.Sharable
public class ServerHandler extends ChannelInboundHandlerAdapter {
    private List<PacketListener> packetListeners = new ArrayList<>();
    private int packetListenersSize = 0;
    private ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private Gson gson = new Gson();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < packetListenersSize; i++) {
            packetListeners.get(i).active(ctx);
        }

        channels.add(ctx.channel());
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
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
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    public void sendAll(Object object) {
        String msg = gson.toJson(new Packet(object.getClass(), object));
        for (Channel channel : channels) {
            channel.writeAndFlush(msg);
        }
    }

    public void addListeners(PacketListener... packetListeners) {
        packetListenersSize += packetListeners.length;
        this.packetListeners.addAll(Arrays.asList(packetListeners));
    }

    public void stop() {
        for (Channel channel : channels) {
            try {
                channel.flush();
                channel.closeFuture().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public int getChannelsSize() {
        return channels.size();
    }
}
