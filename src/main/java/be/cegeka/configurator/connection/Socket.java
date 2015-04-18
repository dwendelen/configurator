package be.cegeka.configurator.connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;

public interface Socket {
    ListeningContext listen(int port) throws IOException;
    Session open(InetAddress inetAddress, int port) throws IOException;
}
