package be.cegeka.configurator.socket;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class SocketFactory {
    public Socket createTCPSocket() {
        return new TCPSocket();
    }

    public Socket createMulticastSocket(String multicastAddress) {
        try {
            return new MulticastSocket(InetAddress.getByName(multicastAddress));
        } catch (UnknownHostException e) {
            throw new RuntimeException("programming mistake", e);
        }
    }
}
