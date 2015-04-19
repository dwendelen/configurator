package be.cegeka.configurator.controller.impl.processor.impl;

import be.cegeka.configurator.controller.impl.JSONMessage;
import be.cegeka.configurator.controller.impl.processor.JSONMessageHandler;
import com.google.common.base.Optional;

import java.util.HashMap;
import java.util.Map;

public class MessageHandlerRepo {
    Map<String, JSONMessageHandler> messageHandlerMap = new HashMap<String, JSONMessageHandler>();

    public <T extends JSONMessage> Optional<JSONMessageHandler<T>> get(String messageType) {
        if(messageHandlerMap.containsKey(messageType)) {
            return Optional.<JSONMessageHandler<T>>of(messageHandlerMap.get(messageType));
        } else {
            return Optional.absent();
        }
    }

    public void add(JSONMessageHandler<?> JSONMessageHandler) {
        String messageType = JSONMessageHandler.getMessageType();
        messageHandlerMap.put(messageType, JSONMessageHandler);
    }
}
