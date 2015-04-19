package be.cegeka.configurator.serverRegistery.impl;

import be.cegeka.configurator.serverRegistery.Server;
import be.cegeka.configurator.serverRegistery.impl.message.UnreachableMessage;

public class ServerListenerForRegistry implements be.cegeka.configurator.serverRegistery.ServerListener {
    private Repository repository;

    public ServerListenerForRegistry(Repository repository) {
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
