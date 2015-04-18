package be.cegeka.configurator.serverRegistery.impl;

import java.net.InetAddress;

interface JoinProtocolListener {
    void newSereverArrived(InetAddress inetAddress, ServerInfoMessage serverInfoMessage);
}
