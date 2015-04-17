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
        try {
            hostname = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            hostname = "unkown";
        }
        return createNewServer(uuid, this.port, hostname);
    }

    public Server createNewServer(UUID uuid, int port, String hostname) {
        Server server = new Server();
        server.setUuid(uuid);
        server.setPort(port);
        server.setHostname(hostname);
        return server;
    }
}
