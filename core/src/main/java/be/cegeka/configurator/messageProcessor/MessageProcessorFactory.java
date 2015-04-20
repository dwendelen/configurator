package be.cegeka.configurator.messageProcessor;

import be.cegeka.configurator.messageProcessor.impl.ListenerDaemon;
import be.cegeka.configurator.messageProcessor.impl.MessageHandlerRepo;
import be.cegeka.configurator.messageProcessor.impl.TCPMessageProcessor;
import be.cegeka.configurator.socket.Socket;
import be.cegeka.configurator.socket.SocketFactory;
import org.codehaus.jackson.map.ObjectMapper;

public class MessageProcessorFactory {
    private SocketFactory socketFactory;
    private ObjectMapper objectMapper;

    public MessageProcessorFactory(SocketFactory socketFactory, ObjectMapper objectMapper) {
        this.socketFactory = socketFactory;
        this.objectMapper = objectMapper;
    }

    public MessageProcessor create() {
        Socket socket = socketFactory.createTCPSocket();
        MessageHandlerRepo messageHandlerRepo = new MessageHandlerRepo();
        ListenerDaemon listenerDaemon = new ListenerDaemon(socket, objectMapper, messageHandlerRepo);
        return new TCPMessageProcessor(listenerDaemon, messageHandlerRepo);
    }
}
