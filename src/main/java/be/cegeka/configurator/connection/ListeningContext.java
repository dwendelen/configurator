package be.cegeka.configurator.connection;

import java.io.IOException;

public interface ListeningContext {
    Session waitForSession() throws IOException;
    void close() throws IOException;
    int getPort();
}
