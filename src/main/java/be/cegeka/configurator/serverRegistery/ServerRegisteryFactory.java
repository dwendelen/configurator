package be.cegeka.configurator.serverRegistery;

import be.cegeka.configurator.messageProcessor.MessageProcessor;
import be.cegeka.configurator.server.Server;
import be.cegeka.configurator.server.ServerFactory;
import be.cegeka.configurator.serverRegistery.impl.*;
import be.cegeka.configurator.serverRegistery.impl.message.ServerInfoHandler;
import be.cegeka.configurator.serverRegistery.impl.protocol.JoinProtocol;
import be.cegeka.configurator.serverRegistery.impl.protocol.JoinProtocolFactory;

import java.io.IOException;

public class ServerRegisteryFactory {
    private ServerFactory serverFactory;

    public ServerRegisteryFactory(ServerFactory serverFactory) {
        this.serverFactory = serverFactory;
    }

    public ServerRegistery create(MessageProcessor messageProcessor) throws IOException {
        Server thisServer = serverFactory.createThisServer(messageProcessor.getPort().get());
        Repository repository = new Repository(thisServer);

        ServerInfoHandler serverInfoHandler = new ServerInfoHandler(repository, serverFactory);

        JoinProtocolFactory joinProtocolFactory = new JoinProtocolFactory();
        JoinProtocol joinProtocol = joinProtocolFactory.create(thisServer);

        messageProcessor.addMessageHandler(serverInfoHandler);

        return new MulticastServerRegistery(joinProtocol, repository);
    }
}
