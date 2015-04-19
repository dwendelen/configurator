package be.cegeka.configurator.serverRegistery.impl;

import be.cegeka.configurator.message.Daemon;
import be.cegeka.configurator.socket.Session;
import be.cegeka.configurator.socket.Socket;
import com.google.common.base.Optional;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public class NewServerDaemon extends Daemon<ServerInfoMessage> {
    private final String uuidToBlock;
    private ServerInfoHandler serverInfoHandler;

    public NewServerDaemon(Socket socket, ObjectMapper objectMapper, int port, String uuidToBlock, ServerInfoHandler serverInfoHandler) throws IOException {
        super(socket, objectMapper, port);
        this.uuidToBlock = uuidToBlock;
        this.serverInfoHandler = serverInfoHandler;
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

        serverInfoHandler.handle(message, session);
    }
}
