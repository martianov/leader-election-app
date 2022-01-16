package com.github.martianov.le;

import com.github.martianov.le.protocol.AbstractMessage;
import com.github.martianov.le.protocol.PingMessage;
import com.github.martianov.le.protocol.PongMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MessageHandler extends SimpleChannelInboundHandler<AbstractMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, AbstractMessage msg) throws Exception {
        if (msg instanceof PingMessage) {
            ctx.writeAndFlush(new PongMessage());
        } else {
            ctx.writeAndFlush(new PingMessage());
        }
    }
}
