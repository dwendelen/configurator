package be.cegeka.configurator.message;

import be.cegeka.configurator.socket.Session;
import be.cegeka.configurator.socket.Socket;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.net.InetAddress;

public class MessageSender {
    private Socket socket;
    private ObjectMapper objectMapper;

    public MessageSender(Socket socket, ObjectMapper objectMapper) {
        this.socket = socket;
        this.objectMapper = objectMapper;
    }

    public void send(InetAddress server, int port, Message message) throws IOException {
        Session session = socket.open(server, port);
        objectMapper.writeValue(session.write(), message);
        session.close();
    }
}
