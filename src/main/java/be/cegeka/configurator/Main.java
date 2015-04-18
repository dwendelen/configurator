package be.cegeka.configurator;

import be.cegeka.configurator.listener.Listener;
import be.cegeka.configurator.listener.ListenerFactory;
import be.cegeka.configurator.listener.MessageHandler;
import be.cegeka.configurator.server.ServerFactory;
import be.cegeka.configurator.serverRegistery.ServerRegistery;
import be.cegeka.configurator.serverRegistery.ServerRegisteryFactory;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String [] args) throws Exception
    {
        ListenerFactory listenerFactory = new ListenerFactory();
        Listener listener = listenerFactory.create();
        listener.init();

        ServerFactory serverFactory = new ServerFactory(listener.getPort());

        ServerRegisteryFactory serverRegisteryFactory = new ServerRegisteryFactory(serverFactory);
        ServerRegistery serverRegistery = serverRegisteryFactory.create();

        List<? extends MessageHandler> messageHandlers = serverRegistery.getMessageHandlers();
        for (MessageHandler messageHandler : messageHandlers) {
            listener.addMessageHandler(messageHandler);
        }

        listener.start();
        serverRegistery.start();
    }
}
