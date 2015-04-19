package be.cegeka.configurator.server;

import be.cegeka.configurator.controller.impl.JSONMessage;

import java.io.IOException;

public interface Server {
    String getUuid();
    int getPort();
    String getHostname();
    String getInetAddress();
    void send(JSONMessage JSONMessage) throws IOException;
}
