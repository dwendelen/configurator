package be.cegeka.configurator.messageProcessor;

public class MessageProcessorFactory {
    public MessageProcessor create() {
        return new TCPMessageProcessor(new MessageHandlerRepo());
    }
}
