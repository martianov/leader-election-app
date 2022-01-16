package com.github.martianov.le;

import com.github.martianov.le.protocol.AbstractMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MessageEncoder extends MessageToByteEncoder<AbstractMessage> {
    @Override
    protected void encode(ChannelHandlerContext ctx, AbstractMessage message, ByteBuf out) {
        out.writeInt(message.getCode().getCode());
    }
}
