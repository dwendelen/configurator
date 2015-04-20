package be.cegeka.configurator.serverRegistery;

import be.cegeka.configurator.message.Message;

public interface Server {
    String getUuid();
    int getPort();
    String getHostname();
    String getInetAddress();
    void send(Message message);
    void addServerListener(ServerListener serverListener);
    boolean ping();
}
