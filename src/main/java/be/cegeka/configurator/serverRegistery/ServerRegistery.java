package be.cegeka.configurator.serverRegistery;

import be.cegeka.configurator.messageProcessor.MessageHandler;

import java.io.IOException;
import java.util.List;

public interface ServerRegistery {
    void start() throws IOException;
    void stop();
    List<? extends MessageHandler> getMessageHandlers();
}
