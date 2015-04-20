package be.cegeka.configurator.serverRegistery.impl;

import be.cegeka.configurator.message.Message;
import be.cegeka.configurator.serverRegistery.Server;

public class Broadcaster {
    private Repository repository;

    public Broadcaster(Repository repository) {
        this.repository = repository;
    }

    public void broadcast(Message message) {
        for (Server server : repository.getServers()) {
            server.send(message);
        }
    }
}
