package be.cegeka.configurator.controller.impl.serverRegistery.impl.message;

import be.cegeka.configurator.controller.impl.processor.JSONMessageHandler;
import be.cegeka.configurator.server.Server;
import be.cegeka.configurator.server.ServerFactory;
import be.cegeka.configurator.server.ServerInformation;
import be.cegeka.configurator.controller.impl.serverRegistery.impl.Repository;

public class ServerInfoHandler implements JSONMessageHandler<ServerInfoMessage> {
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
