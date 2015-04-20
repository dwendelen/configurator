package be.cegeka.configurator.socket;

import java.io.IOException;

public interface ListeningContext {
    Session waitForSession() throws IOException;
    void close() throws IOException;
    int getPort();
}
