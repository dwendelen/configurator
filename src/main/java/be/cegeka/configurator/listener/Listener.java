package be.cegeka.configurator.listener;

import java.io.IOException;

public interface Listener {
    void init() throws IOException;
    void start();
    int getPort();
    void addMessageHandler(MessageHandler messageHandler);
}
