package com.github.martianov.le.protocol;

public class PongMessage extends AbstractMessage {
    public PongMessage() {
        super(MessageCodes.PONG);
    }
}
