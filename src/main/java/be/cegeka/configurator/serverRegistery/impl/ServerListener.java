package be.cegeka.configurator.serverRegistery.impl;

import be.cegeka.configurator.server.Server;

public class ServerListener implements be.cegeka.configurator.server.ServerListener {
    private Repository repository;

    public ServerListener(Repository repository) {
        this.repository = repository;
    }

    @Override
    public void serverUnreachable(Server server) {
        String uuid = server.getUuid();
        repository.removeServerByUuid(uuid);

        UnreachableMessage unreachableMessage = new UnreachableMessage();
        unreachableMessage.setUuid(uuid);
        new Broadcaster(repository).broadcast(unreachableMessage);
    }
}
