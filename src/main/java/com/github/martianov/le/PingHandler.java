package com.github.martianov.le;

import com.github.martianov.le.protocol.AbstractMessage;
import com.github.martianov.le.protocol.PingMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PingHandler extends SimpleChannelInboundHandler<AbstractMessage> {
    private static final Logger logger = LoggerFactory.getLogger(MessageHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.write(new PingMessage());
        ctx.flush();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, AbstractMessage msg) throws Exception {
        logger.info("Got response message: {}", msg);
        ctx.channel().close();
    }
}
