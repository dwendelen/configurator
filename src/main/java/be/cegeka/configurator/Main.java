package be.cegeka.configurator;

import be.cegeka.configurator.listener.Listener;
import be.cegeka.configurator.listener.ListenerFactory;
import be.cegeka.configurator.server.ServerFactory;
import be.cegeka.configurator.serverRegistery.ServerRegistery;
import be.cegeka.configurator.serverRegistery.ServerRegisteryFactory;

public class Main {
    public static void main(String [] args)
    {
        ListenerFactory listenerFactory = new ListenerFactory();
        Listener listener = listenerFactory.create();

        ServerFactory serverFactory = new ServerFactory(listener.getPort());

        ServerRegisteryFactory serverRegisteryFactory = new ServerRegisteryFactory(serverFactory);
        ServerRegistery serverRegistery = serverRegisteryFactory.create();
        serverRegistery.start();
    }
}
