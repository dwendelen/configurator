package be.cegeka.configurator.serverRegistery;

import be.cegeka.configurator.message.MessageSender;
import be.cegeka.configurator.message.MessageSenderFactory;
import be.cegeka.configurator.messageProcessor.MessageProcessor;
import be.cegeka.configurator.server.Server;
import be.cegeka.configurator.server.ServerFactory;
import be.cegeka.configurator.serverRegistery.impl.*;
import be.cegeka.configurator.serverRegistery.impl.ServerInfoHandler;
import be.cegeka.configurator.socket.Socket;
import be.cegeka.configurator.socket.SocketFactory;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public class ServerRegisteryFactory {
    private ServerFactory serverFactory;
    private MessageSenderFactory messageSenderFactory;
    private SocketFactory socketFactory;
    private ObjectMapper objectMapper;

    public ServerRegisteryFactory(ServerFactory serverFactory, MessageSenderFactory messageSenderFactory, SocketFactory socketFactory, ObjectMapper objectMapper) {
        this.serverFactory = serverFactory;
        this.messageSenderFactory = messageSenderFactory;
        this.socketFactory = socketFactory;
        this.objectMapper = objectMapper;
    }

    public ServerRegistery create(MessageProcessor messageProcessor) throws IOException {
        Server thisServer = serverFactory.createThisServer(messageProcessor.getPort().get());
        Repository repository = new Repository(thisServer);

        ServerListener serverListener = new ServerListener(repository);
        ServerInfoHandler serverInfoHandler = new ServerInfoHandler(repository, serverFactory, serverListener);
        //QuitHandler quitHandler = new QuitHandler(repository);
        UnreachableHandler messageHandler = new UnreachableHandler(repository);

        messageProcessor.addMessageHandler(serverInfoHandler);
        //messageProcessor.addMessageHandler(quitHandler);
        messageProcessor.addMessageHandler(messageHandler);

        Socket multicastSocket = socketFactory.createMulticastSocket(MulticastServerRegistery.MULTICAST);
        NewServerDaemon newServerDaemon = new NewServerDaemon(multicastSocket, objectMapper, MulticastServerRegistery.PORT, thisServer.getUuid(), serverInfoHandler);
        MessageSender messageSender = messageSenderFactory.create(multicastSocket);

        return new MulticastServerRegistery(repository, newServerDaemon, messageSender);
    }
}
