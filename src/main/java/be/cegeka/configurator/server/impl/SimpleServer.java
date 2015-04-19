package be.cegeka.configurator.server.impl;

import be.cegeka.configurator.message.Message;
import be.cegeka.configurator.message.MessageSender;
import be.cegeka.configurator.server.Server;
import be.cegeka.configurator.server.ServerInformation;
import be.cegeka.configurator.server.ServerListener;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class SimpleServer implements Server {
    private MessageSender messageSender;
    private ServerInformation serverInformation;
    private List<ServerListener> serverListeners = new LinkedList<ServerListener>();

    public SimpleServer(MessageSender messageSender, ServerInformation serverInformation) {
        this.messageSender = messageSender;
        this.serverInformation = serverInformation;
    }

    public String getUuid() {
        return serverInformation.getUuid();
    }

    public int getPort() {
        return serverInformation.getPort();
    }

    public String getHostname() {
        return serverInformation.getHostname();
    }

    public String getInetAddress() {
        return serverInformation.getInetAddress();
    }

    @Override
    public void send(Message message) {
        try {
            messageSender.send(this.getInetAddress(), this.getPort(), message);
        } catch (IOException e) {
            for (ServerListener serverListener : serverListeners) {
                serverListener.serverUnreachable(this);
            }
        }
    }

    @Override
    public void addServerListener(ServerListener serverListener) {
        serverListeners.add(serverListener);
    }
}
