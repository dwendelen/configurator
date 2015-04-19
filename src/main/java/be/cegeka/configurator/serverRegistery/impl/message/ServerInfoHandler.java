package be.cegeka.configurator.serverRegistery.impl.message;

import be.cegeka.configurator.messageProcessor.MessageHandler;
import be.cegeka.configurator.serverRegistery.Server;
import be.cegeka.configurator.serverRegistery.impl.Repository;
import be.cegeka.configurator.serverRegistery.impl.ServerFactory;
import be.cegeka.configurator.serverRegistery.impl.ServerInformation;
import be.cegeka.configurator.serverRegistery.impl.ServerListenerForRegistry;
import be.cegeka.configurator.socket.Session;
import com.google.common.base.Optional;

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
        //System.out.println("INCOMMING");
        ServerInfoMessage thisServerInfo = new ServerInfoMessage(repository);

        ServerInformation serverInformation = new ServerInformation();
        serverInformation.setPort(message.getPort());
        serverInformation.setHostname(message.getHostname());
        serverInformation.setInetAddress(session.getAddress());
        serverInformation.setUuid(message.getUuid());

        handleServer(serverInformation, thisServerInfo);

        for (ServerInformation information : message.getServers()) {
            //System.out.println("DERIVE");
            handleServer(information, thisServerInfo);
        }

        //System.out.println();
    }

    private void handleServer(ServerInformation serverInformation, ServerInfoMessage thisServerInfo) {
        //System.out.println("SERVER: ");
        //System.out.println(serverInformation.getUuid());

        Optional<Server> server = repository.getServer(serverInformation.getUuid());
        if(server.isPresent()) {
            //System.out.println("ALREADY KNOWN");
            return;
        }

        //System.out.println("NEW");
        handleNewServer(serverInformation, thisServerInfo);
    }

    private void handleNewServer(ServerInformation serverInformation, ServerInfoMessage thisServerInfo) {
        Server newServer = serverFactory.createNewServer(serverInformation);
        boolean success = newServer.ping();

        if(!success) {
            return;
        }

        newServer.addServerListener(serverListenerForRegistry);
        repository.addServer(newServer);

        newServer.send(thisServerInfo);
    }
}
