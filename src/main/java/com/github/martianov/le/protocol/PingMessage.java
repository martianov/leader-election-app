package com.github.martianov.le.protocol;

public class PingMessage extends AbstractMessage {
    public PingMessage() {
        super(MessageCodes.PING);
    }
}
