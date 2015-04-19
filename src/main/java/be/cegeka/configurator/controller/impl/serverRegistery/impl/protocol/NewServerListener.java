package be.cegeka.configurator.controller.impl.serverRegistery.impl.protocol;

import be.cegeka.configurator.server.ServerInformation;

public interface NewServerListener {
    public void newServerArrived(ServerInformation serverInformation);
}
