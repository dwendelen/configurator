package be.cegeka.configurator.controller.impl.serverRegistery.impl;

import be.cegeka.configurator.server.Server;
import be.cegeka.configurator.controller.impl.serverRegistery.ServerRegistery;
import be.cegeka.configurator.controller.impl.serverRegistery.impl.protocol.JoinProtocol;

import java.io.IOException;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class MulticastServerRegistery implements ServerRegistery {
    private JoinProtocol joinProtocol;
    private Repository repository;

    public MulticastServerRegistery(JoinProtocol joinProtocol, Repository repository) {
        this.joinProtocol = joinProtocol;
        this.repository = repository;
    }

    public void start() throws IOException {
        joinProtocol.start();
    }

    public void stop() {
        joinProtocol.stop();
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
