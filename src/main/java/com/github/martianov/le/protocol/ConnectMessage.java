package com.github.martianov.le.protocol;

public class ConnectMessage extends AbstractMessage {
    private final NodeDescription nodeDescription;

    public ConnectMessage(NodeDescription nodeDescription) {
        super(MessageCodes.CONNECT);
        this.nodeDescription = nodeDescription;
    }

    public NodeDescription getNodeDescription() {
        return nodeDescription;
    }

    @Override
    public String toString() {
        return "ConnectMessage{" +
                "nodeDescription=" + nodeDescription +
                '}';
    }
}
