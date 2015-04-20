package be.cegeka.configurator.serverRegistery.impl.message;

import be.cegeka.configurator.message.Message;
import be.cegeka.configurator.serverRegistery.Server;
import be.cegeka.configurator.serverRegistery.impl.Repository;
import be.cegeka.configurator.serverRegistery.impl.ServerInformation;

import java.util.LinkedList;
import java.util.List;

public class ServerInfoMessage implements Message {
    public static final String MESSAGE_TYPE = "serverInfo";

    private String uuid;
    private String hostname;
    private int port;
    private List<ServerInformation> servers = new LinkedList<ServerInformation>();

    public ServerInfoMessage() {}

    public ServerInfoMessage(Repository repository) {
        setPort(repository.getThisServer().getPort());
        setUuid(repository.getThisServer().getUuid().toString());
        setHostname(repository.getThisServer().getHostname());

        for (Server server : repository.getServers()) {
            ServerInformation serverInformation = new ServerInformation();
            serverInformation.setHostname(server.getHostname());
            serverInformation.setPort(server.getPort());
            serverInformation.setInetAddress(server.getInetAddress());
            serverInformation.setUuid(server.getUuid());

            servers.add(serverInformation);
        }
    }

    public String getUuid() {
        return uuid;
    }

    public String getHostname() {
        return hostname;
    }


    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    @Override
    public String getType() {
        return MESSAGE_TYPE;
    }

    public void setType(String messageType) {

    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public List<ServerInformation> getServers() {
        return new LinkedList<ServerInformation>(servers);
    }

    public void setServers(List<ServerInformation> servers) {
        this.servers = new LinkedList<ServerInformation>(servers);
    }
}
