package be.cegeka.configurator.serverRegistery.impl.message;

import be.cegeka.configurator.messageProcessor.MessageHandler;
import be.cegeka.configurator.serverRegistery.Server;
import be.cegeka.configurator.serverRegistery.impl.*;
import be.cegeka.configurator.socket.Session;
import com.google.common.base.Optional;

import java.io.IOException;

public class ServerInfoHandler implements MessageHandler<ServerInfoMessage> {
    private Repository repository;
    private ServerFactory serverFactory;
    private ServerListenerForRegistry serverListenerForRegistry;

    public ServerInfoHandler(Repository repository, ServerFactory serverFactory, ServerListenerForRegistry serverListenerForRegistry) {
        this.repository = repository;
        this.serverFactory = serverFactory;
        this.serverListenerForRegistry = serverListenerForRegistry;
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
        ServerInfoMessage thisServerInfo = new ServerInfoMessage(repository);

        ServerInformation serverInformation = new ServerInformation();
        serverInformation.setPort(message.getPort());
        serverInformation.setHostname(message.getHostname());
        serverInformation.setInetAddress(session.getAddress());
        serverInformation.setUuid(message.getUuid());

        handleServer(serverInformation, thisServerInfo);

        for (ServerInformation information : message.getServers()) {
            handleServer(information, thisServerInfo);
        }
    }

    private void handleServer(ServerInformation serverInformation, ServerInfoMessage thisServerInfo) {
        Optional<Server> server = repository.getServer(serverInformation.getUuid());
        if(server.isPresent()) {
            return;
        }

        handleNewServer(serverInformation, thisServerInfo);
    }

    private void handleNewServer(ServerInformation serverInformation, ServerInfoMessage thisServerInfo) {
        Server newServer = serverFactory.createNewServer(serverInformation);
        newServer.addServerListener(serverListenerForRegistry);

        boolean success = newServer.ping();

        if(!success) {
            return;
        }

        repository.addServer(newServer);

        newServer.send(thisServerInfo);
    }
}
