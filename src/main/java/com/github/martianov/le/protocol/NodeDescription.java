package com.github.martianov.le.protocol;

public class NodeDescription {
    private final String host;
    private final int port;

    public NodeDescription(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String toDataString() {
        return host + ":" + port;
    }

    @Override
    public String toString() {
        return "NodeDescription{" +
                "host='" + host + '\'' +
                ", port=" + port +
                '}';
    }

    public static NodeDescription fromDataString(String description) {
        String[] parts = description.split(":");
        if (parts.length < 2) {
            throw new IllegalArgumentException("Invalid description format");
        }
        String host = parts[0];
        String portString = parts[1];
        try {
            int port = Integer.parseInt(portString, 10);
            return new NodeDescription(host, port);
        } catch (NumberFormatException nfe) {
            throw new IllegalArgumentException("Invalid description format", nfe);
        }
    }
}

