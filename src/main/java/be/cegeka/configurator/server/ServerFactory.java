package be.cegeka.configurator.server;

import be.cegeka.configurator.controller.impl.MessageSender;
import be.cegeka.configurator.server.impl.SimpleServer;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

public class ServerFactory {
    private MessageSender messageSender;

    public ServerFactory(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    public Server createThisServer(int port) {
        ServerInformation serverInformation = new ServerInformation();
        serverInformation.setPort(port);
        serverInformation.setUuid(UUID.randomUUID().toString());

        try {
            InetAddress localhost = InetAddress.getLocalHost();
            serverInformation.setInetAddress(localhost.getHostAddress().toString());
            serverInformation.setHostname(localhost.getHostName());
        } catch (UnknownHostException e) {
            serverInformation.setInetAddress("unknown");
            serverInformation.setHostname("unknown");
        }

        return createNewServer(serverInformation);
    }

    public Server createNewServer(ServerInformation serverInformation) {
        SimpleServer server = new SimpleServer(messageSender, serverInformation);
        return server;
    }
}
