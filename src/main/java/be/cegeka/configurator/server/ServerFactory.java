package be.cegeka.configurator.server;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

public class ServerFactory {
    private int port;

    public ServerFactory(int port) {
        this.port = port;
    }

    public Server createThisServer() {
        UUID uuid = UUID.randomUUID();
        String hostname;
        InetAddress localHost;
        try {
            localHost = InetAddress.getLocalHost();
            hostname = localHost.getHostName();
        } catch (UnknownHostException e) {
            hostname = "unkown";
            localHost = InetAddress.getLoopbackAddress();
        }

        return createNewServer(uuid, localHost, this.port, hostname);
    }

    public Server createNewServer(UUID uuid, InetAddress address, int port, String hostname) {
        Server server = new Server();
        server.setUuid(uuid);
        server.setPort(port);
        server.setHostname(hostname);
        server.setInetAddress(address);
        return server;
    }
}
