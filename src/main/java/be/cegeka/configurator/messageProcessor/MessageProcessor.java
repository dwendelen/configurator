package be.cegeka.configurator.messageProcessor;

import java.io.IOException;

public interface MessageProcessor {
    void start();
    int getPort();
    void addMessageHandler(MessageHandler messageHandler);
}
