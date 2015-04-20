package be.cegeka.configurator;

import be.cegeka.configurator.message.MessageSender;
import be.cegeka.configurator.message.MessageSenderFactory;
import be.cegeka.configurator.messageProcessor.MessageProcessor;
import be.cegeka.configurator.messageProcessor.MessageProcessorFactory;
import be.cegeka.configurator.serverRegistery.impl.ServerFactory;
import be.cegeka.configurator.serverRegistery.ServerRegistery;
import be.cegeka.configurator.serverRegistery.ServerRegisteryFactory;
import be.cegeka.configurator.socket.Socket;
import be.cegeka.configurator.socket.SocketFactory;
import org.codehaus.jackson.map.ObjectMapper;

public class Main {
    public static void main(String [] args) throws Exception
    {
        SocketFactory socketFactory = new SocketFactory();
        ObjectMapper objectMapper = new ObjectMapper();

        MessageSenderFactory messageSenderFactory = new MessageSenderFactory(objectMapper);

        MessageProcessorFactory messageProcessorFactory = new MessageProcessorFactory(socketFactory, objectMapper);
        ServerRegisteryFactory serverRegisteryFactory = new ServerRegisteryFactory(messageSenderFactory, socketFactory, objectMapper);

        MessageProcessor messageProcessor = messageProcessorFactory.create();
        messageProcessor.start();

        ServerRegistery serverRegistery = serverRegisteryFactory.create(messageProcessor);
        serverRegistery.start();

        System.in.read();

        messageProcessor.stop();
    }
}
