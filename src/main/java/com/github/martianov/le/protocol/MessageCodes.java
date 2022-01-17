package com.github.martianov.le.protocol;

public enum MessageCodes {
    PING(0), PONG(1), CONNECT(2), NODES_LIST(3);

    private final int code;
    MessageCodes(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static MessageCodes fromCode(int code) {
        for (MessageCodes messageCode : values()) {
            if (messageCode.code == code) {
                return messageCode;
            }
        }
        throw new IllegalArgumentException();
    }
}
