package be.cegeka.configurator.serverRegistery;

import be.cegeka.configurator.socket.*;
import be.cegeka.configurator.message.Daemon;
import be.cegeka.configurator.message.MessageSender;
import be.cegeka.configurator.server.Server;
import com.google.common.base.Optional;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.net.InetAddress;

class JoinProtocol {
    public static final int PORT = 6548;
    public static final String MULTICAST = "ff05::dace";

    private MessageSender messageSender;
    private JoinProtocolListener joinProtocolListener;
    private MulticasterDaemon multicasterDaemon;
    private Server thisServer;

    public JoinProtocol(Server thisServer) throws IOException {
        this.thisServer = thisServer;
        ObjectMapper objectMapper = new ObjectMapper();
        Socket multicastSocket = new SocketFactory().createMulticastSocket(MULTICAST);
        multicasterDaemon = new MulticasterDaemon(multicastSocket, objectMapper);
        messageSender = new MessageSender(multicastSocket, objectMapper);
    }

    public void start() throws IOException {
        multicasterDaemon.start();

        ServerInfoMessage serverInfoMessage = new ServerInfoMessage(thisServer);
        messageSender.send(InetAddress.getByName(MULTICAST), PORT, serverInfoMessage);
    }

    public void stop() {
        multicasterDaemon.stop();
    }

    public void setJoinProtocolListener(JoinProtocolListener joinProtocolListener) {
        this.joinProtocolListener = joinProtocolListener;
    }

    private class MulticasterDaemon extends Daemon<ServerInfoMessage> {

        public MulticasterDaemon(Socket socket, ObjectMapper objectMapper) throws IOException {
            super(socket, objectMapper, PORT);
        }

        @Override
        protected Optional<? extends Class<? extends ServerInfoMessage>> deriveMessageClass(String type) {
            return Optional.of(ServerInfoMessage.class);
        }

        @Override
        protected void messageArrived(ServerInfoMessage message, InetAddress inetAddress) {
            if (joinProtocolListener != null) {
                joinProtocolListener.newSereverArrived(inetAddress, message);
            }
        }
    }
}
