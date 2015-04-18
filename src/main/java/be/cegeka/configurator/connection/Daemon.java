package be.cegeka.configurator.connection;

import be.cegeka.configurator.listener.Message;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.net.InetAddress;

public abstract class Daemon<T extends Message> {
    private Runner runner = new Runner();
    private Socket socket;
    private ObjectMapper objectMapper;

    protected Daemon(Socket socket, ObjectMapper objectMapper) {
        this.socket = socket;
        this.objectMapper = objectMapper;
    }

    public void start() {
        runner.start();
    }

    public void stop() {
        runner.interrupt();
    }

    protected abstract int getPort();

    protected abstract Class<? extends T> deriveMessageClass(String type);

    protected abstract void messageArrived(T message, InetAddress inetAddress);

    private class Runner extends Thread {
        private ListeningContext listeningContext;

        @Override
        public void run() {
            try {
                listeningContext = socket.listen(getPort());
                while (true) {
                    if (isInterrupted()) {
                        break;
                    }
                    listenAndCloseSession();
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

        private void listenAndCloseSession() throws IOException {
            Session session = listeningContext.waitForSession();
            try {
                listen(session);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                session.close();
            }
        }

        private void listen(Session session) throws IOException {
            JsonNode jsonNode = objectMapper.readTree(session.read());

            if(!jsonNode.has("type")) {
                session.close();
                return;
            }

            Class<? extends T> messageClass = deriveMessageClass(jsonNode.get("type").asText());
            T message = objectMapper.readValue(jsonNode, messageClass);
            messageArrived(message, session.getAddress());
        }

        private void closeConnection() {
            if(listeningContext != null) {
                try {
                    listeningContext.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
