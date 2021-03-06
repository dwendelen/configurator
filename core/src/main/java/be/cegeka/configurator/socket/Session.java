package be.cegeka.configurator.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;

public interface Session {
    String getAddress();
    InputStream read() throws IOException;
    OutputStream write() throws IOException;
    void close() throws IOException;
}
