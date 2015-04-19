package be.cegeka.configurator.serverRegistery.impl.protocol;

import be.cegeka.configurator.message.Daemon;
import be.cegeka.configurator.message.MessageSender;
import be.cegeka.configurator.server.Server;
import be.cegeka.configurator.server.ServerInformation;
import be.cegeka.configurator.serverRegistery.impl.message.ServerInfoMessage;
import be.cegeka.configurator.socket.Session;
import be.cegeka.configurator.socket.Socket;
import be.cegeka.configurator.socket.SocketFactory;
import com.google.common.base.Optional;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.net.InetAddress;
import java.util.UUID;

public class NewServerDaemon extends Daemon<ServerInfoMessage> {
    private final String uuidToBlock;
    private NewServerListener newServerListener;

    public NewServerDaemon(Socket socket, ObjectMapper objectMapper, int port, String uuidToBlock) throws IOException {
        super(socket, objectMapper, port);
        this.uuidToBlock = uuidToBlock;
    }

    @Override
    protected Optional<? extends Class<? extends ServerInfoMessage>> deriveMessageClass(String type) {
        return Optional.of(ServerInfoMessage.class);
    }

    @Override
    protected void messageArrived(ServerInfoMessage message, Session session) {
        if (message.getUuid().equals(uuidToBlock)) {
            return;
        }

        if(newServerListener == null) {
            return;
        }

        ServerInformation serverInformation = new ServerInformation();
        serverInformation.setPort(message.getPort());
        serverInformation.setHostname(message.getHostname());
        serverInformation.setInetAddress(session.getAddress());
        serverInformation.setUuid(message.getUuid());

        newServerListener.newServerArrived(serverInformation);
    }

    public void setNewServerListener(NewServerListener newServerListener) {
        this.newServerListener = newServerListener;
    }
}
