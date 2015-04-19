package be.cegeka.configurator.controller.impl.serverRegistery;

import be.cegeka.configurator.server.Server;

import java.io.IOException;
import java.util.List;

public interface ServerRegistery {
    void start() throws IOException;
    void stop();
    List<Server> getServers();
    Server getThisServer();
}
