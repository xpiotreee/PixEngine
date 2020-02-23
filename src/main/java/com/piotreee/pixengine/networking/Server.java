package com.piotreee.pixengine.networking;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class Server implements Runnable {
    protected Runnable runnable;
    protected ServerHandler handler;
    private int port;
    private EventLoopGroup acceptor;
    private EventLoopGroup workerGroup;

    public Server(int port) {
        this.port = port;
        this.runnable = () -> {
        };
        this.handler = new ServerHandler();
    }

    @Override
    public void run() {
        acceptor = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(acceptor, workerGroup);
            b.channel(NioServerSocketChannel.class);
            b.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) {
                    ch.pipeline().addLast(
                            new ObjectEncoder(),
                            new ObjectDecoder(ClassResolvers.cacheDisabled(null)),
                            handler
                    );
                }
            });

            b.option(ChannelOption.SO_BACKLOG, 128);
            b.childOption(ChannelOption.SO_KEEPALIVE, true);

            new Thread(runnable).start();

            ChannelFuture f = b.bind(port).sync();
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
            acceptor.shutdownGracefully();
        }
    }

    public void sendAll(Object o) {
        handler.sendAll(o);
    }

    public void addListeners(PacketListener... packetListeners) {
        handler.addListeners(packetListeners);
    }

    public void stop() {
        handler.stop();
        workerGroup.shutdownGracefully();
        acceptor.shutdownGracefully();
    }

    public ServerHandler getHandler() {
        return handler;
    }
}
