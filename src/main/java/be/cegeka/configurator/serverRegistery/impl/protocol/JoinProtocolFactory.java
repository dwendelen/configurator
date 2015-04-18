package be.cegeka.configurator.serverRegistery.impl.protocol;

import be.cegeka.configurator.message.MessageSender;
import be.cegeka.configurator.server.Server;
import be.cegeka.configurator.socket.Socket;
import be.cegeka.configurator.socket.SocketFactory;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public class JoinProtocolFactory {
    public JoinProtocol create(Server thisServer) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Socket multicastSocket = new SocketFactory().createMulticastSocket(JoinProtocol.MULTICAST);

        MessageSender messageSender = new MessageSender(multicastSocket, objectMapper);

        NewServerDaemon multicasterDaemon = new NewServerDaemon(multicastSocket, objectMapper, JoinProtocol.PORT, thisServer.getUuid());
        JoinProtocol joinProtocol = new JoinProtocol(thisServer, multicasterDaemon, messageSender);

        multicasterDaemon.setNewServerListener(joinProtocol);

        return joinProtocol;
    }
}
