package com.github.martianov.le;

import com.github.martianov.le.protocol.AbstractMessage;
import com.github.martianov.le.protocol.ConnectMessage;
import com.github.martianov.le.protocol.NodesListMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.StandardCharsets;

public class MessageEncoder extends MessageToByteEncoder<AbstractMessage> {
    @Override
    protected void encode(ChannelHandlerContext ctx, AbstractMessage message, ByteBuf out) {
        switch (message.getCode()) {
            case PING, PONG -> out.writeInt(message.getCode().getCode());
            case CONNECT -> {
                ConnectMessage connectMessage = (ConnectMessage) message;
                out.writeInt(message.getCode().getCode());

                String nodeAddress = connectMessage.getNodeDescription().toDataString();
                out.writeInt(nodeAddress.length());
                out.writeCharSequence(nodeAddress, StandardCharsets.UTF_8);
            }
            case NODES_LIST -> {
                NodesListMessage nodeListMessage = (NodesListMessage) message;
                out.writeInt(nodeListMessage.getCode().getCode());

                String dataString = nodeListMessage.toDataString();
                out.writeInt(dataString.length());
                out.writeCharSequence(dataString, StandardCharsets.UTF_8);
            }
        }
    }
}
