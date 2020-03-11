package com.piotreee.pixengine.networking;

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

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        channels.add(ctx.channel());
        for (int i = 0; i < packetListenersSize; i++) {
            packetListeners.get(i).active(ctx);
        }

        ctx.flush();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        channels.remove(ctx.channel());
        for (int i = 0; i < packetListenersSize; i++) {
            packetListeners.get(i).inActive(ctx);
        }

        ctx.flush();
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
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    public void sendAll(Packet packet) {
        byte[] data = packet.getData();
        for (Channel channel : channels) {
            channel.writeAndFlush(data);
        }
    }

    public void sendAllExcept(Packet packet, Channel except) {
        byte[] data = packet.getData();
        for (Channel channel : channels) {
            if (channel.equals(except)) {
                continue;
            }

            channel.writeAndFlush(data);
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
