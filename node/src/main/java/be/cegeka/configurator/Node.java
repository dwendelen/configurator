package be.cegeka.configurator;

import be.cegeka.configurator.message.MessageSenderFactory;
import be.cegeka.configurator.messageProcessor.MessageProcessor;
import be.cegeka.configurator.messageProcessor.MessageProcessorFactory;
import be.cegeka.configurator.serverRegistery.ServerRegistery;
import be.cegeka.configurator.serverRegistery.ServerRegisteryFactory;
import be.cegeka.configurator.socket.SocketFactory;
import org.codehaus.jackson.map.ObjectMapper;

public class Node {
    public static void main(String[] args) throws Exception {
        System.out.println("Starting up");
        SocketFactory socketFactory = new SocketFactory();
        ObjectMapper objectMapper = new ObjectMapper();

        MessageSenderFactory messageSenderFactory = new MessageSenderFactory(objectMapper);

        MessageProcessorFactory messageProcessorFactory = new MessageProcessorFactory(socketFactory, objectMapper);
        ServerRegisteryFactory serverRegisteryFactory = new ServerRegisteryFactory(messageSenderFactory, socketFactory, objectMapper);

        final MessageProcessor messageProcessor = messageProcessorFactory.create();
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.out.println("Shutting down");
                messageProcessor.stop();
            }
        });
        messageProcessor.start();

        ServerRegistery serverRegistery = serverRegisteryFactory.create(messageProcessor);
        serverRegistery.start();

        System.out.println("Started");


    }
}
