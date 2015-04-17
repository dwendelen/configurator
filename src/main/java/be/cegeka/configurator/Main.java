package be.cegeka.configurator;

import be.cegeka.configurator.server.ServerFactory;
import be.cegeka.configurator.serverRegistery.ServerRegistery;
import be.cegeka.configurator.serverRegistery.ServerRegisteryFactory;

public class Main {
    public static void main(String [] args)
    {
        ServerFactory serverFactory = new ServerFactory();
        ServerRegisteryFactory serverRegisteryFactory = new ServerRegisteryFactory(serverFactory);
        ServerRegistery serverRegistery = serverRegisteryFactory.create();
        serverRegistery.start();
    }
}
