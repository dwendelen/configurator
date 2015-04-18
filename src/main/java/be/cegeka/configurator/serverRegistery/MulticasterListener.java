package be.cegeka.configurator.serverRegistery;

import java.net.InetAddress;

interface MulticasterListener {
    void messageArrived(InetAddress inetAddress, ServerInfoMessage serverInfoMessage);
}
