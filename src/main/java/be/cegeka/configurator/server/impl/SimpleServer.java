package be.cegeka.configurator.server.impl;

import be.cegeka.configurator.server.Server;

import java.net.InetAddress;
import java.util.UUID;

public class SimpleServer implements Server {
    private String uuid;
    private int port;
    private String hostname;
    private String inetAddress;

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getInetAddress() {
        return inetAddress;
    }

    public void setInetAddress(String inetAddress) {
        this.inetAddress = inetAddress;
    }
}
