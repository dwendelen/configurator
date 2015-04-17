package be.cegeka.configurator.serverRegistery;

import be.cegeka.configurator.server.Server;
import com.google.common.base.Optional;

import java.util.*;

class Repository {
    private Server thisServer;
    private Map<UUID, Server> otherServersMap = new HashMap<UUID, Server>();
    private List<Server> otherServersList = new LinkedList<Server>();

    public Repository(Server thisServer) {
        this.thisServer = thisServer;
    }

    public Server getThisServer() {
        return thisServer;
    }

    public void addServer(Server server) {
        if(otherServersMap.containsKey(server.getUuid())) {
            return;
        }

        otherServersList.add(server);
        otherServersMap.put(server.getUuid(), server);
    }

    public void removeServerByUuid(UUID uuid) {
        Server server = otherServersMap.remove(uuid);
        otherServersList.remove(server);
    }

    public Optional<Server> getServer(UUID uuid) {
        if(otherServersMap.containsKey(uuid)) {
            return Optional.of(otherServersMap.get(uuid));
        } else {
            return Optional.absent();
        }
    }

    public List<Server> getServers() {
        return new LinkedList<Server>(otherServersList);
    }
}
