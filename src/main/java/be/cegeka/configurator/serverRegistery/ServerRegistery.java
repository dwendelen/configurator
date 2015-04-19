package be.cegeka.configurator.serverRegistery;

import java.io.IOException;
import java.util.List;

public interface ServerRegistery {
    void start() throws IOException;
    void stop();
    List<Server> getServers();
    Server getThisServer();
}
