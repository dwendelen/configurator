package be.cegeka.configurator.serverRegistery;

import be.cegeka.configurator.listener.MessageHandler;

import java.util.List;

public interface ServerRegistery {
    void start();
    void stop();
    List<? extends MessageHandler> getMessageHandlers();
}
