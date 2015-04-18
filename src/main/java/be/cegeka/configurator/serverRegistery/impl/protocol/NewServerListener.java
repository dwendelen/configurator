package be.cegeka.configurator.serverRegistery.impl.protocol;

import be.cegeka.configurator.server.ServerInformation;
import be.cegeka.configurator.serverRegistery.impl.message.ServerInfoMessage;

import java.net.InetAddress;

public interface NewServerListener {
    public void newServerArrived(ServerInformation serverInformation);
}
