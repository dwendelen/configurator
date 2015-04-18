package be.cegeka.configurator.serverRegistery.impl.message;

import be.cegeka.configurator.messageProcessor.MessageHandler;
import be.cegeka.configurator.server.Server;
import be.cegeka.configurator.server.ServerFactory;
import be.cegeka.configurator.server.ServerInformation;
import be.cegeka.configurator.serverRegistery.impl.Repository;

import java.net.InetAddress;

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
        public void handle(ServerInfoMessage message, String inetAddress) {
            ServerInformation serverInformation = new ServerInformation();
            serverInformation.setPort(message.getPort());
            serverInformation.setHostname(message.getHostname());
            serverInformation.setInetAddress(inetAddress);
            serverInformation.setUuid(message.getUuid());

            Server newServer = serverFactory.createNewServer(serverInformation);
            repository.addServer(newServer);
        }
}
