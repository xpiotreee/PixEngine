package com.piotreee.pixengine.networking;

import com.google.gson.Gson;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class Client implements Runnable {
    private Gson gson;
    private ClientHandler handler;
    private EventLoopGroup workerGroup;
    private Channel channel;
    private ChannelFuture lastWriteFuture;
    private String host;
    private int port;

    public Client(String host, int port) {
        this.handler = new ClientHandler();
        this.host = host;
        this.port = port;
    }

    @Override
    public void run() {
        workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) {
                    ch.pipeline().addLast(
                            new ObjectEncoder(),
                            new ObjectDecoder(ClassResolvers.cacheDisabled(null)),
                            handler);
                }
            });

            channel = b.connect(host, port).sync().channel();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                channel.closeFuture().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            workerGroup.shutdownGracefully();
        }
    }

    public void send(Object o) {
        lastWriteFuture = channel.writeAndFlush(gson.toJson(new Packet(o.getClass(), o)));
    }

    public void addListeners(PacketListener... packetListeners) {
        handler.addListeners(packetListeners);
    }

    public void stop() {
        channel.closeFuture();
        workerGroup.shutdownGracefully();
    }
}
