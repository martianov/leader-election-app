package com.github.martianov.le.protocol;

public abstract class AbstractMessage {
    private final MessageCodes code;

    protected AbstractMessage(MessageCodes code) {
        this.code = code;
    }

    public MessageCodes getCode() {
        return code;
    }
}
