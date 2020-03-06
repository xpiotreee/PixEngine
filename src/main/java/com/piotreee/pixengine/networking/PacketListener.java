package com.piotreee.pixengine.networking;

import io.netty.channel.ChannelHandlerContext;

public class PacketListener<T> {
    private Class type;

    public PacketListener(Class<T> type) {
        this.type = type;
    }

    public void active(ChannelHandlerContext ctx) {

    }

    public void inActive(ChannelHandlerContext ctx) {

    }

    public void on(ChannelHandlerContext ctx, T t) throws Exception {

    }

    public Class<T> getType() {
        return type;
    }
}
