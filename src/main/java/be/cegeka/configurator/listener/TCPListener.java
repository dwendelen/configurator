package be.cegeka.configurator.listener;

import com.google.common.base.Optional;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Enumeration;
import java.util.Map;

public class TCPListener implements Listener {
    private ServerSocket serverSocket;
    private Runner runner = new Runner();
    private MessageHandlerRepo messageHandlerRepo;

    public TCPListener(MessageHandlerRepo messageHandlerRepo) {
        this.messageHandlerRepo = messageHandlerRepo;
    }

    public void start() {
        runner.start();
    }

    public void stop() {
        runner.interrupt();
    }

    @Override
    public int getPort() {
        if(serverSocket == null) {
            return -1;
        }

        return serverSocket.getLocalPort();
    }

    @Override
    public void addMessageHandler(MessageHandler messageHandler) {
        messageHandlerRepo.add(messageHandler);
    }

    private class Runner extends Thread {
        private ObjectMapper objectMapper = new ObjectMapper();

        @Override
        public void run() {
            try {
                init();
                while (true) {
                    if (isInterrupted()) {
                        break;
                    }
                    listen();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                closeConnection();
            }
        }

        @Override
        public void interrupt() {
            closeConnection();
            super.interrupt();
        }

        private void init() throws IOException {
            serverSocket = new ServerSocket(0);
        }

        private <T extends Message> void listen() throws IOException {
            Socket accept = serverSocket.accept();

            JsonNode jsonNode = objectMapper.readTree(accept.getInputStream());
            if(!jsonNode.has("type")) {
                return;
            }

            Optional<MessageHandler<T>> optional = messageHandlerRepo.get(jsonNode.get("type").asText());
            if(!optional.isPresent()) {
                return;
            }

            MessageHandler<T> messageHandler = optional.get();
            T message = objectMapper.readValue(jsonNode, messageHandler.getMessageClass());
            messageHandler.handle(message, accept.getInetAddress());
        }

        private void closeConnection() {
            if(serverSocket != null) {
                if(!serverSocket.isClosed()) {
                    try {
                        serverSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
