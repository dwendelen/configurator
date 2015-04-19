package be.cegeka.configurator.messageProcessor;

import com.google.common.base.Optional;

import java.io.IOException;

public interface MessageProcessor {
    void start();
    void stop();
    Optional<Integer> getPort();
    void addMessageHandler(MessageHandler messageHandler);
}
