package com.github.martianov.le;

import com.github.martianov.le.protocol.*;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class MessageDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        if (in.readableBytes() < 4) {
            return;
        }

        MessageCodes code = MessageCodes.fromCode(in.getInt(0));

        switch (code) {
            case PING, PONG -> in.readInt();
            case CONNECT, NODES_LIST ->  {
                if (in.readableBytes() < 8) return;
                int length = in.getInt(4);
                if (in.readableBytes() < 8 + length) return;
            }
        }

        switch (code) {
            case PING -> out.add(new PingMessage());
            case PONG -> out.add(new PongMessage());
            case CONNECT -> {
                in.readInt();
                int length = in.readInt();
                String data = in.readCharSequence(length, StandardCharsets.UTF_8).toString();
                out.add(new ConnectMessage(NodeDescription.fromDataString(data)));
            }
            case NODES_LIST -> {
                in.readInt();
                int length = in.readInt();
                String data = in.readCharSequence(length, StandardCharsets.UTF_8).toString();
                out.add(NodesListMessage.fromDataString(data));
            }
        }
    }
}
