package be.cegeka.configurator.serverRegistery.impl;

import be.cegeka.configurator.server.Server;
import com.google.common.base.Optional;

import java.util.*;

public class Repository {
    private Server thisServer;
    private Map<String, Server> otherServersMap = new HashMap<String, Server>();
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

    public void removeServerByUuid(String uuid) {
        Server server = otherServersMap.remove(uuid);
        otherServersList.remove(server);
    }

    public Optional<Server> getServer(String uuid) {
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
