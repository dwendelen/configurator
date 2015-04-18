package be.cegeka.configurator.server;

import java.net.InetAddress;
import java.util.UUID;

public interface Server {
    String getUuid();
    int getPort();
    String getHostname();
    String getInetAddress();
}
