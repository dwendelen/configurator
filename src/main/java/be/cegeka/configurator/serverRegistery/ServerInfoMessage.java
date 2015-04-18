package be.cegeka.configurator.serverRegistery;

import be.cegeka.configurator.listener.Message;
import be.cegeka.configurator.server.Server;

import java.util.UUID;

class ServerInfoMessage implements Message {
    public static final String MESSAGE_TYPE = "serverInfo";

    private UUID uuid;
    private String hostname;
    private int port;

    public ServerInfoMessage(Server server) {
        setPort(server.getPort());
        setUuid(server.getUuid());
        setHostname(server.getHostname());
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getHostname() {
        return hostname;
    }


    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    @Override
    public String getMessageType() {
        return MESSAGE_TYPE;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
