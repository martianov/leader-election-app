package com.github.martianov.le;

import com.github.martianov.le.protocol.AbstractMessage;
import com.github.martianov.le.protocol.PingMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class PingHandler extends SimpleChannelInboundHandler<AbstractMessage> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.write(new PingMessage());
        ctx.flush();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, AbstractMessage msg) throws Exception {
        System.out.println(msg);
        ctx.channel().close();
    }
}
