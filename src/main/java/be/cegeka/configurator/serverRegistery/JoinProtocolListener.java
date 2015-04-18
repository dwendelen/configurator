package be.cegeka.configurator.serverRegistery;

import java.net.InetAddress;

interface JoinProtocolListener {
    void newSereverArrived(InetAddress inetAddress, ServerInfoMessage serverInfoMessage);
}
