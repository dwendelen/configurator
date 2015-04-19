package be.cegeka.configurator.server.impl;

import be.cegeka.configurator.controller.impl.JSONMessage;
import be.cegeka.configurator.controller.impl.MessageSender;
import be.cegeka.configurator.server.Server;
import be.cegeka.configurator.server.ServerInformation;

import java.io.IOException;

public class SimpleServer implements Server {
    private MessageSender messageSender;
    private ServerInformation serverInformation;

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
    public void send(JSONMessage JSONMessage) throws IOException {
        messageSender.send(this.getInetAddress(), this.getPort(), JSONMessage);
    }
}
