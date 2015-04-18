package be.cegeka.configurator.listener;

import com.google.common.base.Optional;

import java.util.HashMap;
import java.util.Map;

public class MessageHandlerRepo {
    Map<String, MessageHandler> messageHandlerMap = new HashMap<String, MessageHandler>();

    public <T extends Message> Optional<MessageHandler<T>> get(String messageType) {
        if(messageHandlerMap.containsKey(messageType)) {
            return Optional.<MessageHandler<T>>of(messageHandlerMap.get(messageType));
        } else {
            return Optional.absent();
        }
    }

    public void add(MessageHandler<?> messageHandler) {
        String messageType = messageHandler.getMessageType();
        messageHandlerMap.put(messageType, messageHandler);
    }
}