package be.cegeka.configurator;

import be.cegeka.configurator.controller.impl.MessageSender;
import be.cegeka.configurator.controller.impl.processor.MessageProcessor;
import be.cegeka.configurator.controller.impl.processor.MessageProcessorFactory;
import be.cegeka.configurator.server.ServerFactory;
import be.cegeka.configurator.controller.impl.serverRegistery.ServerRegistery;
import be.cegeka.configurator.controller.impl.serverRegistery.ServerRegisteryFactory;
import be.cegeka.configurator.socket.Socket;
import be.cegeka.configurator.socket.SocketFactory;
import org.codehaus.jackson.map.ObjectMapper;

public class Main {
    public static void main(String [] args) throws Exception
    {
        SocketFactory socketFactory = new SocketFactory();
        Socket tcpSocket = socketFactory.createTCPSocket();
        ObjectMapper objectMapper = new ObjectMapper();

        MessageSender messageSender = new MessageSender(tcpSocket, objectMapper);

        ServerFactory serverFactory = new ServerFactory(messageSender);
        MessageProcessorFactory messageProcessorFactory = new MessageProcessorFactory(socketFactory, objectMapper);
        ServerRegisteryFactory serverRegisteryFactory = new ServerRegisteryFactory(serverFactory);

        MessageProcessor messageProcessor = messageProcessorFactory.create();
        messageProcessor.start();

        ServerRegistery serverRegistery = serverRegisteryFactory.create(messageProcessor);
        serverRegistery.start();
    }
}
