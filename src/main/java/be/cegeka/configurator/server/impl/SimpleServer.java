package be.cegeka.configurator.server.impl;

import be.cegeka.configurator.message.Message;
import be.cegeka.configurator.message.MessageSender;
import be.cegeka.configurator.server.Server;
import be.cegeka.configurator.server.ServerInformation;
import be.cegeka.configurator.server.ServerListener;
import be.cegeka.configurator.serverRegistery.impl.PingMessage;
import com.google.common.base.Optional;

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
            notifyUnreachable();
        }
    }

    private <T> Optional<T> sendAndReceive(Message message, Class<T> classResponse) {
        try {
            T response = messageSender.sendAnReceive(this.getInetAddress(), this.getPort(), message, classResponse);
            return Optional.of(response);
        } catch (IOException e) {
            notifyUnreachable();
            return Optional.absent();
        }
    }

    @Override
    public void addServerListener(ServerListener serverListener) {
        serverListeners.add(serverListener);
    }

    @Override
    public void ping() {
        Optional<String> stringOptional = sendAndReceive(new PingMessage(), String.class);
        if(!getUuid().equals(stringOptional.orNull())) {
            notifyUnreachable();
        }
    }

    private void notifyUnreachable() {
        for (ServerListener serverListener : serverListeners) {
            serverListener.serverUnreachable(this);
        }
    }
}
