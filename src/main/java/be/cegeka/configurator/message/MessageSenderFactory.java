package be.cegeka.configurator.message;

import be.cegeka.configurator.socket.Socket;
import org.codehaus.jackson.map.ObjectMapper;

public class MessageSenderFactory {
    private ObjectMapper objectMapper;

    public MessageSenderFactory(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public MessageSender create(Socket socket) {
        return new MessageSender(socket, objectMapper);
    }
}
