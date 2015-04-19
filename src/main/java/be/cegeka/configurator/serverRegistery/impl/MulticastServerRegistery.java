package be.cegeka.configurator.serverRegistery.impl;

import be.cegeka.configurator.message.MessageSender;
import be.cegeka.configurator.server.Server;
import be.cegeka.configurator.serverRegistery.ServerRegistery;

import java.io.IOException;
import java.util.List;


public class MulticastServerRegistery implements ServerRegistery {
    public static final int PORT = 6548;
    public static final String MULTICAST = "ff05::dace";

    private Repository repository;
    private NewServerDaemon newServerDaemon;
    private MessageSender messageSender;

    public MulticastServerRegistery(Repository repository, NewServerDaemon newServerDaemon, MessageSender messageSender) {
        this.repository = repository;
        this.newServerDaemon = newServerDaemon;
        this.messageSender = messageSender;
    }

    public void start() throws IOException {
        newServerDaemon.start();

        ServerInfoMessage serverInfoMessage = new ServerInfoMessage(repository);
        messageSender.send(MULTICAST, PORT, serverInfoMessage);
    }

    public void stop() {
        newServerDaemon.stop();
        //QuitMessage quitMessage = new QuitMessage();
        //quitMessage.setUuid(getThisServer().getUuid());
        UnreachableMessage unreachableMessage = new UnreachableMessage();
        unreachableMessage.setUuid(getThisServer().getUuid());
        new Broadcaster(repository).broadcast(unreachableMessage);
    }

    @Override
    public List<Server> getServers() {
        return repository.getServers();
    }

    @Override
    public Server getThisServer() {
        return repository.getThisServer();
    }
}
