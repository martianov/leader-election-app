package com.github.martianov.le.protocol;

import java.util.Arrays;
import java.util.List;

public class NodesListMessage extends AbstractMessage {
    private final List<NodeDescription> nodeDescriptions;

    public NodesListMessage(List<NodeDescription> nodeDescriptions) {
        super(MessageCodes.NODES_LIST);
        this.nodeDescriptions = List.copyOf(nodeDescriptions);
    }

    public List<NodeDescription> getNodeDescriptions() {
        return nodeDescriptions;
    }

    public String toDataString() {
        return String.join(",", nodeDescriptions.stream().map(NodeDescription::toDataString).toList());
    }

    public static NodesListMessage fromDataString(String dataString) {
        List<NodeDescription> nodeDescriptions = Arrays.stream(dataString.split(",")).map(NodeDescription::fromDataString).toList();
        return new NodesListMessage(nodeDescriptions);
    }

    @Override
    public String toString() {
        return "NodesListMessage{" +
                "nodeDescriptions=" + nodeDescriptions +
                '}';
    }
}
