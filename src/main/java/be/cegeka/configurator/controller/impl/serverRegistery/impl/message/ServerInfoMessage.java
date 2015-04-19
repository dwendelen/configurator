package be.cegeka.configurator.controller.impl.serverRegistery.impl.message;

import be.cegeka.configurator.controller.impl.JSONMessage;
import be.cegeka.configurator.server.Server;

public class ServerInfoMessage implements JSONMessage {
    public static final String MESSAGE_TYPE = "serverInfo";

    private String uuid;
    private String hostname;
    private int port;

    public ServerInfoMessage() {}

    public ServerInfoMessage(Server server) {
        setPort(server.getPort());
        setUuid(server.getUuid().toString());
        setHostname(server.getHostname());
    }

    public String getUuid() {
        return uuid;
    }

    public String getHostname() {
        return hostname;
    }


    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    @Override
    public String getType() {
        return MESSAGE_TYPE;
    }

    public void setType(String messageType) {

    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
