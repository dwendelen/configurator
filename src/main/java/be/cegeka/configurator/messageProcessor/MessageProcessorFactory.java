package be.cegeka.configurator.messageProcessor;

import be.cegeka.configurator.messageProcessor.impl.MessageHandlerRepo;
import be.cegeka.configurator.messageProcessor.impl.TCPMessageProcessor;

public class MessageProcessorFactory {
    public MessageProcessor create() {
        return new TCPMessageProcessor(new MessageHandlerRepo());
    }
}
