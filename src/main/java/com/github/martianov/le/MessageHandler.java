package com.github.martianov.le;

import com.github.martianov.le.protocol.*;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.List;

public class MessageHandler extends SimpleChannelInboundHandler<AbstractMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, AbstractMessage msg) throws Exception {
        if (msg instanceof PingMessage) {
            ctx.writeAndFlush(new PongMessage());
        } else if (msg instanceof ConnectMessage connectMessage) {
            ctx.writeAndFlush(new NodesListMessage(List.of(new NodeDescription("127.0.0.1", 99), connectMessage.getNodeDescription())));
        } else {
            ctx.writeAndFlush(new PingMessage());
        }
    }
}
