package be.cegeka.configurator.message;

import be.cegeka.configurator.socket.Session;
import be.cegeka.configurator.socket.Socket;
import com.google.common.base.Optional;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.net.InetAddress;

public class MessageSender {
    private Socket socket;
    private ObjectMapper objectMapper;

    protected MessageSender(Socket socket, ObjectMapper objectMapper) {
        this.socket = socket;
        this.objectMapper = objectMapper;
    }

    public void send(String server, int port, Object message) throws IOException {
        Session session = socket.open(server, port);
        objectMapper.writeValue(session.write(), message);
        session.close();
    }

    public <T> T sendAnReceive(String server, int port, Object message, Class<T> responseClass) throws IOException {
        Session session = socket.open(server, port);
        send(session, message);
        T response = objectMapper.readValue(session.read(), responseClass);
        return response;
    }

    public void send(Session session, Object message) throws IOException {
        objectMapper.writeValue(session.write(), message);
    }
}
