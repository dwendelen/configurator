package be.cegeka.configurator.server;

import java.util.UUID;

public class Server {
    private UUID uuid;
    private int port;
    private String hostname;

    protected void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getPort() {
        return port;
    }

    protected void setPort(int port) {
        this.port = port;
    }

    public String getHostname() {
        return hostname;
    }

    protected void setHostname(String hostname) {
        this.hostname = hostname;
    }
}
