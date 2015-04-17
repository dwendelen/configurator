package be.cegeka.configurator.serverRegistery;

import be.cegeka.configurator.server.Server;
import be.cegeka.configurator.server.ServerFactory;

public class ServerRegisteryFactory {
    private ServerFactory serverFactory;

    public ServerRegisteryFactory(ServerFactory serverFactory) {
        this.serverFactory = serverFactory;
    }

    public ServerRegistery create() {
        Server thisServer = serverFactory.createThisServer();
        Multicaster multicaster = new Multicaster(thisServer);
        Repository repository = new Repository(thisServer);
        MulticastServerRegistery multicastServerRegistery = new MulticastServerRegistery(multicaster, repository, serverFactory);

        return multicastServerRegistery;
    }
}
