package be.cegeka.configurator.serverRegistery;

import be.cegeka.configurator.connection.*;
import be.cegeka.configurator.server.Server;
import com.google.common.base.Optional;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.net.InetAddress;

class Multicaster {
    public static final int PORT = 6548;
    public static final String MULTICAST = "ff05::dace";

    private MessageSender messageSender;
    private MulticasterListener multicasterListener;
    private MulticasterDaemon multicasterDaemon;
    private Server thisServer;

    public Multicaster(Server thisServer) throws IOException {
        this.thisServer = thisServer;
        ObjectMapper objectMapper = new ObjectMapper();
        MulticastConnection multicastConnection = new MulticastConnection(InetAddress.getByName(MULTICAST));
        multicasterDaemon = new MulticasterDaemon(multicastConnection, objectMapper);
        messageSender = new MessageSender(multicastConnection, objectMapper);
    }

    public void start() throws IOException {
        multicasterDaemon.init();
        multicasterDaemon.start();

        ServerInfoMessage serverInfoMessage = new ServerInfoMessage(thisServer);
        messageSender.send(InetAddress.getByName(MULTICAST), PORT, serverInfoMessage);
    }

    public void stop() {
        multicasterDaemon.stop();
    }

    public void setMulticasterListener(MulticasterListener multicasterListener) {
        this.multicasterListener = multicasterListener;
    }

    private class MulticasterDaemon extends Daemon<ServerInfoMessage> {

        public MulticasterDaemon(Socket socket, ObjectMapper objectMapper) throws IOException {
            super(socket, objectMapper);
        }

        @Override
        protected int getPort() {
            return PORT;
        }

        @Override
        protected Optional<? extends Class<? extends ServerInfoMessage>> deriveMessageClass(String type) {
            return Optional.of(ServerInfoMessage.class);
        }

        @Override
        protected void messageArrived(ServerInfoMessage message, InetAddress inetAddress) {
            if (multicasterListener != null) {
                multicasterListener.messageArrived(inetAddress, message);
            }
        }
    }
}
