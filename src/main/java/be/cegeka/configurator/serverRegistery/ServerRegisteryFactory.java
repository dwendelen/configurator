package be.cegeka.configurator.serverRegistery;

import be.cegeka.configurator.messageProcessor.MessageHandler;
import be.cegeka.configurator.messageProcessor.MessageProcessor;
import be.cegeka.configurator.server.Server;
import be.cegeka.configurator.server.ServerFactory;

import java.io.IOException;
import java.util.List;

public class ServerRegisteryFactory {
    private ServerFactory serverFactory;

    public ServerRegisteryFactory(ServerFactory serverFactory) {
        this.serverFactory = serverFactory;
    }

    public ServerRegistery create(MessageProcessor messageProcessor) throws IOException {
        Server thisServer = serverFactory.createThisServer(messageProcessor.getPort().get());

        JoinProtocol joinProtocol = new JoinProtocol(thisServer);
        Repository repository = new Repository(thisServer);
        MulticastServerRegistery multicastServerRegistery = new MulticastServerRegistery(joinProtocol, repository, serverFactory);

        List<? extends MessageHandler> messageHandlers = multicastServerRegistery.getMessageHandlers();
        for (MessageHandler messageHandler : messageHandlers) {
            messageProcessor.addMessageHandler(messageHandler);
        }

        return multicastServerRegistery;
    }
}
