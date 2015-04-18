package be.cegeka.configurator.listener;

import be.cegeka.configurator.connection.Daemon;
import com.google.common.base.Optional;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Enumeration;
import java.util.Map;

public class TCPListener implements Listener {
    private MessageHandlerRepo messageHandlerRepo;
    private ListenerDaemon listenerDaemon;

    public TCPListener(MessageHandlerRepo messageHandlerRepo) {
        ObjectMapper objectMapper = new ObjectMapper();
        this.messageHandlerRepo = messageHandlerRepo;
        TCPSocket tcpSocket = new TCPSocket();
        listenerDaemon = new ListenerDaemon(tcpSocket, objectMapper);
    }

    public void init() throws IOException {
        listenerDaemon.init();
    }

    public void start() {
        listenerDaemon.start();
    }

    @Override
    public int getPort() {
        return listenerDaemon.getActualPort();
    }

    @Override
    public void addMessageHandler(MessageHandler messageHandler) {
        messageHandlerRepo.add(messageHandler);
    }

    public void stop() {
        listenerDaemon.stop();
    }

    private class ListenerDaemon extends Daemon<Message> {

        protected ListenerDaemon(be.cegeka.configurator.connection.Socket socket, ObjectMapper objectMapper) {
            super(socket, objectMapper);
        }

        @Override
        protected int getPort() {
            return 0;
        }

        @Override
        protected Optional<? extends Class<? extends Message>> deriveMessageClass(String type) {
            Optional<MessageHandler<Message>> messageHandlerOptional = messageHandlerRepo.get(type);
            if(!messageHandlerOptional.isPresent()) {
                return Optional.absent();
            }

            MessageHandler<Message> messageHandler = messageHandlerOptional.get();
            return Optional.of(messageHandler.getMessageClass());
        }

        @Override
        protected void messageArrived(Message message, InetAddress inetAddress) {
            Optional<MessageHandler<Message>> messageHandlerOptional = messageHandlerRepo.get(message.getType());
            if(!messageHandlerOptional.isPresent()) {
                return;
            }

            MessageHandler<Message> messageHandler = messageHandlerOptional.get();
            messageHandler.handle(message, inetAddress);
        }
    }


}
