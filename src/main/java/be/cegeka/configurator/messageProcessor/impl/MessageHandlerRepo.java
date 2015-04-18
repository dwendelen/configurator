package be.cegeka.configurator.messageProcessor.impl;

import be.cegeka.configurator.message.Message;
import be.cegeka.configurator.messageProcessor.MessageHandler;
import com.google.common.base.Optional;

import java.util.HashMap;
import java.util.Map;

class MessageHandlerRepo {
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
