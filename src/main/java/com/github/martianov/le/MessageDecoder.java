package com.github.martianov.le;

import com.github.martianov.le.protocol.MessageCodes;
import com.github.martianov.le.protocol.PingMessage;
import com.github.martianov.le.protocol.PongMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class MessageDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        if (in.readableBytes() < 4) {
            return;
        }

        MessageCodes code = MessageCodes.fromCode(in.readInt());

        switch (code) {
            case PING -> out.add(new PingMessage());
            case PONG -> out.add(new PongMessage());
        }
    }
}
