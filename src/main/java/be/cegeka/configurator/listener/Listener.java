package be.cegeka.configurator.listener;

import java.io.IOException;

public interface Listener {
    void start();
    int getPort();
    void addMessageHandler(MessageHandler messageHandler);

    void init() throws IOException;
}
