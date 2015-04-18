package be.cegeka.configurator;

import be.cegeka.configurator.messageProcessor.MessageProcessor;
import be.cegeka.configurator.messageProcessor.MessageProcessorFactory;
import be.cegeka.configurator.messageProcessor.MessageHandler;
import be.cegeka.configurator.server.ServerFactory;
import be.cegeka.configurator.serverRegistery.ServerRegistery;
import be.cegeka.configurator.serverRegistery.ServerRegisteryFactory;

import java.util.List;

public class Main {
    public static void main(String [] args) throws Exception
    {
        ServerFactory serverFactory = new ServerFactory();
        MessageProcessorFactory messageProcessorFactory = new MessageProcessorFactory();
        ServerRegisteryFactory serverRegisteryFactory = new ServerRegisteryFactory(serverFactory);

        MessageProcessor messageProcessor = messageProcessorFactory.create();
        messageProcessor.start();

        ServerRegistery serverRegistery = serverRegisteryFactory.create(messageProcessor);
        serverRegistery.start();
    }
}
