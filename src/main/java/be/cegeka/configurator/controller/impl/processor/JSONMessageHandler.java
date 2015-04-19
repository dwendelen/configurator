package be.cegeka.configurator.controller.impl.processor;

import be.cegeka.configurator.controller.impl.JSONMessage;

public interface JSONMessageHandler<T extends JSONMessage> {
    Class<T> getMessageClass();
    String getMessageType();
    void handle(T message, String inetAddress);
}
