package be.cegeka.configurator.server;

import be.cegeka.configurator.message.Message;

import java.io.IOException;
import java.net.InetAddress;
import java.util.UUID;

public interface Server {
    String getUuid();
    int getPort();
    String getHostname();
    String getInetAddress();
    void send(Message message);
    void addServerListener(ServerListener serverListener);
    void ping();
}
