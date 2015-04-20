package be.cegeka.configurator.serverRegistery;

import be.cegeka.configurator.message.MessageSender;
import be.cegeka.configurator.message.MessageSenderFactory;
import be.cegeka.configurator.messageProcessor.MessageProcessor;
import be.cegeka.configurator.serverRegistery.impl.ServerFactory;
import be.cegeka.configurator.serverRegistery.impl.*;
import be.cegeka.configurator.serverRegistery.impl.message.PingHandler;
import be.cegeka.configurator.serverRegistery.impl.message.ServerInfoHandler;
import be.cegeka.configurator.serverRegistery.impl.message.UnreachableHandler;
import be.cegeka.configurator.socket.Socket;
import be.cegeka.configurator.socket.SocketFactory;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public class ServerRegisteryFactory {
    private MessageSenderFactory messageSenderFactory;
    private SocketFactory socketFactory;
    private ObjectMapper objectMapper;

    public ServerRegisteryFactory(MessageSenderFactory messageSenderFactory, SocketFactory socketFactory, ObjectMapper objectMapper) {
        this.messageSenderFactory = messageSenderFactory;
        this.socketFactory = socketFactory;
        this.objectMapper = objectMapper;
    }

    public ServerRegistery create(MessageProcessor messageProcessor) throws IOException {
        MessageSender tcpMessageSender = messageSenderFactory.create(socketFactory.createTCPSocket());
        ServerFactory serverFactory = new ServerFactory(tcpMessageSender);
        Server thisServer = serverFactory.createThisServer(messageProcessor.getPort().get());
        Repository repository = new Repository(thisServer);

        ServerListenerForRegistry serverListener = new ServerListenerForRegistry(repository);

        ServerInfoHandler serverInfoHandler = new ServerInfoHandler(repository, serverFactory, serverListener);
        UnreachableHandler messageHandler = new UnreachableHandler(repository);
        PingHandler pingHandler = new PingHandler(tcpMessageSender, thisServer.getUuid());

        messageProcessor.addMessageHandler(serverInfoHandler);
        messageProcessor.addMessageHandler(messageHandler);
        messageProcessor.addMessageHandler(pingHandler);

        Socket multicastSocket = socketFactory.createMulticastSocket(MulticastServerRegistery.MULTICAST);
        NewServerDaemon newServerDaemon = new NewServerDaemon(multicastSocket, objectMapper, MulticastServerRegistery.PORT, thisServer.getUuid(), serverInfoHandler);
        MessageSender messageSender = messageSenderFactory.create(multicastSocket);

        MulticastServerRegistery multicastServerRegistery = new MulticastServerRegistery(repository, newServerDaemon, messageSender);

        MessageProcessorWatcher messageProcessorWatcher = new MessageProcessorWatcher(multicastServerRegistery);
        messageProcessor.addListener(messageProcessorWatcher);

        return multicastServerRegistery;
    }
}
