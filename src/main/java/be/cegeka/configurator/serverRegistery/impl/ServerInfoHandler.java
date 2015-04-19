package be.cegeka.configurator.serverRegistery.impl;

import be.cegeka.configurator.messageProcessor.MessageHandler;
import be.cegeka.configurator.server.Server;
import be.cegeka.configurator.server.ServerFactory;
import be.cegeka.configurator.server.ServerInformation;
import be.cegeka.configurator.socket.Session;
import com.google.common.base.Optional;

import java.io.IOException;

public class ServerInfoHandler implements MessageHandler<ServerInfoMessage> {
    private Repository repository;
    private ServerFactory serverFactory;

    public ServerInfoHandler(Repository repository, ServerFactory serverFactory) {
        this.repository = repository;
        this.serverFactory = serverFactory;
    }

    @Override
    public Class<ServerInfoMessage> getMessageClass() {
        return ServerInfoMessage.class;
    }

    @Override
    public String getMessageType() {
        return ServerInfoMessage.MESSAGE_TYPE;
    }

    @Override
    public void handle(ServerInfoMessage message, Session session) {
        System.out.println("INCOMMING");
        ServerInfoMessage thisServerInfo = new ServerInfoMessage(repository);

        ServerInformation serverInformation = new ServerInformation();
        serverInformation.setPort(message.getPort());
        serverInformation.setHostname(message.getHostname());
        serverInformation.setInetAddress(session.getAddress());
        serverInformation.setUuid(message.getUuid());

        handleServer(serverInformation, thisServerInfo);

        for (ServerInformation information : message.getServers()) {
            System.out.println("DERIVE");
            handleServer(information, thisServerInfo);
        }

        System.out.println();
    }

    private void handleServer(ServerInformation serverInformation, ServerInfoMessage thisServerInfo) {
        System.out.println("SERVER: ");
        System.out.println(serverInformation.getUuid());

        Optional<Server> server = repository.getServer(serverInformation.getUuid());
        if(server.isPresent()) {
            System.out.println("ALREADY KNOWN");
            return;
        }

        System.out.println("NEW");
        handleNewServer(serverInformation, thisServerInfo);
    }

    private void handleNewServer(ServerInformation serverInformation, ServerInfoMessage thisServerInfo) {
        Server newServer = serverFactory.createNewServer(serverInformation);
        repository.addServer(newServer);

        try {
            newServer.send(thisServerInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
