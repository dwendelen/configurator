package be.cegeka.configurator.socket;

import java.io.IOException;
import java.net.InetAddress;

public interface Socket {
    ListeningContext listen(int port) throws IOException;
    Session open(String inetAddress, int port) throws IOException;
}
