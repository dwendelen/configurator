package be.cegeka.configurator.serverRegistery;

import java.io.IOException;
import java.util.List;

public interface ServerRegistery {
    void start() throws IOException;
    List<Server> getServers();
    Server getThisServer();
}
