package be.cegeka.configurator.controller.impl.processor;

import com.google.common.base.Optional;

public interface MessageProcessor {
    void start();
    Optional<Integer> getPort();
    void addMessageHandler(JSONMessageHandler JSONMessageHandler);
}
