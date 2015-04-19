package be.cegeka.configurator.controller.impl;

import be.cegeka.configurator.socket.Session;
import be.cegeka.configurator.socket.Socket;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public class MessageSender {
    private Socket socket;
    private ObjectMapper objectMapper;

    public MessageSender(Socket socket, ObjectMapper objectMapper) {
        this.socket = socket;
        this.objectMapper = objectMapper;
    }

    public void send(String server, int port, JSONMessage JSONMessage) throws IOException {
        Session session = socket.open(server, port);
        objectMapper.writeValue(session.write(), JSONMessage);
        session.close();
    }
}
